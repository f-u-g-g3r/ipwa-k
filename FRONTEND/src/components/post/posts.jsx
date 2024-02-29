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
            console.log(pageNumber)
            setPosts(await getPostsByPage(pageNumber))
        } catch (e) {
            console.log(e)
        }
    }
    useEffect(() => {
        fetchPosts()
    }, []);

    useEffect(() => {
        console.log(posts)
    }, [posts]);

    return (
        <>
            <div className="flex justify-center my-10">
                <Link to={`/home`} className="btn btn-neutral w-1/12">Home</Link>
            </div>
            <PostSearch/>
            {posts.content.length ? (
                <>
                    {posts.content.map((post) => (
                        <PostCard key={post.id} post={post}/>
                    ))}
                    <ActionContext.Provider value={[fetchPosts]}>
                        <Pagination posts={posts}/>
                    </ActionContext.Provider>
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