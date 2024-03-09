import React, {useContext, useEffect, useRef, useState} from "react";
import {getCompany, getLogo} from "../../services/CompanyService.jsx";
import {Link, redirect, useNavigate} from "react-router-dom";
import {ActionContext} from "../company/homeCompany.jsx";
import {deletePostById} from "../../services/PostService.jsx";
import DeleteModal from "./deleteModal.jsx";
import {ActionContextFetchPosts} from "../company/companyPosts.jsx";


function PostCard(props) {

    const [company, setCompany] = useState("");
    const [logo, setLogo] = useState("http://via.placeholder.com/200x200");
    const [isModalOpen, setIsModalOpen] = useState(false);
    const value = useContext(ActionContext);
    const fetchPosts = useContext(ActionContextFetchPosts);

    const fetchCompany = async () => {
        try {
            setCompany(await getCompany(props.post.company));
        } catch (e) {
            console.log(e);
        }
    }


    const openModal = () => {
        setIsModalOpen(true);
    }

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const fetchLogo = async () => {
        try {
            if (company.logoPath !== null) {
                setLogo(await getLogo(company.logoPath));
            }
        } catch (e) {
            console.log(e)
        }
    }


    const handleDeletePost = async (id) => {
        try {
            await deletePostById(id);
            fetchPosts();

        } catch (error) {
            console.error("error", error);
        }
    };

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
            <div className="flex justify-center">
                <Link to={props.isCheck ? `/posts/${props.post.id}?home=true` : `/posts/${props.post.id}`}
                      className="card lg:card-side bg-base-100 hover:bg-base-300 shadow-lg my-5 w-5/6 h-56">
                    <figure>
                        <img className="w-56" src={logo} alt="Company Logo"/>
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
                {props.isCheck ?
                    <>
                        <div className="card-body w-2/5 my-10">
                            <p className="text-2xl w-full">Students applied: {props.post.postStudents.length}</p>
                            <button onClick={() => {
                                value[0](2);
                                value[1](props.post.id)
                            }} className="btn btn-secondary text-xl h-14">Show info
                            </button>
                        </div>
                        <div className="flex items-center w-1/5 ms-10">
                            <button className="btn btn-error" onClick={() => openModal()}>
                                Delete Post
                            </button>

                        </div>
                        <ActionContext.Provider value={[handleDeletePost, closeModal]}>
                            {isModalOpen ? <DeleteModal post={props.post}/> : <></>}
                        </ActionContext.Provider>
                    </>
                    : <></>}
            </div>

        </>
    )
}

export default PostCard