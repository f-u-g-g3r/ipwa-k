import {useEffect, useState} from "react";
import {createCompany, generateRandomString} from "../../../services/AuthService.jsx";
import {getCompanies, getCompaniesByPage} from "../../../services/CompanyService.jsx";
import {Link} from "react-router-dom";
import {getStudentsByPage} from "../../../services/StudentService.jsx";
import Pagination from "../../pagination/pagination.jsx";


function CompanyProfileManagement() {
    const [companies, setCompanies] = useState({content: []});
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

    const fetchCompanies = async (pageNumber = 0) => {
        try {
            setCompanies(await getCompaniesByPage(pageNumber));
        } catch (e) {
            console.log(e)
        }
    }

    const addNewCompany = async () => {
        try {
            if (newCompany.username !== "" && newCompany.password !== "") {
                await createCompany(newCompany).then(
                    setNewCompany({
                        username: "",
                        password: "",
                    }));
                fetchCompanies()
            }
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchCompanies()
    }, []);



    return(
        <>
            <p className="text-xl font-medium text-center mb-10">Companies</p>

            <div className="flex">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Username:
                    <input type="text" className="grow" required value={newCompany.username}
                           onChange={handelNewUsernameChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomUsername()}>Generate
                    random
                </button>
            </div>
            <div className="flex mt-5">
                <label className="input input-bordered flex items-center gap-2 w-1/3">
                    Password:
                    <input type="text" className="grow" required value={newCompany.password}
                           onChange={handelNewPasswordChange}/>
                </label>
                <button type="submit" className="btn btn-success ms-2" onClick={() => generateRandomPassword()}>Generate
                    random
                </button>
            </div>
            <div className="w-1/3 flex justify-center">
                <button className="btn btn-success mt-5 w-1/2" onClick={() => addNewCompany()}>Add</button>
            </div>

            {companies.content.length ? (
                <>
                <table className="table text-center mt-10">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Posts</th>
                    </tr>
                    </thead>
                    <tbody>
                    {companies.content.map((company) => (
                        <tr key={company.id}>
                            <td><p>{company.id}</p></td>
                            <td><p>{company.name}</p></td>
                            <td><p>{company.email}</p></td>
                            <td><p>{company.posts.length}</p></td>
                            <td>
                                <a className="btn btn-info mx-1">Show</a>
                                <Link to={`/edit-company/${company.id}`} className="btn btn-warning mx-1">Edit</Link></td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <Pagination data={companies} fetchAction={fetchCompanies}/>
                </>
            ) : <></>}
        </>
    )
}

export default CompanyProfileManagement