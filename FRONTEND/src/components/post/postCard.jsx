import {useEffect, useState} from "react";
import {getCompany} from "../../services/CompanyService.jsx";
import {Link} from "react-router-dom";


function PostCard(props) {

    const [company, setCompany] = useState("");

    const fetchCompany = async () => {
        try {
            setCompany(await getCompany(props.post.company));
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        fetchCompany()
    }, []);

    return (
        <>
            <Link to={`/posts/${props.post.id}`} className="card lg:card-side bg-base-100 hover:bg-base-300 shadow-lg my-10">
                <figure>
                    <img src="http://via.placeholder.com/200x200" style={{width:"224px", height:"224px"}} alt="Company Logo"/>
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
                </div>
            </Link>
        </>
    )
}

export default PostCard