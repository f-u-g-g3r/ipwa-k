import {useEffect, useState} from "react";
import {findStudentsByPostId} from "../../services/StudentService.jsx";

function StudentsTable(props) {
    const [students, setStudents] = useState([]);

    const fetchStudents = async () => {
        try {
            setStudents(await findStudentsByPostId(props.postId))
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        fetchStudents();
    }, []);


    return(
        <>
            <table className="table table-lg">
                <thead>
                <tr>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {students ? (
                    <>
                {students.map((student) => (
                    <tr>
                        <td>{student.firstName}</td>
                        <td>{student.lastName}</td>
                        <td><button className="btn btn-success">Action</button> </td>
                    </tr>
                ))}
                </>
                ) : <></>}
                </tbody>
            </table>

        </>
    )
}

export default StudentsTable