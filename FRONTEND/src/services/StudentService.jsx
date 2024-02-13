import axios from "axios";
import {getId, hasAuthority, isAuth} from "./AuthService.jsx";

const API_URL = 'http://localhost:8080/students';

export async function getStudent(studentId) {
    const student = await axios.get(`${API_URL}/${studentId}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
    return student.data;
}

export async function getStudents() {
    const student = await axios.get(`${API_URL}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
    return student.data;
}

export async function updateStudent(student, studentId) {
    return await axios.patch(`${API_URL}/${studentId}`, student, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
}

export async function applyToJob(postId) {
    if (isAuth() && hasAuthority("STUDENT")) {
        try {
            return axios.patch(`${API_URL}/apply/${getId()}/${postId}`, "", {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                },
            });
        } catch (e) {
            console.log(e);
        }
    }
}

export async function findStudentsByPostId(postId) {
    if (isAuth()) {
        try {
            const students = axios.get(`${API_URL}/posts/${postId}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            return (await students).data;
        } catch (e) {
            console.log(e);
        }
    }
}