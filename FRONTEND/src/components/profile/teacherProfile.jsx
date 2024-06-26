import {useEffect, useState} from "react";
import {getTeacher, updateTeacher} from "../../services/TeacherService.jsx";
import {getId} from "../../services/AuthService.jsx";
import {Form, redirect} from "react-router-dom";

export async function actionTeacherProfile({request}) {
    const formData = await request.formData();
    const teacherData = Object.fromEntries(formData);
    await updateTeacher(getId(), teacherData);

    const teacher = await getTeacher(getId())
    localStorage.setItem("firstName", teacher.firstName);
    localStorage.setItem("lastName", teacher.lastName);

    return redirect("/teacher/profile")
}

function TeacherProfile() {
    const [teacher, setTeacher] = useState({});

    const fetchTeacher = async () => {
        try {
            setTeacher(await getTeacher(getId()));
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchTeacher();
    }, []);

    return(
        <>
            <div className="flex justify-center ">
                <Form method="post" className="w-full">
                    <div className="text-lg flex justify-center w-full">
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text text-lg">First name</span>
                            </div>
                            <input type="text" defaultValue={teacher.firstName} name="firstName"
                                   className="input input-bordered w-full"/>
                        </label>
                    </div>

                    <div className="text-lg flex justify-center w-full">
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text text-lg">Last name</span>
                            </div>
                            <input type="text" defaultValue={teacher.lastName} name="lastName"
                                   className="input input-bordered w-full"/>
                        </label>
                    </div>
                    <div className="text-lg flex justify-center w-full">
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text text-lg">Email</span>
                            </div>
                            <input type="text" defaultValue={teacher.email} name="email"
                                   className="input input-bordered w-full"/>
                        </label>
                    </div>
                    <div className="text-lg flex justify-center w-full">
                        <div className="w-full max-w-lg">
                            <a className="btn btn-neutral my-3">Reset password</a>
                            <p className="text-lg my-3">Username: {teacher.username}</p>
                            <p className="text-lg my-3">Group: {teacher.classGroup}</p>

                            <input type="submit" className="btn btn-success my-3 w-full" value="Update profile"/>
                        </div>
                    </div>
                </Form>
            </div>
        </>
    )
}

export default TeacherProfile