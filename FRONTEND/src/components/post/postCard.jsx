import {useContext, useEffect, useState} from "react";
import {getCompany, getLogo} from "../../services/CompanyService.jsx";
import {Link, useOutletContext} from "react-router-dom";
import {ActionContext} from "../company/homeCompany.jsx";


function PostCard(props) {

    const [company, setCompany] = useState("");
    const [logo, setLogo] = useState("http://via.placeholder.com/200x200");
    const value = useContext(ActionContext);

    const fetchCompany = async () => {
        try {
            setCompany(await getCompany(props.post.company));
        } catch (e) {
            console.log(e);
        }
    }

    const fetchLogo = async () => {
        try {
            if (company.logoPath !== null) {
                setLogo(await getLogo(company.logoPath));
            }
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchCompany()
    }, []);

    useEffect(() => {
        if (company.logoPath !== undefined) {
            fetchLogo()
        }
    }, [company]);

    return (
        <>
            <div className="flex">
                <Link to={`/posts/${props.post.id}`}
                      className="card lg:card-side bg-base-100 hover:bg-base-300 shadow-lg my-10 w-full">
                    <figure>
                        <img style={{width: "224px", height: "224px"}} src={logo} alt="Company Logo"/>
                    </figure>
                    <div className="card-body">
                        <h2 className="card-title">{props.post.workName}</h2>
                        <p>{company.name}</p>
                        <li className="flex text-lg font-medium text-gray-400">
                            <ul className="mx-5">skill1</ul>
                            <ul className="mx-5">skill2</ul>
                            <ul className="mx-5">skill3</ul>
                            <ul className="mx-5">skill4</ul>
                        </li>

                        <p>Expiration date: {props.post.expiryDate}</p>
                        <p>Date posted: {props.post.datePosted}</p>
                    </div>
                </Link>
                {props.isCheck === true ?
                    <div className="card-body w-2/5 my-20">
                        <p className="text-2xl w-full">Students applied: {props.post.students.length}</p>
                        <button onClick={() => {value[0](2); value[1](props.post.id)}} className="btn btn-secondary text-xl h-14">Show info</button>
                    </div>
                    : <></>}
            </div>

        </>
    )
}

export default PostCard