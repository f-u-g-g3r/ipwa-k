import axios from "axios";
import {hasAuthority, isAuth} from "./AuthService.jsx";
import {redirect} from "react-router-dom";


const API_URL = 'http://localhost:8080/class-groups';

export async function getAllGroups() {
    if (isAuth()) {
        try {
            const groups = axios.get(`${API_URL}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
            return (await groups).data;
        } catch (e) {
            console.log(e);
        }
    } else {
        return redirect("/login")
    }
}

export async function addNewGroup(group) {
    if (isAuth() && hasAuthority("COORDINATOR")) {
        try {
            return axios.post(`${API_URL}`, group, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
        } catch (e) {
            console.log(e);
        }
    } else {
        return redirect("/login")
    }
}

export async function deleteGroup(groupId) {
    if (isAuth() && hasAuthority("COORDINATOR")) {
        try {
            return axios.delete(`${API_URL}/${groupId}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
        } catch (e) {
            console.log(e)
        }
    }
}