import {createContext, useContext, useEffect, useState} from "react";
import PostCard from "./postCard.jsx";
import PostSearch from "./postSearch.jsx";
import {getPosts, getPostsByPage} from "../../services/PostService.jsx";
import {Link} from "react-router-dom";
import Pagination from "../pagination/pagination.jsx";

export const ActionContext = createContext();
function Posts() {
    const [posts, setPosts] = useState({content: []});

    const fetchPosts = async (pageNumber = 0) => {
        try {
            setPosts(await getPostsByPage(pageNumber))
        } catch (e) {
            console.log(e)
        }
    }
    useEffect(() => {
        fetchPosts()
    }, []);

    return (
        <>
            <PostSearch/>
            {posts.content.length ? (
                <>
                    {posts.content.map((post) => (
                        <PostCard key={post.id} post={post}/>
                    ))}
                    <Pagination data={posts} fetchAction={fetchPosts}/>
                </>
            ) : (
                <p>
                    <i>No job posts.</i>
                </p>
            )}
        </>
    )
}

export default Posts