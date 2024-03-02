import {useEffect, useState} from "react";
import {getStudentsByPage} from "../../../services/StudentService.jsx";
import {Link} from "react-router-dom";
import {createStudent, generateRandomString} from "../../../services/AuthService.jsx";
import Pagination from "../../pagination/pagination.jsx";

function StudentProfileManagement() {

    const [students, setStudents] = useState({content: []});
    const [newStudent, setNewStudent] = useState({
        username: "",
        password: "",
    });

    function handelNewUsernameChange(event) {
        setNewStudent(n => ({...n, username: event.target.value}));
    }

    function handelNewPasswordChange(event) {
        setNewStudent(n => ({...n, password: event.target.value}));
    }

    function generateRandomUsername() {
        setNewStudent(n => ({...n, username: generateRandomString(12)}));
    }

    function generateRandomPassword() {
        setNewStudent(n => ({...n, password: generateRandomString(12)}));
    }

    const fetchStudents = async (pageNumber = 0) => {
        try {
            setStudents(await getStudentsByPage(pageNumber));
        } catch (e) {
            console.log(e)
        }
    }

    const addNewStudent = async () => {
        try {
            if (newStudent.username !== "" && newStudent.password !== "") {
                await createStudent(newStudent).then(
                    setNewStudent({
                        username: "",
                        password: "",
                    }));
                fetchStudents()
            }
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchStudents()
    }, []);

    useEffect(() => {
        console.log(students)
    }, [students]);

    return (
        <>
            <p className="text-xl font-medium text-center mb-10">Students</p>

            <div className="flex">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Username:
                    <input type="text" className="grow" required value={newStudent.username}
                           onChange={handelNewUsernameChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomUsername()}>Generate
                    random
                </button>
            </div>
            <div className="flex mt-5">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Password:
                    <input type="text" className="grow" required value={newStudent.password}
                           onChange={handelNewPasswordChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomPassword()}>Generate
                    random
                </button>
            </div>
            <div className="w-1/3 flex justify-center">
                <button className="btn btn-success mt-5 w-1/2" onClick={() => addNewStudent()}>Add</button>
            </div>

            {students.content.length ? (
                <>
                    <table className="table mt-10">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Group</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {students.content.map((student) => (
                            <tr key={student.id}>
                                <td><p>{student.firstName + " " + student.lastName}</p></td>
                                <td><p>{student.classGroup}</p></td>
                                <td><p>{student.accountStatus}</p></td>
                                <td>
                                    <Link to={`/show-student/${student.id}`} className="btn btn-info mx-1">Show</Link>
                                    <Link to={`/edit-student/${student.id}`}
                                          className="btn btn-warning mx-1">Edit</Link></td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <Pagination data={students} fetchAction={fetchStudents}/>
                </>

            ) : <></>}
        </>
    )
}

export default StudentProfileManagement