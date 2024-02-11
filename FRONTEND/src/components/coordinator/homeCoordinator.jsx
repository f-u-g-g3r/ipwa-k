import {useState} from "react";
import StudentProfileManagement from "./studentProfileManagement.jsx";
import CompanyProfileManagement from "./companyProfileManagement.jsx";
import TeacherProfileManagement from "./teacherProfileManagement.jsx";

function HomeCoordinator() {
    const [action, setAction] = useState(0);

    const handleActionChange = (actionToSet) => {
        setAction(actionToSet);
    }

    return(
        <>
            <div className="flex">
                <div className="drawer lg:drawer-open">
                    <input id="my-drawer-2" type="checkbox" className="drawer-toggle" />
                    <div className="drawer-content flex flex-col px-20 pt-5 bg-base-200">
                        {action === 1 ? <StudentProfileManagement/> :
                            action === 2 ? <CompanyProfileManagement/> :
                                action === 3 ? <TeacherProfileManagement/> : <></>}
                        <label htmlFor="my-drawer-2" className="btn btn-primary drawer-button lg:hidden">Open drawer</label>
                    </div>
                    <div className="drawer-side">
                        <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay"></label>
                        <ul className="menu p-4 w-80 min-h-full bg-base-300 text-base-content">
                            <li><a onClick={() => handleActionChange(1)}>Student profile management</a></li>
                            <li><a onClick={() => handleActionChange(2)}>Company profile management</a></li>
                            <li><a onClick={() => handleActionChange(3)}>Teacher profile management</a></li>
                        </ul>

                    </div>
                </div>


            </div>
        </>
    )
}

export default HomeCoordinator