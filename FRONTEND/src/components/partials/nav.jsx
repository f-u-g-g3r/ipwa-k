import {Link, useLocation} from "react-router-dom";
import {useEffect, useState} from "react";


function Nav() {
    let location = useLocation();
    const [authority, setAuthority] = useState(localStorage.getItem("authority"));
    const [userData, setUserData] = useState("Guest");

    const setData = () => {
        setAuthority(localStorage.getItem("authority"));
        switch (localStorage.getItem("authority")) {
            case "STUDENT":
                setUserData(localStorage.getItem("firstName") + " " + localStorage.getItem("lastName"));
                break;
            case "COMPANY":
                setUserData(localStorage.getItem("name"));
                break;
            case "TEACHER":
                setUserData(localStorage.getItem("firstName") + " " + localStorage.getItem("lastName"));
                break;
            case "COORDINATOR":
                setUserData("")
                break;
            default:
                break;
        }
    }

    const logOut = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("firstName");
        localStorage.removeItem("lastName");
        localStorage.removeItem("name");
        localStorage.removeItem("authority");
        setUserData(u => u = "Guest");
        setAuthority(a => a = "");
    }
    useEffect(() => {
        setData()
    }, [location]);


    return (
        <div className="navbar bg-base-100">
            <div className="navbar-start">
                <div className="dropdown">
                    <div tabIndex={0} role="button" className="btn btn-ghost lg:hidden">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                                  d="M4 6h16M4 12h8m-8 6h16"/>
                        </svg>
                    </div>
                    <ul tabIndex={0}
                        className="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">
                        <li><a>Item 1</a></li>
                        <li>
                            <a>Parent</a>
                            <ul className="p-2">
                                <li><a>Submenu 1</a></li>
                                <li><a>Submenu 2</a></li>
                            </ul>
                        </li>
                        <li><a>Item 3</a></li>
                    </ul>
                </div>
                <Link to={`/home`} className="btn btn-ghost text-xl">Praktika portaal</Link>
            </div>
            <div className="navbar-center hidden lg:flex">
                <ul className="menu menu-horizontal px-1">
                    <li><Link to={`/home`}>Home</Link></li>
                    <li><Link to={`/posts`}>Posts</Link></li>
                    {authority === "COMPANY" || authority === "COORDINATOR" ?
                        <li><Link to={`/posts/new`}>Create a job posting</Link></li> :
                        <></>}
                </ul>
            </div>
            <div className="navbar-end ">
                <div className="dropdown dropdown-hover dropdown-end">
                    <div tabIndex={0} role="button" className="btn btn-active h-auto">
                        <div className="">
                            <p className="text-xl w-full">{userData !== undefined ? userData : 'Guest'}</p>
                            <p className="text-lg font-medium capitalize">{authority}</p>
                        </div>
                    </div>
                    {userData !== "Guest" ?
                        <ul tabIndex={0}
                            className="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-52">

                            <li><Link to={`/${authority.toLowerCase()}/profile`}>My account</Link></li>
                            <li><Link to={`/login`} onClick={logOut}>Log out</Link></li>
                        </ul> : <></>
                    }
                </div>
            </div>
        </div>
    )
}

export default Nav
