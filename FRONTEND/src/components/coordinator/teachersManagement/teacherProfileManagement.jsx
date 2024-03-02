import {useEffect, useState} from "react";
import {createTeacher, generateRandomString} from "../../../services/AuthService.jsx";
import {getTeachers, getTeachersByPage} from "../../../services/TeacherService.jsx";
import {Link} from "react-router-dom";
import Pagination from "../../pagination/pagination.jsx";

function TeacherProfileManagement() {

    const [teachers, setTeachers] = useState({content: []});
    const [newTeacher, setNewTeacher] = useState({
        username: "",
        password: "",
    });

    const fetchTeachers = async (pageNumber = 0) => {
        try {
            setTeachers(await getTeachersByPage(pageNumber));
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

            {teachers.content.length ?
                <>
                    <table className="table mt-10">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Group</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {teachers.content.map((teacher) =>
                            <tr key={teacher.id}>
                                <td>{teacher.id}</td>
                                <td>{`${teacher.firstName} ${teacher.lastName}` === "null null" ? "Name is not given" :
                                    `${teacher.firstName} ${teacher.lastName}`}</td>
                                <td>{teacher.email}</td>
                                <td>{teacher.classGroup}</td>
                                <td>
                                    <a className="btn btn-info mx-1">Show</a>
                                    <Link to={`/edit-teacher/${teacher.id}`}
                                          className="btn btn-warning mx-1">Edit</Link>
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                    <Pagination data={teachers} fetchAction={fetchTeachers}/>
                </>
                : <></>
            }

        </>
    )
}

export default TeacherProfileManagement