import axios from "axios";
import {getId, hasAuthority, isAuth} from "./AuthService.jsx";
import {redirect} from "react-router-dom";

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

export async function addStudentToGroup(groupId, studentId) {
    if (isAuth()) {
        try {
            const student = axios.patch(`${API_URL}/${groupId}/${studentId}`, "", {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            return (await student).data;
        } catch (e) {
            console.log(e);
        }
    }
}


export async function uploadCv(studentId, file) {
    if (isAuth()) {
        try {
            await axios.put(`${API_URL}/${studentId}/cv`, file, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
        } catch (e) {
            console.log(e)
        }
    }
}

export async function getCvPdf(path) {
    if (isAuth()) {
        try {
            const response = await axios.get(`${API_URL}/cv/${path}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                },
                responseType: 'blob'

            });
            const blob = response.data;
            return URL.createObjectURL(blob);
        } catch (e) {
            console.log(e)
        }
    } else {
        return redirect("/login")
    }
}

export async function uploadMotivationLetter(studentId, file) {
    if (isAuth()) {
        try {
            await axios.put(`${API_URL}/${studentId}/motivationLetter`, file, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
        } catch (e) {
            console.log(e)
        }
    }
}

export async function getMotivationLetter(path) {
    if (isAuth()) {
        try {
            const response = await axios.get(`${API_URL}/motivationLetter/${path}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                },
                responseType: 'blob'

            });
            const blob = response.data;
            return URL.createObjectURL(blob);
        } catch (e) {
            console.log(e)
        }
    } else {
        return redirect("/login")
    }
}