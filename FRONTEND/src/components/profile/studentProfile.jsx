import {useEffect, useRef, useState} from "react";
import {
    getCvPdf,
    getMotivationLetter,
    getStudent,
    updateStudent,
    uploadCv,
    uploadMotivationLetter
} from "../../services/StudentService.jsx";
import {Link, useNavigate} from "react-router-dom";
import {getId} from "../../services/AuthService.jsx";
import {getResume} from "../../services/ResumeService.jsx";
import {getGroupByName} from "../../services/ClassGroupService.jsx";
import {getTeacher} from "../../services/TeacherService.jsx";

function StudentProfile() {

    const [student, setStudent] = useState({});
    const [cv, setCv] = useState("");
    const [motivationLetter, setMotivationLetter] = useState("");
    const [resume, setResume] = useState({});
    const [action, setAction] = useState(1);
    const [teacher, setTeacher] = useState("");

    const cvInputRef = useRef();
    const letterInputRef = useRef();

    let navigate = useNavigate();

    const fetchStudent = async () => {
        try {
            setStudent(await getStudent(getId()));
        } catch (e) {
            console.log(e)
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

    const handleSubmit = async (event) => {
        event.preventDefault()
        const formData = new FormData(event.target);
        const studentData = Object.fromEntries(formData);
        await updateStudent(studentData, getId());

        const student = await getStudent(getId());
        localStorage.setItem("firstName", student.firstName);
        localStorage.setItem("lastName", student.lastName);

        return navigate("/student/profile")
    }

    const handleCvUploading = async (event) => {
        event.preventDefault()
        const formData = new FormData(event.target);
        const file = formData.get('file');

        if (file.name !== "") {
            const fileFormData = new FormData();
            fileFormData.append('file', file, file.name);
            await uploadCv(getId(), fileFormData).then(
                fetchResume
            );
        }

        return navigate("/student/profile")
    }
    const handleLetterUploading = async (event) => {
        event.preventDefault()
        const formData = new FormData(event.target);
        const file = formData.get('file');

        if (file.name !== "") {
            const fileFormData = new FormData();
            fileFormData.append('file', file, file.name);
            await uploadMotivationLetter(getId(), fileFormData).then(
                fetchResume
            );
        }

        return navigate("/student/profile")
    }

    const fetchTeacher = async () => {
        if (student.classGroup !== undefined) {
            if (student.classGroup !== null) {
                await getGroupByName(student.classGroup).then(async (data) => {
                    if (data.teacher !== null) {
                        await getTeacher(data.teacher).then((data) => {
                            setTeacher(`${data.firstName} ${data.lastName}`)
                        });
                    }
                });
            }
        }
    }

    useEffect(() => {
        fetchStudent()
    }, []);

    useEffect(() => {
        fetchResume()
        fetchTeacher()
    }, [student]);

    useEffect(() => {
        if (action === 1) {
            cvInputRef.current.value = null;
        }
        if (action === 2) {
            letterInputRef.current.value = null;
        }
    }, [action]);

    return (
        <>
            <div className="flex justify-center">
                <Link to={`/home`} className="btn btn-neutral w-1/12 my-10">Home</Link>
            </div>
            <p className="text-2xl font-bold text-center">Edit profile</p>

            <div className="flex justify-end mb-5">
                <ul className="menu menu-vertical text-lg lg:menu-horizontal bg-gray-300 ">
                    <li><a onClick={() => setAction(1)}
                           className={action === 1 ? "bg-success" : "" + "hover:bg-gray-400"}>
                        CV</a></li>
                    <li><a onClick={() => setAction(2)}
                           className={action === 2 ? "bg-success" : "" + "hover:bg-gray-400"}>
                        Motivation Letter</a></li>
                </ul>
            </div>
            <div className="flex justify-center ">
                <form method="post" className="w-1/2" onSubmit={handleSubmit}>
                    <div className="text-lg flex justify-center w-full">
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text text-lg">First name</span>
                            </div>
                            <input type="text" defaultValue={student.firstName} name="firstName"
                                   className="input input-bordered w-full"/>
                        </label>
                    </div>

                    <div className="text-lg flex justify-center w-full">
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text text-lg">Last name</span>
                            </div>
                            <input type="text" defaultValue={student.lastName} name="lastName"
                                   className="input input-bordered w-full"/>
                        </label>
                    </div>
                    <div className="text-lg flex justify-center w-full">
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text text-lg">Email</span>
                            </div>
                            <input type="text" defaultValue={student.email} name="email"
                                   className="input input-bordered w-full"/>
                        </label>
                    </div>
                    <div className="text-lg flex justify-center w-full">
                        <div className="w-full max-w-lg">
                            <a className="btn btn-neutral my-3">Reset password</a>
                            <p className="text-lg my-3">Username: {student.username}</p>
                            <p className="text-lg my-3">Group: {student.classGroup}</p>
                            <p className="text-lg my-3">Class teacher: {teacher}</p>

                            <input type="submit" className="btn btn-success my-3 w-full" value="Update profile"/>
                        </div>
                    </div>
                </form>
                <div className="divider lg:divider-horizontal"></div>
                <div className="w-1/2">
                    {action === 1 ?
                        <div>
                            <div className="flex justify-center mb-5">
                                <form onSubmit={handleCvUploading} encType="multipart/form-data"
                                      className="card-body bg-base-300 shadow-lg">
                                    <span className="label-text text-lg">Upload CV</span>
                                    <div className="flex w-full">
                                        <input type="file" name="file" ref={cvInputRef}
                                               className="file-input file-input-bordered w-1/2"/>
                                        <input type="submit" value="Update" className="btn btn-success ms-5"/>
                                    </div>

                                </form>
                            </div>

                            <img src={cv}/>

                        </div> :
                        <div>
                            <div className="flex justify-center mb-5">
                                <form onSubmit={handleLetterUploading} encType="multipart/form-data"
                                      className="card-body bg-base-300 shadow-lg">
                                    <span className="label-text text-lg">Upload Motivation Letter</span>
                                    <div className="flex w-full">
                                        <input type="file" name="file" ref={letterInputRef}
                                               className="file-input file-input-bordered w-1/2"/>
                                        <input type="submit" value="Update" className="btn btn-success ms-5"/>
                                    </div>

                                </form>
                            </div>

                            <img src={motivationLetter}/>
                        </div>
                    }

                </div>
            </div>
        </>
    )
}

export default StudentProfile