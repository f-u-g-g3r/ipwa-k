import {Form, Link, redirect, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getTeacher, updateTeacher} from "../../../services/TeacherService.jsx";
import {getId} from "../../../services/AuthService.jsx";

export async function actionEditTeacher({request}) {
    const formData = await request.formData();
    const teacherData = Object.fromEntries(formData);
    await updateTeacher(formData.get("teacherId"), teacherData);

    const teacher = await getTeacher(formData.get("teacherId"))
    localStorage.setItem("firstName", teacher.firstName);
    localStorage.setItem("lastName", teacher.lastName);

    return redirect("/home?action=3")
}

function EditTeacherProfile() {
    let {id} = useParams();

    const [teacher, setTeacher] = useState({})

    const fetchTeacher = async () => {
        try {
            setTeacher(await getTeacher(id));
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchTeacher()
    }, []);

    return(
        <>
            <div className="flex justify-center">
                <Link to={`/home?action=3`} className="btn btn-neutral w-1/6 mt-10 mx-auto">Back</Link>
            </div>
            <p className="text-2xl font-bold text-center my-10">Edit profile</p>
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
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text text-lg">Username</span>
                            </div>
                            <input type="text" defaultValue={teacher.username} name="username"
                                   className="input input-bordered w-full"/>
                        </label>
                    </div>
                    <div className="text-lg flex justify-center w-full">
                        <div className="w-full max-w-lg">
                            <a className="btn btn-neutral my-3">Reset password</a>
                            <p className="text-lg my-3">Group: {teacher.classGroup}</p>
                            <input type="hidden" name="teacherId" value={teacher.id}/>
                            <input type="submit" className="btn btn-success my-3 w-full" value="Update profile"/>
                        </div>
                    </div>
                </Form>
            </div>
        </>
    )
}

export default EditTeacherProfile