import {getAuthority} from "../services/AuthService.jsx";
import StudentProfile from "./studentProfile.jsx";
import CompanyProfile from "./companyProfile.jsx";
import TeacherProfile from "./teacherProfile.jsx";
import CoordinatorProfile from "./coordinatorProfile.jsx";

function Profile() {
    const authority = getAuthority();

    return(
        <>
            {
                authority === "STUDENT" ? <StudentProfile/> :
                    authority === "COMPANY" ? <CompanyProfile/> :
                        authority === "TEACHER" ? <TeacherProfile/> :
                            authority === "COORDINATOR" ? <CoordinatorProfile/> :
                                <></>
            }
        </>
    )
}

export default Profile