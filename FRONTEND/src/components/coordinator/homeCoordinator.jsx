import {useEffect, useState} from "react";
import StudentProfileManagement from "./studentsManagement/studentProfileManagement.jsx";
import CompanyProfileManagement from "./companiesManagement/companyProfileManagement.jsx";
import TeacherProfileManagement from "./teachersManagement/teacherProfileManagement.jsx";
import ShowGroups from "./groupsManegement/showGroups.jsx";
import {useNavigate} from "react-router-dom";

function HomeCoordinator() {
    const [action, setAction] = useState(0);
    const urlParams = new URLSearchParams(window.location.search);
    const actionParam = urlParams.get('action');
    const navigate = useNavigate();

    const handleActionChange = (actionToSet) => {
        setAction(actionToSet);
    }

    useEffect(() => {
        navigate(`/home?action=${action}`)
    }, [action])

    useEffect(() => {
        switch (actionParam) {
            case "1":
                setAction(1);
                break;
            case "2":
                setAction(2);
                break;
            case "3":
                setAction(3);
                break;
            case "4":
                setAction(4);
                break;
        }
    });

    return(
        <>
            <div className="flex">
                <div className="drawer lg:drawer-open">
                    <input id="my-drawer-2" type="checkbox" className="drawer-toggle" />
                    <div className="drawer-content flex flex-col px-20 pt-5 bg-base-200">
                        {action === 1 ? <StudentProfileManagement/> :
                            action === 2 ? <CompanyProfileManagement/> :
                                action === 3 ? <TeacherProfileManagement/> :
                                action === 4 ? <ShowGroups/> : <></>}
                        <label htmlFor="my-drawer-2" className="btn btn-primary drawer-button lg:hidden">Open drawer</label>
                    </div>
                    <div className="drawer-side">
                        <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay"></label>
                        <ul className="menu p-4 w-80 min-h-full bg-base-300 text-base-content text-xl">
                            <li><a onClick={() => handleActionChange(1)} className={action === 1 ? 'bg-gray-300' : ''}>Student profile management</a></li>
                            <li><a onClick={() => handleActionChange(2)} className={action === 2 ? 'bg-gray-300' : ''}>Company profile management</a></li>
                            <li><a onClick={() => handleActionChange(3)} className={action === 3 ? 'bg-gray-300' : ''}>Teacher profile management</a></li>
                            <li><a onClick={() => handleActionChange(4)} className={action === 4 ? 'bg-gray-300' : ''}>Groups management</a></li>
                        </ul>

                    </div>
                </div>


            </div>
        </>
    )
}

export default HomeCoordinator