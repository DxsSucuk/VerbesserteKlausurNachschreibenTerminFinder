const BASE_PATH = "https://freedom-api.presti.me/api/"

export async function login(name: string, pw: string) {
    let value = await post("auth/login", JSON.stringify({ name: name, password: pw }))

    if (value.success) {
        localStorage.setItem("token", value.token)
        localStorage.setItem("name", value.fullName)
        return true
    } else {
        console.log("Login failed!")
        return false;
    }
}

export async function logout() {
    let value = await post("auth/logout", '')

    if (value.success) {
        localStorage.removeItem("token")
        localStorage.removeItem("name")
        return true
    } else {
        console.log("Loginout failed!")
        return false;
    }
}

export async function checkSession() {
    let value = await post("auth/check", '')

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
                "Authorization": localStorage.getItem("token") ?? "Missing Token"
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
