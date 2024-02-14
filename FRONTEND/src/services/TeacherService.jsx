import axios from "axios";
import {isAuth} from "./AuthService.jsx";

const API_URL = 'http://localhost:8080/teachers';

export async function getTeacher(id) {
    try {
        const teacher = axios.get(`${API_URL}/${id}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        return (await teacher).data;
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