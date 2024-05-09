import {useState} from "react";
import AllStudentsTable from "./allStudentsTable.jsx";
import CreateNewStudent from "./createNewStudent.jsx";


function StudentProfileManagement() {
    const [activeTab, setActiveTab] = useState(0);

    const handleTabSwitch = (tabNum) => {
        setActiveTab(tabNum);
    }

    return (
        <>
            <p className="text-xl font-medium text-center mb-5">Students</p>
            <div role="tablist" className="tabs tabs-bordered w-1/2 mx-auto mb-5">
                <a role="tab" className={activeTab === 0 ? "tab tab-active" : "tab"} onClick={() => handleTabSwitch(0)}>All students</a>
                <a role="tab" className={activeTab === 1 ? "tab tab-active" : "tab"}  onClick={() => handleTabSwitch(1)}>Create new student</a>
            </div>

            {activeTab === 0 ? <AllStudentsTable/> : activeTab === 1 ? <CreateNewStudent/> : <></>}
        </>
    )
}

export default StudentProfileManagement