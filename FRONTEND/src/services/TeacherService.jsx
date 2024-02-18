import axios from "axios";
import {isAuth} from "./AuthService.jsx";

const API_URL = 'http://localhost:8080/teachers';

export async function getTeachers() {
    try {
        const teachers = await axios.get(`${API_URL}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        console.log(teachers.data)
        return teachers.data;
    } catch (e) {
        console.log(e);
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