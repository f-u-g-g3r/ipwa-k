import {Link} from "react-router-dom";
import Pagination from "../../pagination/pagination.jsx";
import {useEffect, useState} from "react";
import {getCompaniesByPage} from "../../../services/CompanyService.jsx";

export default function AllCompaniesTable() {
    const [companies, setCompanies] = useState({content: []});


    const fetchCompanies = async (pageNumber = 0) => {
        try {
            setCompanies(await getCompaniesByPage(pageNumber));
        } catch (e) {
            console.log(e)
        }
    }



    useEffect(() => {
        fetchCompanies()
    }, []);

    return(
        <>
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