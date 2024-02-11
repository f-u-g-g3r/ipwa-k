import axios from "axios";

const API_URL = 'http://localhost:8080/teachers';

export async function getTeacher(id) {
    return await axios.get(`${API_URL}/${id}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    })
}