import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import {getStudent} from "../../services/StudentService.jsx";
import {getId} from "../../services/AuthService.jsx";

function StudentHome() {
    const [student, setStudent] = useState({});

    const fetchStudent = async () => {
        setStudent(await getStudent(getId()));
    }

    useEffect(() => {
        fetchStudent();
    }, []);

    return (
        <>
            <div className="flex justify-center items-start my-20 min-h-screen">
                <div className="hero-content text-center w-full">
                    <div className="w-full">
                        <h1 className="text-5xl font-bold mb-10">Welcome {`${student.firstName} ${student.lastName}`}</h1>

                        {student.accountStatus === "INACTIVE" ? <h2 className="text-2xl font-semibold mb-10 text-red-500">Your account is inactivated, you must configure your profile.</h2> : <></>}


                        <Link to={`/posts`} className="btn btn-success text-2xl w-1/3 mx-4">Job posts</Link>
                        <Link to={`/my-applications`} className="btn btn-success text-2xl w-1/3 mx-4">My applications</Link>
                        <Link to={`/student/profile`} className="btn btn-warning text-2xl w-1/3 mt-8">Configure profile info</Link>
                    </div>
                </div>
            </div>
        </>
    )
}

export default StudentHome;