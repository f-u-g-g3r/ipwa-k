import {createContext, useContext, useEffect, useState} from "react";
import {getPostByCompanyId} from "../../services/PostService.jsx";
import {getId} from "../../services/AuthService.jsx";
import PostCard from "../post/postCard.jsx";
import {Link} from "react-router-dom";
import {ActionContext} from "../post/posts.jsx";


export const ActionContextFetchPosts = createContext();
function CompanyPosts() {
    const [posts, setPosts] = useState({});


    const fetchPosts = async () => {
        setPosts(await getPostByCompanyId(getId()));
    }

    useEffect(() => {
        fetchPosts();
    }, []);


    return (
        <>
            {posts.length ? (
                    <>
                        {posts.map((post) => (
                            <ActionContextFetchPosts.Provider value={fetchPosts}>
                                <PostCard key={post.id} isCheck={true} post={post}/>
                            </ActionContextFetchPosts.Provider>
                        ))}
                    </>
                ) :
                <div>
                    <p className="text-4xl font-bold text-center mt-20">Your company has not posted a single post
                        yet.</p>
                    <div className="flex justify-center">
                        <Link to={`/posts/new`} className="btn btn-success w-1/5 text-2xl mt-10">Create a post</Link>
                    </div>
                </div>}

        </>
    )
}

export default CompanyPosts