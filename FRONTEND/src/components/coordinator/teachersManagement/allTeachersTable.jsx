import {useEffect, useState} from "react";
import {getTeachersByPage} from "../../../services/TeacherService.jsx";
import {Link} from "react-router-dom";
import Pagination from "../../pagination/pagination.jsx";

export default function AllTeachersTable() {
    const [teachers, setTeachers] = useState({content: []});

    const fetchTeachers = async (pageNumber = 0) => {
        try {
            setTeachers(await getTeachersByPage(pageNumber));
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchTeachers()
    }, []);

    return (
        <>
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