import {useState} from "react";
import {createCompany, generateRandomString} from "../../../services/AuthService.jsx";

export default function CreateNewCompany() {
    const [newCompany, setNewCompany] = useState({
        username: "",
        password: "",
    });

    function handelNewUsernameChange(event) {
        setNewCompany(n => ({...n, username: event.target.value}));
    }

    function handelNewPasswordChange(event) {
        setNewCompany(n => ({...n, password: event.target.value}));
    }

    function generateRandomUsername() {
        setNewCompany(n => ({...n, username: generateRandomString(12)}));
    }

    function generateRandomPassword() {
        setNewCompany(n => ({...n, password: generateRandomString(12)}));
    }

    const addNewCompany = async () => {
        try {
            if (newCompany.username !== "" && newCompany.password !== "") {
                await createCompany(newCompany).then(
                    setNewCompany({
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
                    <input type="text" className="grow" required value={newCompany.username}
                           onChange={handelNewUsernameChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomUsername()}>Generate
                    random
                </button>
            </div>
            <div className="flex flex-wrap justify-center mt-5">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Password:
                    <input type="text" className="grow" required value={newCompany.password}
                           onChange={handelNewPasswordChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomPassword()}>Generate
                    random
                </button>
            </div>
            <div className="flex justify-center">
                <button className="btn btn-success mt-5 w-1/4" onClick={() => addNewCompany()}>Add</button>
            </div>
        </>
    )
}