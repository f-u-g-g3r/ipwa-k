import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getCvPdf, getMotivationLetter, getStudent} from "../../services/StudentService.jsx";
import {getResume} from "../../services/ResumeService.jsx";

function ShowStudentProfile() {
    let {id} = useParams();
    const [student, setStudent] = useState({});
    const [resume, setResume] = useState({});
    const [cv, setCv] = useState("");
    const [motivationLetter, setMotivationLetter] = useState("");
    const [action, setAction] = useState(1);

    const fetchStudent = async () => {
        try {
            setStudent(await getStudent(id));
        } catch (e) {
            console.log(e);
        }
    }

    const fetchResume = async () => {
        try {
            if (student.resume !== undefined) {
                setResume(await getResume(student.resume).then(
                    async (resume) => {
                        if (resume.cv !== null) {
                            setCv(await getCvPdf(resume.cv));
                        }
                        if (resume.motivationLetter !== null) {
                            setMotivationLetter(await getMotivationLetter(resume.motivationLetter));
                        }
                    }
                ));
            }
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchStudent();
    }, []);

    useEffect(() => {
        fetchResume()
    }, [student]);



    return (
        <>
            <div className="flex justify-center">
                <Link to={`/home?action=1`} className="btn btn-neutral w-1/6 mt-10 mx-auto">Back</Link>
            </div>
            <div className="mt-10 mb-20 flex">
                <div className="w-1/2">
                    <p className="text-3xl font-bold">{`${student.firstName} ${student.lastName}`}</p>
                    <p className="text-xl font-semibold mt-2">{student.email}</p>
                    <p className="text-xl font-semibold mt-2">{`Student's group: ${student.classGroup}`}</p>
                </div>

                <div className="divider lg:divider-horizontal"></div>

                <div className="flex justify-center w-1/2">
                    <div>
                        <div className="flex mb-5">
                            <ul className="menu menu-vertical text-lg lg:menu-horizontal bg-gray-300 ">
                                <li><a onClick={() => setAction(1)}
                                       className={action === 1 ? "bg-success" : "" + "hover:bg-gray-400"}>
                                    CV</a></li>
                                <li><a onClick={() => setAction(2)}
                                       className={action === 2 ? "bg-success" : "" + "hover:bg-gray-400"}>
                                    Motivation Letter</a></li>
                            </ul>
                        </div>

                        <div className="w-full">
                            {action === 1 ?
                                <div>
                                    {cv !== "" ? <img src={cv}/> : <>Cv is not loaded</>}
                                </div> :
                                <div>
                                    {motivationLetter !== "" ? <img src={motivationLetter}/> : <>Motivation letter is not
                                        loaded</>}
                                </div>
                            }
                        </div>


                    </div>
                </div>
            </div>
        </>
    )
}

export default ShowStudentProfile