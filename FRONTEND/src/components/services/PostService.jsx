import axios from "axios";
import {isAuth} from "./AuthService.jsx";
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