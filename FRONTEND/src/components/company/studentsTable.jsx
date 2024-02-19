import {useEffect, useState} from "react";
import {findStudentsByPostId} from "../../services/StudentService.jsx";
import {Link} from "react-router-dom";

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
            <Link to={`/home?action=1`} className="btn btn-neutral w-1/12 ms-20">Back</Link>
            <table className="table table-lg">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {students ? (
                    <>
                {students.map((student) => (
                    <tr key={student.id}>
                        <td>{`${student.firstName} ${student.lastName}`}</td>
                        <td className="flex items-center">
                            <button className="btn btn-info">Show profile</button>
                            <div className="ms-10">
                                <button className="btn btn-success my-1 w-20">Accept</button><br />
                                <button className="btn btn-error my-1 w-20">Decline</button>
                            </div>
                        </td>
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