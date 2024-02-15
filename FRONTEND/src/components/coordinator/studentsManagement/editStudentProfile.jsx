import {Form, Link, redirect, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {addStudentToGroup, getStudent, updateStudent} from "../../../services/StudentService.jsx";
import {getId} from "../../../services/AuthService.jsx";
import {getAllGroups} from "../../../services/ClassGroupService.jsx";

export async function actionEditStudent({request}) {
    const formData = await request.formData();
    const studentData = Object.fromEntries(formData);

    if (formData.get("classGroup") != null) {
        await addStudentToGroup(formData.get("classGroup"), formData.get("studentId"));
    }

    await updateStudent(studentData, getId());

    const student = await getStudent(getId());
    localStorage.setItem("firstName", student.firstName);
    localStorage.setItem("lastName", student.lastName);

    return redirect("/home")
}

function EditStudentProfile() {
    let {id} = useParams();

    const [student, setStudent] = useState({});
    const [isActive, setIsActive] = useState(false);
    const [groups, setGroups] = useState({});

    const fetchGroups = async () => {
        try {
            setGroups(await getAllGroups());
        } catch (e) {
            console.log(e)
        }
    }

    const fetchStudent = async () => {
        try {
            setStudent(await getStudent(id));
        } catch (e) {
            console.log(e)
        }
    }

    const activateStudent = async () => {
        try {
            await updateStudent({"accountStatus": "ACTIVE"}, id)
            await setStudent(await getStudent(id))
        } catch (e) {
            console.log(e)
        }
    }

    const deactivateStudent = async () => {
        try {
            await updateStudent({"accountStatus": "INACTIVE"}, id)
            await setStudent(await getStudent(id))
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchStudent()
        fetchGroups()
    }, []);

    useEffect(() => {
        if (student.accountStatus === "ACTIVE") {
            setIsActive(true);
        } else {
            setIsActive(false);
        }
    }, [student]);

    return (
        <>
            <div className="flex justify-center">
            <Link to={`/home`} className="btn btn-neutral w-1/6 mt-10 mx-auto">Back</Link>
            </div>
            <p className="text-2xl font-bold text-center my-10">Edit student profile ({student.firstName + " " + student.lastName})</p>
            <Form method="post" className="w-full">
                <div className="text-lg flex justify-center w-full">
                    <span className={"text-lg font-bold mt-2 " + (isActive ? "text-success" : "text-red-400")}>
                        Account status: {student.accountStatus}
                    </span>
                    <button type="button" onClick={isActive ? deactivateStudent : activateStudent}
                            className={"text-lg font-bold ms-5 " + (isActive ? "btn btn-error" : "btn btn-success")}>
                        {isActive ? "Deactivate" : "Activate"}
                    </button>
                </div>

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
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text text-lg">Username</span>
                        </div>
                        <input type="text" defaultValue={student.username} name="username"
                               className="input input-bordered w-full"/>
                    </label>
                </div>
                <div className="text-lg flex justify-center w-full">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text text-lg">Group</span>
                        </div>
                        <select className="select select-bordered w-full" name="classGroup">
                            <option disabled>Group</option>
                            {groups.length ?
                                groups.map((group) =>
                                    <option selected={student.classGroup === group.name} value={group.id} name={group.name}>{group.name}</option>) :
                            <></>}
                        </select>
                    </label>
                </div>
                <div className="text-lg flex justify-center w-full">
                    <div className="w-full max-w-xs">
                        <a className="btn btn-neutral my-3">Reset password</a>
                        <input type="hidden" name="studentId" value={student.id}/>
                        <input type="submit" className="btn btn-success my-3 w-full" value="Update profile"/>
                    </div>
                </div>
            </Form>
        </>
    )
}

export default EditStudentProfile