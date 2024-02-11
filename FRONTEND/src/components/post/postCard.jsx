import {useEffect, useState} from "react";
import {getCompany, getLogo} from "../../services/CompanyService.jsx";
import {Link} from "react-router-dom";


function PostCard(props) {

    const [company, setCompany] = useState("");
    const [logo, setLogo] = useState("http://via.placeholder.com/200x200");

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
            <Link to={`/posts/${props.post.id}`} className="card lg:card-side bg-base-100 hover:bg-base-300 shadow-lg my-10">
                <figure>
                    <img  style={{width:"224px", height:"224px"}} src={logo} alt="Company Logo"/>
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
        </>
    )
}

export default PostCard