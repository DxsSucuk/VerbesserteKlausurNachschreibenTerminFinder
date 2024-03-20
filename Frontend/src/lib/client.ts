const BASE_PATH = "http://localhost:8080/api/"

export interface Settings {
    name: string
    value: string
}

export interface Classroom {
    name: string,
    abteilung: BigInteger,
}

export interface Exam {
    id: bigint,
    studentId: string,
    teacherId: string,
    classroomId: string,
    hasNotice: boolean,
    missedAt: string
}

export interface Notice {
    id: bigint,
    examId: bigint,
    studentId: string,
    approveByTeacherId: string,
    createdAt: string
}

export interface Appointment {
    id: bigint,
    acceptedByTeacher: boolean,
    acceptedByStudent: boolean,
    teacherId: string,
    studentId: string,
    classroomId: string,
    date: string
}

export interface Student {
    id: string,
    firstname: string,
    lastname: string,
    email: string,
    classroomId: string
}

export interface Teacher {
    id: string,
    firstname: string,
    lastname: string,
    email: string,
    abteilung: bigint
}

export function parseClassroom(values: any) {
    let array = new Array<Classroom>()

    for (const value of values) {
        const instance: Classroom = {
            name: value.name,
            abteilung: value.abteilung
        };

        array.push(instance);
    }

    return array;
}

export function parseStudents(values: any) {
    let array = new Array<Student>()

    for (const value of values) {
        const instance: Student = {
            id: value.id,
            firstname: value.vorname,
            lastname: value.nachname,
            email: value.email,
            classroomId: value.classroomId
        };

        array.push(instance);
    }

    return array;
}

export function parseAppointments(values: any) {
    let array = new Array<Appointment>()

    for (const value of values) {
        const instance: Appointment = {
            id: value.id,
            acceptedByStudent: value.acceptedByStudent,
            acceptedByTeacher: value.acceptedByTeacher,
            classroomId: value.classroomId,
            date: value.date,
            studentId: value.studentId,
            teacherId: value.teacherId
        };

        array.push(instance);
    }

    return array;
}

export function parseExams(values: any) {
    let array = new Array<Exam>()

    for (const value of values) {
        const instance: Exam = {
            id: value.id,
            classroomId: value.classroomId,
            hasNotice: value.hasNotice,
            missedAt: value.missedAt,
            studentId: value.studentId,
            teacherId: value.teacherId
        };

        array.push(instance);
    }

    return array;
}

export function parsenotice(values: any) {
    let array = new Array<Notice>()

    for (const value of values) {
        const instance: Notice = {
            id: value.id,
            approveByTeacherId: value.approvedByTeacherId,
            createdAt: value.createdAt,
            examId: value.examId,
            studentId: value.studentId
        };

        array.push(instance);
    }

    return array;
}

export async function login(name: string, pw: string) {
    const value = await post("auth/login", JSON.stringify({ name: name, password: pw }))

    if (value.success) {
        localStorage.setItem("token", value.object.sessionToken)
        localStorage.setItem("name", value.object.fullName)
        return true
    } else {
        console.log("Login failed!")
        return false;
    }
}

export async function getClassrooms() {
    const value = await get("teacher/classrooms");
    console.log(value)
    if (value.success) {
        return parseClassroom(value.object)
    } else {
        console.log("Failed to retrieve classrooms!")
        return [];
    } 
}

export async function getStudentInClassroom(id: string) {
    const value = await get(`classroom/${id}/students`);
    if (value.success) {
        return parseStudents(value.object)
    } else {
        console.log("Failed to retrieve students in classroom!")
        return [];
    } 
}

export async function getProposedAppointments() {
    const value = await get("appointment/proposed")
    if (value.success) {
        return parseAppointments(value.object)
    } else {
        console.log("Failed to retrieve appointments!")
        return [];
    }
}

export async function getAcceptedAppointments() {
    const value = await get("appointment/agreed")
    if (value.success) {
        return parseAppointments(value.object)
    } else {
        console.log("Failed to retrieve appointments!")
        return [];
    }
}

export async function getPendingAppointments() {
    const value = await get("appointment/pending")
    if (value.success) {
        return parseAppointments(value.object)
    } else {
        console.log("Failed to retrieve appointments!")
        return [];
    }
}

export async function getMissedExamsForClass(id: string) {
    const value = await post("exam/class", JSON.stringify({ value: id}))
    if (value.success) {
        return parseExams(value.object)
    } else {
        console.log("Failed to retrieve missed exams.")
        return [];
    }
}

export async function getMissedExams() {
    const value = await get("exam/missed")
    if (value.success) {
        return parseExams(value.object)
    } else {
        console.log("Failed to retrieve missed exams.")
        return [];
    }
}

export async function hasNoticeForExam(exam: bigint, student:string) {
    const value = await post("notice/exam", JSON.stringify({ examId: exam, studentId: student}))
    if(value.success) {
        return true;
    } else {
        return false;
    }
}

export function getToken() {
    return localStorage.getItem("token") ?? "Missing Token"
}

export async function logout() {
    const value = await post("auth/logout", JSON.stringify({ value: getToken()}))

    if (value.success) {
        localStorage.removeItem("token")
        localStorage.removeItem("name")
        return true
    } else {
        console.log("Logout failed!")
        return false;
    }
}

export async function createNewAppointment(date: Date, classroomId: string, studentIds: string[]) {
    const value = await post("appointment/create", JSON.stringify({ "classroomId": classroomId, "date": date, "students": studentIds}))

    if (value.success) {
        return true;
    } else {
        console.log("Failed to create Appointment")
        return false
    }
}

export async function checkSession() {
    const value = await post("auth/check", JSON.stringify({ value: getToken() }))

    if (value.success) {
        return true
    } else {
        console.log("Session invalid!")
        localStorage.removeItem("token")
        localStorage.removeItem("name")
        return false;
    }
}


export async function post(path: string, body: string) {

    try {
        const res = await fetch(BASE_PATH + path, {
            method: "POST",
            body: body,
            headers: {
                "Content-Type": "application/json",
                "Authorization": getToken()
            },
        })

        if (res.status != 200) {
            console.error(res.status + " " + path)
            return { success: false }
        }

        const json = await res.json()
        if (!json.success) {
            console.error(json.message)
            return { success: false, message: json.message }
        }

        return json
    } catch (err) {
        return { success: false, message: err}
    }
}


export async function get(path: string) {

    try {
        const res = await fetch(BASE_PATH + path, {
            method: "GET",
            headers: {
                "Authorization": getToken(),
                "ngrok-skip-browser-warning": "tits"
            },
        })

        if (res.status != 200) {
            console.error(res.status + " " + path)
            return { success: false }
        }

        const json = await res.json()
        if (!json.success) {
            console.error(json.message)
            return { success: false, message: json.message }
        }

        return json
    } catch (err) {
        return { success: false, message: err}
    }
}