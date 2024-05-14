import {useState} from "react";
import {createTeacher, generateRandomString} from "../../../services/AuthService.jsx";

export default function CreateNewTeacher() {
    const [newTeacher, setNewTeacher] = useState({
        username: "",
        password: "",
    });

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
            }
        } catch (e) {
            console.log(e)
        }
    }
    return (
        <>
            <div className="flex flex-wrap justify-center mt-10">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Username:
                    <input type="text" className="grow" required value={newTeacher.username}
                           onChange={handelNewUsernameChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomUsername()}>Generate
                    random
                </button>
            </div>
            <div className="flex flex-wrap justify-center mt-5">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Password:
                    <input type="text" className="grow" required value={newTeacher.password}
                           onChange={handelNewPasswordChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomPassword()}>Generate
                    random
                </button>
            </div>
            <div className="flex justify-center">
                <button className="btn btn-success mt-5 w-1/4" onClick={() => addNewTeacher()}>Add</button>
            </div>
        </>
    )
}