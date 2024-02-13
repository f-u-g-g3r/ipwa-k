import {createContext, useState} from "react";
import CompanyPosts from "./companyPosts.jsx";
import StudentsTable from "./studentsTable.jsx";


export const ActionContext = createContext();
function HomeCompany() {

    const [action,  setAction] = useState(0);
    const [postId, setPostId] = useState(0);

    const handeActionChange = (actionToSet) => {
        setAction(actionToSet)
    }
    return(
        <>

            <h1>Home company</h1>
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
                        <li><a onClick={() => handeActionChange(1)} className={action === 1 ? 'bg-gray-300' : ''}>My posts</a></li>
                        <li><a>Sidebar Item 2</a></li>
                    </ul>

                </div>
            </div>

        </>
    );
}

export default HomeCompany