import SuccessAlert from "./successAlert.jsx";
import {useState} from "react";
import {generateRandomString} from "../services/AuthService.jsx";
import {createCompany} from "../services/CompanyService.jsx";

function CompanyProfileManagement() {
    const [ success, setSuccess ] = useState()
    const createNewCompany = () => {
        const newUsername = generateRandomString(12);
        const newPassword = generateRandomString(16);
        createCompany({
            username: newUsername,
            password: newPassword
        });

        setSuccess(<SuccessAlert username={newUsername} password={newPassword}/>)
    }
    return(
        <>
            <p className="text-2xl font-medium">Create a new account for company.</p>
            <a className="btn btn-info max-w-xs mt-5" onClick={() => createNewCompany()}>Create</a>

            {success}
        </>
    )
}

export default CompanyProfileManagement