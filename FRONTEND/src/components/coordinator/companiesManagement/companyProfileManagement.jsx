import {useState} from "react";
import AllCompaniesTable from "./allCompaniesTable.jsx";
import CreateNewCompany from "./createNewCompany.jsx";


function CompanyProfileManagement() {
    const [activeTab, setActiveTab] = useState(0);

    const handleTabSwitch = (tabNum) => {
        setActiveTab(tabNum);
    }


    return(
        <>
            <p className="text-xl font-medium text-center mb-10">Companies</p>
            <div role="tablist" className="tabs tabs-bordered w-1/2 mx-auto mb-5">
                <a role="tab" className={activeTab === 0 ? "tab tab-active" : "tab"} onClick={() => handleTabSwitch(0)}>All
                    companies</a>
                <a role="tab" className={activeTab === 1 ? "tab tab-active" : "tab"} onClick={() => handleTabSwitch(1)}>Create
                    new company</a>
            </div>
            {activeTab === 0 ? <AllCompaniesTable/> : activeTab === 1 ? <CreateNewCompany/> : <></>}

        </>
    )
}

export default CompanyProfileManagement