import axios from "axios";
import {redirect} from "react-router-dom";

const API_URL = 'http://localhost:8080/auth';

export async function auth(data) {
    try {
        return axios.post(`${API_URL}/authenticate`, {
            username: data.username,
            password: data.password
        });
    } catch (e) {
        return "Invalid credentials"
    }
}

export async function createCompany(companyData) {
    const response = await axios.post(`${API_URL}/companies`, companyData,{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    })
    return response.data;
}

export async function createTeacher(teacherData) {
    console.log(1)
    const response = await axios.post(`${API_URL}/teachers`, teacherData,{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    })
    return response.data;
}

export async function createStudent(studentData) {
    const response = await axios.post(`${API_URL}/students`, studentData,{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    })
    return response.data;
}

export function isAuthLoader() {
    if (!localStorage.getItem("token")) {
        return redirect("/login")
    }
    return true
}

export function isAuth() {
    return localStorage.getItem("token");

}

export function hasAuthority(authority) {
    return localStorage.getItem('authority') === authority;

}
export function hasAuthorityLoader(authority) {
    if (localStorage.getItem('authority') !== authority) {
        return redirect("/home");
    }
    return null;
}

export function hasAnyAuthorityLoader(...authorities) {
    for (let authority of authorities) {
        if (localStorage.getItem('authority') === authority) {
            return null;
        }
    }
    return redirect("/home");
}

export function getAuthority() {
    return localStorage.getItem("authority");
}

export function getId() {
    return localStorage.getItem("id");
}

export function generateRandomString(length) {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-%';
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

