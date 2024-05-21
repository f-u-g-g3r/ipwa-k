import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import StudentProfileManagement from "../coordinator/studentsManagement/studentProfileManagement.jsx";

export default function HomeTeacher() {
    const [action, setAction] = useState(1);
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
        }
    });


    return (
        <>
            <div className="flex">
                <div className="drawer lg:drawer-open h-full">
                    <input id="my-drawer-2" type="checkbox" className="drawer-toggle" />
                    <div className="drawer-content flex flex-col px-20 pt-5 bg-gray-200 pb-20">
                        {action === 1 ? <StudentProfileManagement/> : <></>}
                        <label htmlFor="my-drawer-2" className="btn btn-primary drawer-button lg:hidden">Open drawer</label>
                    </div>
                    <div className="drawer-side h-full">
                        <label htmlFor="my-drawer-2" aria-label="close sidebar" className="drawer-overlay"></label>
                        <ul className="menu p-4 w-80 min-h-full bg-gray-300 text-base-content text-xl ">
                            <li><a onClick={() => handleActionChange(1)} className={action === 1 ? 'bg-gray-300' : ''}>Student profile management</a></li>
                        </ul>

                    </div>
                </div>


            </div>
        </>
    )
}