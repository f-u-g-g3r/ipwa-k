import HomeCoordinator from "./coordinator/homeCoordinator.jsx";
import {Link} from "react-router-dom";
import HomeCompany from "./company/homeCompany.jsx";

function Home() {
    const authority = localStorage.getItem('authority');
    return(
        <>

            {authority === "COORDINATOR" ? <HomeCoordinator/> :
                authority === "STUDENT" ?
                    <div className="hero min-h-screen">
                        <div className="hero-content text-center">
                            <div className="max-w-md">
                                <h1 className="text-5xl font-bold">Welcome</h1>
                                <p className="py-6">Some text</p>
                                <Link to={`/posts`} className="btn btn-primary">Job posts</Link>
                            </div>
                        </div>
                    </div> :
                        authority === "COMPANY" ? <>
                            <HomeCompany/>
                            </>:
                    <><h1>Home page!</h1></>}


        </>
    )
}

export default Home