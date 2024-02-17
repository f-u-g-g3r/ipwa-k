import {useEffect, useState} from "react";
import {createTeacher, generateRandomString} from "../../../services/AuthService.jsx";
import {getTeachers} from "../../../services/TeacherService.jsx";

function TeacherProfileManagement() {

    const [teachers, setTeachers] = useState({});
    const [newTeacher, setNewTeacher] = useState({
        username: "",
        password: "",
    });

    const fetchTeachers = async () => {
        try {
            setTeachers(await getTeachers());
        } catch (e) {
            console.log(e)
        }
    }

    function handelNewUsernameChange(event) {
        setNewTeacher(n => ({...n, username: event.target.value}));
    }

    function handelNewPasswordChange(event) {
        setNewTeacher(n => ({...n, password: event.target.value}));
    }

    function generateRandomUsername() {
        setNewTeacher(n => ({...n, username: generateRandomString(12)}));
    }

    function generateRandomPassword() {
        setNewTeacher(n => ({...n, password: generateRandomString(12)}));
    }


    const addNewTeacher = async () => {
        try {
            if (newTeacher.username !== "" && newTeacher.password !== "") {
                await createTeacher(newTeacher).then(
                    setNewTeacher({
                        username: "",
                        password: "",
                    }));
                fetchTeachers()
            }
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchTeachers()
    }, []);


    return (
        <>
            <p className="text-xl font-medium text-center mb-10">Teachers</p>
            <div className="flex">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Username:
                    <input type="text" className="grow" required value={newTeacher.username}
                           onChange={handelNewUsernameChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomUsername()}>Generate
                    random
                </button>
            </div>
            <div className="flex mt-5">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Password:
                    <input type="text" className="grow" required value={newTeacher.password}
                           onChange={handelNewPasswordChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomPassword()}>Generate
                    random
                </button>
            </div>
            <div className="w-1/3 flex justify-center">
                <button className="btn btn-success mt-5 w-1/2" onClick={() => addNewTeacher()}>Add</button>
            </div>

            <table className="table mt-10">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Group</th>
                </tr>
                </thead>
                <tbody>
                {teachers.length ?
                    teachers.map((teacher) =>
                        <tr>
                            <td>{teacher.id}</td>
                            <td>{`${teacher.firstName} ${teacher.lastName}` === "null null" ? "Name is not given" :
                                `${teacher.firstName} ${teacher.lastName}`}</td>
                            <td>{teacher.email}</td>
                            <td>{teacher.classGroup}</td>
                        </tr>
                    ) : <></>}
                </tbody>
            </table>

        </>
    )
}

export default TeacherProfileManagement