import axios from "axios";

const API_URL = 'http://localhost:8080/companies';

export async function getCompanies() {
    try {
        const companies = await axios.get(`${API_URL}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        return companies.data;
    } catch (e) {
        console.log(e);
    }
}

export async function getCompany(id) {
    const company = await axios.get(`${API_URL}/${id}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
    return company.data;
}

export async function updateCompany(company, id) {
    return axios.patch(`${API_URL}/${id}`, company, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
}

export async function uploadLogo(id, file) {
    return axios.put(`${API_URL}/logos/${id}`, file, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`,
            'Content-Type': 'multipart/form-data'
        }
    });
}

export async function getLogo(path) {
    try {
        const response = await axios.get(`${path}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            },
            responseType: 'blob'
        });
        const blob = response.data;
        return URL.createObjectURL(blob);
    } catch (error) {
        console.error(error);
    }
}