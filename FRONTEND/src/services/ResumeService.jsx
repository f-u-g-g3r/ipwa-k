import {isAuth} from "./AuthService.jsx";
import axios from "axios";

const API_URL = 'http://localhost:8080/resumes';

export async function getResume(id) {
    if (isAuth()) {
        try {
            const resume = await axios.get(`${API_URL}/${id}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            return resume.data;
        } catch (e) {
            console.log(e)
        }
    }
}