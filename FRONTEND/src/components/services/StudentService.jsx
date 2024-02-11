import axios from "axios";

const API_URL = 'http://localhost:8080/students';

export async function getStudent(studentId) {
    const student = await axios.get(`${API_URL}/${studentId}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
    return student.data;
}