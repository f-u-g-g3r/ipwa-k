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

export async function createCompany(companyData) {
    const response = await axios.post("http://localhost:8080/auth/companies", companyData,{
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    })
    return response.data;
}

export async function updateCompany(id, company) {
    return axios.patch(`${API_URL}/${id}`, company, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
}