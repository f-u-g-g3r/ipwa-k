import {useEffect, useState} from "react";
import {getStudent} from "../../services/StudentService.jsx";
import {getId} from "../../services/AuthService.jsx";
import {getPost, getPostsByStudentId} from "../../services/PostService.jsx";
import {Link} from "react-router-dom";
import {getCompaniesByStudentId} from "../../services/CompanyService.jsx";

function StudentApplications() {

    const [student, setStudent] = useState({postStudents: []});
    const [posts, setPosts] = useState({});
    const [companies, setCompanies] = useState([]);

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

    const fetchCompanies = async (id) => {
        try {
            setCompanies(await getCompaniesByStudentId(id))
        } catch (e) {
            console.log(e)
        }
    }

    const getPostInfo = (postId) => {
        let post;
        let company;
        if (posts.length > 0 && companies.length > 0) {
            for (let onePost of posts) {
                if (onePost.id === postId) {
                    post = onePost;
                    for (let oneCompany of companies) {
                        if (oneCompany.id === post.company) {
                            company = oneCompany;
                        }
                    }
                }
            }
            return (
                <>
                    <td>{post.workName}</td>
                    <td>{company.name}</td>
                    <td>{post.expiryDate}</td>
                </>
            )
        }

    }

    useEffect(() => {
        fetchStudent();
        fetchPosts(getId());
        fetchCompanies(getId());
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
                        {getPostInfo(application.post)}
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