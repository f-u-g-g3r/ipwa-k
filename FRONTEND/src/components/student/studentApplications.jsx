import {useEffect, useState} from "react";
import {getStudent} from "../../services/StudentService.jsx";
import {getId} from "../../services/AuthService.jsx";
import {getPost, getPostsByStudentId} from "../../services/PostService.jsx";
import {Link} from "react-router-dom";

function StudentApplications() {

    const [student, setStudent] = useState({postStudents: []});
    const [posts, setPosts] = useState({});

    const fetchStudent = async () => {
        try {
            setStudent(await getStudent(getId()))
        } catch (e) {
            console.log(e);
        }
    }

    const fetchPosts = async (id) => {
        try {
            setPosts(await getPostsByStudentId(id))
        } catch (e) {
            console.log(e)
        }
    }

    const test = (postId) => {
        let post;
        if (posts.length > 0) {
            for (let onePost of posts) {
                if (onePost.id == postId) {
                    post = onePost;
                }
            }
            return (
                <>
                    <td>{post.workName}</td>
                    <td>{post.company}</td>
                    <td>{post.expiryDate}</td>
                </>
            )
        }

    }

    useEffect(() => {
        fetchStudent();
        fetchPosts(getId());
    }, []);

    return (
        <>
            <div className="flex justify-center my-10">
                <Link to={`/home`} className="btn btn-neutral w-1/12">Home</Link>
            </div>

            <p className="text-3xl font-bold text-center my-10">Your applications</p>

            <table className="table border-slate-500 bg-gray-200 table-lg">
                <thead className="bg-gray-300">
                <tr>
                    <th>Id</th>
                    <th>Work name</th>
                    <th>Company</th>
                    <th>Application deadline</th>
                    <th>Date of application</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {student.postStudents.map((application) => (
                    <tr key={application.id}>
                        <td>{application.id}</td>
                        {test(application.post)}
                        <td>{application.createdAt}</td>
                        <td className="font-bold">{application.status}</td>
                        <td>
                            <Link to={`/posts/${application.post}?stHome=true`} className="btn btn-outline">Open</Link>
                        </td>
                    </tr>)
                )}
                </tbody>
            </table>
        </>
    )
}

export default StudentApplications