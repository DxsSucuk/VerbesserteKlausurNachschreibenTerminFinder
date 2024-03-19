const BASE_PATH = "https://59fc-2a01-599-11a-8b98-b500-c90c-5aa2-7b9a.ngrok-free.app/api/"

export interface Settings {
    name: string
    value: string
}

export interface Classroom {
    name: string,
    abteilung: BigInteger,
}

export interface Exam {
    id: BigInt,
    studentId: string,
    teacherId: string,
    classroomId: string,
    hasNotice: boolean,
    missedAt: string
}

export interface Notice {
    id: BigInt,
    examId: BigInt,
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