import {createContext, useEffect, useState} from "react";
import CompanyPosts from "./companyPosts.jsx";
import StudentsTable from "./studentsTable.jsx";
import {Link, useNavigate} from "react-router-dom";


export const ActionContext = createContext();
function HomeCompany() {

    const [action,  setAction] = useState(1);
    const [postId, setPostId] = useState(0);
    const urlParams = new URLSearchParams(window.location.search);
    const actionParam = urlParams.get('action');
    const navigate = useNavigate();

    const handeActionChange = (actionToSet) => {
        setAction(actionToSet)
    }

    useEffect(() => {
        navigate(`/home?action=${action}`)
    }, [action])

    useEffect(() => {
        if (actionParam === "1") {
            setAction(1);
        } else if (actionParam === "2") {
            setAction(2);
        }
    });

    return(
        <>
            <div className="drawer lg:drawer-open">
                <input id="my-drawer-2" type="checkbox" className="drawer-toggle"/>
                <div className="drawer-content flex flex-col px-20 pt-5 bg-base-200">
                    {action === 1 ?
                    <ActionContext.Provider value={[setAction, setPostId]}>
                        <CompanyPosts/>
                    </ActionContext.Provider> :
                        action === 2 ? <StudentsTable postId={postId}/> : <></>}
                    <label htmlFor="my-drawer-2" className="btn btn-primary drawer-button lg:hidden">Open drawer</label>

                </div>
                <div className="drawer-side">
                    <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay"></label>
                    <ul className="menu p-4 w-80 min-h-full bg-base-300 text-base-content text-xl">
                        <li><Link to={`/home?action=1`} onClick={() => handeActionChange(1)} className={action === 1 ? 'bg-gray-300' : ''}>My posts</Link></li>
                        <li><a>Sidebar Item 2</a></li>
                    </ul>

                </div>
            </div>

        </>
    );
}

export default HomeCompany