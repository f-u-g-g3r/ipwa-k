import axios from "axios";

const API_URL = 'http://localhost:8080/internship-coordinators';

export async function getCoordinator(coordinatorId) {
    const coordinator = await axios.get(`${API_URL}/${coordinatorId}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    })
    return coordinator.data;
}