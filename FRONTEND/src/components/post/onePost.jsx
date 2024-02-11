import {useEffect, useState} from "react";

import {Link, useParams} from "react-router-dom";
import {getPost} from "../services/PostService.jsx";

function OnePost() {

    const [post, setPost] = useState({});

    let  { id } = useParams();

    const fetchPost = async () => {
        try {
            setPost(await getPost(id))
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        fetchPost();
    }, []);



    return(
        <>
            <Link to={`/posts`} className="btn btn-neutral my-10 w-1/12">Back</Link>


            <p>Name: {post.name}</p>
            <p>Work name: {post.workName}</p>
            <p>Work description: {post.workDescription}</p>
            <p>Additional info: {post.additionalInfo}</p>
            <p>Salary: {post.salary}</p>
        </>
    )
}

export default OnePost