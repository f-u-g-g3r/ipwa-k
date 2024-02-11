import {useEffect, useState} from "react";
import {getPost, getPostPdf} from "../../services/PostService.jsx";
import {Link, useParams} from "react-router-dom";

function OnePost() {

    const [post, setPost] = useState({});
    const [pdf, setPdf] = useState("");

    let  { id } = useParams();

    const fetchPost = async () => {
        try {
            setPost(await getPost(id))
        } catch (e) {
            console.log(e);
        }
    }

    const fetchPdf = async () => {
        try {
            if (post.pathToPdf !== null) {
                setPdf(await getPostPdf(post.pathToPdf))
            }
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        fetchPost();
    }, []);

    useEffect(() => {
        if (post.pathToPdf !== undefined) {
            fetchPdf()
        }
    }, [post])


    return(
        <>
            <Link to={`/posts`} className="btn btn-neutral my-10 w-1/12">Back</Link>


            <p>Name: {post.name}</p>
            <p>Work name: {post.workName}</p>
            <p>Work description: {post.workDescription}</p>
            <p>Additional info: {post.additionalInfo}</p>
            <p>Salary: {post.salary}</p>
            <p>Expiry date: {post.expiryDate}</p>
            <p>Date posted: {post.datePosted}</p>

            <div className="w-full flex justify-center">
                <img className="w-1/2" src={pdf} />
            </div>
        </>
    )
}

export default OnePost