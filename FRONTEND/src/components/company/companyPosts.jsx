import { useEffect, useState} from "react";
import {getPostByCompanyId} from "../../services/PostService.jsx";
import {getId} from "../../services/AuthService.jsx";
import PostCard from "../post/postCard.jsx";

function CompanyPosts() {
    const [posts, setPosts] = useState({});


    const fetchPosts = async () => {
        setPosts(await getPostByCompanyId(getId()));
    }

    useEffect(() => {
        fetchPosts();
    }, []);


    return(
        <>
            {posts.length ? (
                <>
                {posts.map((post) => (
                    <PostCard key={post.id} isCheck={true} post={post}/>
                    ))}
                </>
            ) : <></>}

        </>
    )
}

export default CompanyPosts