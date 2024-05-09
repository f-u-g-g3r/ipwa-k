import {useState} from "react";
import {createStudent, generateRandomString} from "../../../services/AuthService.jsx";

export default function CreateNewStudent() {
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

    const addNewStudent = async () => {
        try {
            if (newStudent.username !== "" && newStudent.password !== "") {
                await createStudent(newStudent).then(
                    setNewStudent({
                        username: "",
                        password: "",
                    }));
            }
        } catch (e) {
            console.log(e)
        }
    }
    return (
        <div>
            <div className="flex flex-wrap justify-center">
                <label className="input input-bordered flex items-center gap-2">
                    Username:
                    <input type="text" className="grow" required value={newStudent.username}
                           onChange={handelNewUsernameChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomUsername()}>Generate
                    random
                </button>
            </div>

            <div className="flex flex-wrap justify-center mt-5">
                <label className="input input-bordered flex items-center gap-2">
                    Password:
                    <input type="text" className="grow" required value={newStudent.password}
                           onChange={handelNewPasswordChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomPassword()}>Generate
                    random
                </button>
            </div>
            <div className="flex justify-center">
                <button className="btn btn-success mt-5 w-1/4" onClick={() => addNewStudent()}>Add</button>
            </div>
        </div>
    )
}