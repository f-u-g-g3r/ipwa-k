import {useEffect, useState} from "react";
import {getStudent, updateStudent} from "../services/StudentService.jsx";
import {Form, redirect} from "react-router-dom";
import {getId} from "../services/AuthService.jsx";

export async function actionStudentProfile({request}) {
    const formData = await request.formData();
    const studentData = Object.fromEntries(formData);
    await updateStudent(studentData, getId());

    const student = await getStudent(getId());
    localStorage.setItem("firstName", student.firstName);
    localStorage.setItem("lastName", student.lastName);

    return redirect("/student/profile")
}

function StudentProfile() {

    const [student, setStudent] = useState({});

    const fetchStudent = async () => {
        try {
            setStudent(await getStudent(getId()));
        } catch (e) {
            console.log(e)
        }
    }
    useEffect(() => {
        fetchStudent()
    }, []);

    return (
        <>
            <p className="text-2xl font-bold text-center my-10">Edit profile</p>
            <Form method="post" className="w-full">
                <div className="text-lg flex justify-center w-full">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text text-lg">First name</span>
                        </div>
                        <input type="text" defaultValue={student.firstName} name="firstName"
                               className="input input-bordered w-full"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-full">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text text-lg">Last name</span>
                        </div>
                        <input type="text" defaultValue={student.lastName} name="lastName"
                               className="input input-bordered w-full"/>
                    </label>
                </div>
                <div className="text-lg flex justify-center w-full">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text text-lg">Email</span>
                        </div>
                        <input type="text" defaultValue={student.email} name="email"
                               className="input input-bordered w-full"/>
                    </label>
                </div>
                <div className="text-lg flex justify-center w-full">
                    <div className="w-full max-w-xs">
                        <a className="btn btn-neutral my-3">Reset password</a>
                        <p className="text-lg my-3">Username: {student.username}</p>
                        <p className="text-lg my-3">Group: {student.classGroup}</p>
                        <p className="text-lg my-3">Class teacher: {student.teacher === null ? "null" : student.teacher}</p>
                        <input type="submit" className="btn btn-success my-3 w-full" value="Update profile" />
                    </div>
                </div>
            </Form>
        </>
    )
}

export default StudentProfile