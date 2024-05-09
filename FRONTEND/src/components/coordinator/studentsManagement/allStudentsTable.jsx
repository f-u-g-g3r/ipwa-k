import {useEffect, useState} from "react";
import {getStudentsByPage} from "../../../services/StudentService.jsx";
import {Link} from "react-router-dom";
import Pagination from "../../pagination/pagination.jsx";

export default function AllStudentsTable() {
    const [students, setStudents] = useState({content: []});


    const fetchStudents = async (pageNumber = 0) => {
        try {
            setStudents(await getStudentsByPage(pageNumber));
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchStudents()
    }, []);


    return (
        <>
            {students.content.length ? (
                <>
                    <table className="table text-center mt-10">
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
    );
}