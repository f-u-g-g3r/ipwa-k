import axios from "axios";
import {hasAuthority, isAuth} from "./AuthService.jsx";
import {redirect} from "react-router-dom";

const API_URL = 'http://localhost:8080/posts';

export async function savePost(post) {
    return await axios.post(API_URL, post, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
    });
}

export async function getPosts() {
    if (isAuth()) {
        const posts = await axios.get(API_URL, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        })
        return posts.data;
    } else {
        return redirect("/login")
    }
}

export async function getPost(id) {
    if (isAuth()) {
        const post = await axios.get(`${API_URL}/${id}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        });
        return post.data;
    } else {
        return redirect("/login")
    }
}

export async function updatePost(postData, id) {
    if (isAuth()) {
        if (hasAuthority("COMPANY") || hasAuthority("COORDINATOR")) {
            return await axios.patch(`${API_URL}/${id}`, postData, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
                }
            });
        }
    }
    return redirect("/login")
}

export async function getPostByCompanyId(companyId) {
    if (isAuth()) {
        const posts = await axios.get(`${API_URL}/company/${companyId}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        });
        return posts.data;
    }
}

export async function getPostsByStudentId(studentId) {
    if (isAuth()) {
        const posts = await axios.get(`${API_URL}/student/${studentId}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
        });

        return posts.data;
    }
}

export async function uploadPostPdf(id, file) {
    if (isAuth()) {
        const post = await axios.put(`${API_URL}/pdf/${id}`, file, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`,
                'Content-Type': 'multipart/form-data'
            }
        });
        return post.data;
    } else {
        return redirect("/login")
    }
}

export async function getPostPdf(path) {
    if (isAuth()) {
        try {
            const response = await axios.get(`${API_URL}/pdf/${path}`, {
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