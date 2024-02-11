import axios from "axios";

const API_URL = 'http://localhost:8080/companies';

export async function getCompany(id) {
    const company = await axios.get(`${API_URL}/${id}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
    return company.data;
}