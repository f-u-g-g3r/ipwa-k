import {useEffect, useState} from "react";
import {getStudents} from "../../../services/StudentService.jsx";
import {Link} from "react-router-dom";

function StudentProfileManagement() {

    const [students, setStudents] = useState({});

    const fetchStudents = async () => {
        try {
            setStudents(await getStudents());
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchStudents()
    }, []);

    return(
        <>
            <p className="text-xl font-medium text-center mb-10">Students</p>
            {students.length ? (
                <table className="table text-center">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Group</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {students.map((student) => (
                        <tr key={student.id}>
                            <td><p>{student.firstName + " " + student.lastName}</p></td>
                            <td><p>{student.classGroup}</p></td>
                            <td><p>{student.accountStatus}</p></td>
                            <td>
                                <a className="btn btn-info mx-1">Show</a>
                                <Link to={`/edit-student/${student.id}`} className="btn btn-warning mx-1">Edit</Link></td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : <></>}
        </>
    )
}

export default StudentProfileManagement