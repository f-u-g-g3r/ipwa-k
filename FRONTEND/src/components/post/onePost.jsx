import {useEffect, useState} from "react";
import {getPost, getPostPdf, updatePost} from "../../services/PostService.jsx";
import {Link, useParams} from "react-router-dom";
import {getId, hasAuthority} from "../../services/AuthService.jsx";
import {applyToJob} from "../../services/StudentService.jsx";

function OnePost() {

    const [post, setPost] = useState({students: []});
    const [pdf, setPdf] = useState("");

    let {id} = useParams();

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

    const apply = async (postId) => {
        await applyToJob(postId).then(
            fetchPost
        );
    }

    const edit = async (postId) => {
        await updatePost(postId).then(
            fetchPost
        );
    }

    return (
        <>

            <div className="flex">
                <Link to={`/posts`} className="btn btn-neutral w-1/12 ms-20">Back</Link>
                {(hasAuthority("STUDENT") && !post.students.includes(parseInt(getId()))) ?
                    <button className="btn btn-success w-1/12 ms-auto me-20" onClick={() => apply(post.id)}>Apply</button> :
                    (hasAuthority("STUDENT") && post.students.includes(parseInt(getId()))) ?
                            <button className="btn btn-error w-1/12 ms-auto me-20" onClick={() => apply(post.id)}>Unapply</button> :
                                (hasAuthority("COMPANY") && post.company == getId()) ?
                                    <Link to={`/edit-post/${id}`} className="btn btn-info w-1/12 ms-auto me-20">Edit post</Link> :
                                    <></>}
            </div>
            <p>Name: {post.name}</p>
            <p>Work name: {post.workName}</p>
            <p>Work description: {post.workDescription}</p>
            <p>Additional info: {post.additionalInfo}</p>
            <p>Salary: {post.salary}</p>
            <p>Expiry date: {post.expiryDate}</p>
            <p>Date posted: {post.datePosted}</p>

            <div className="w-full flex justify-center">
                <img className="w-1/2" src={pdf}/>
            </div>
        </>
    )
}

export default OnePost