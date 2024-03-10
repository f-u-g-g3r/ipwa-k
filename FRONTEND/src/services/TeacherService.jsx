import axios from "axios";
import {isAuth} from "./AuthService.jsx";
import {redirect} from "react-router-dom";

const API_URL = 'http://localhost:8080/teachers';

export async function getTeachers() {
    try {
        const teachers = await axios.get(`${API_URL}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        return teachers.data;
    } catch (e) {
        console.log(e);
    }
}

export async function getTeachersNonPage() {
    try {
        const teachers = await axios.get(`${API_URL}/nonPage`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        return teachers.data;
    } catch (e) {
        console.log(e);
    }
}

export async function getTeachersByPage(pageNumber) {
    if (isAuth()) {
        const teachers = await axios.get(`${API_URL}?page=${pageNumber}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        return teachers.data;
    } else {
        return redirect("/login")
    }
}

export async function updateTeacher(id, data) {
    try {
        return (await axios.patch(`${API_URL}/${id}`, data, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })).data
    } catch (e) {
        console.log(e);
    }
}

export async function getTeacher(id) {
    try {
        const teacher = await axios.get(`${API_URL}/${id}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        return teacher.data;
    } catch (e) {
        console.log(e)
    }
}

export async function addTeacherToGroup(groupId, teacherId) {
    if (isAuth()) {
        try {
            const teacher = axios.patch(`${API_URL}/${groupId}/${teacherId}`, "", {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            return (await teacher).data;
        } catch (e) {
            console.log(e);
        }
    }
}