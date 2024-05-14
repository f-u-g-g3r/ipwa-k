import {useState} from "react";
import AllTeachersTable from "./allTeachersTable.jsx";
import CreateNewTeacher from "./createNewTeacher.jsx";


function TeacherProfileManagement() {
    const [activeTab, setActiveTab] = useState(0);

    const handleTabSwitch = (tabNum) => {
        setActiveTab(tabNum);
    }

    return (
        <>
            <p className="text-xl font-medium text-center mb-10">Teachers</p>

            <div role="tablist" className="tabs tabs-bordered w-1/2 mx-auto mb-5">
                <a role="tab" className={activeTab === 0 ? "tab tab-active" : "tab"} onClick={() => handleTabSwitch(0)}>All
                    teachers</a>
                <a role="tab" className={activeTab === 1 ? "tab tab-active" : "tab"} onClick={() => handleTabSwitch(1)}>Create
                    new teacher</a>
            </div>
            {activeTab === 0 ? <AllTeachersTable/> : activeTab === 1 ? <CreateNewTeacher/> : <></>}


        </>
    )
}

export default TeacherProfileManagement