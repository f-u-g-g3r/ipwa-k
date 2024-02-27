import HomeCoordinator from "./coordinator/homeCoordinator.jsx";
import {Link} from "react-router-dom";
import HomeCompany from "./company/homeCompany.jsx";

function Home() {
    const authority = localStorage.getItem('authority');
    return(
        <>

            {authority === "COORDINATOR" ? <HomeCoordinator/> :
                authority === "COMPANY" ? <HomeCompany/> :
                authority === "STUDENT" ?
                    <div className="flex justify-center items-start my-20 min-h-screen">
                        <div className="hero-content text-center w-full">
                            <div className="w-full">
                                <h1 className="text-5xl font-bold mb-20">Welcome {`${localStorage.getItem("firstName")} ${localStorage.getItem("lastName")}`}</h1>
                                <Link to={`/posts`} className="btn btn-success text-2xl w-1/3 mx-4">Job posts</Link>
                                <Link to={`/my-applications`} className="btn btn-success text-2xl w-1/3 mx-4">My applications</Link>
                                <Link to={`/student/profile`} className="btn btn-warning text-2xl w-1/3 mt-8">Configure profile info</Link>
                            </div>
                        </div>
                    </div> :

                    <><h1>Home page!</h1></>}


        </>
    )
}

export default Home