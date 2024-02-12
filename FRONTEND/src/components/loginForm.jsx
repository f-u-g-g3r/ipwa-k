import {auth, isAuth} from "../services/AuthService.jsx";
import {Form, redirect} from "react-router-dom";
import {getStudent} from "../services/StudentService.jsx";
import {getCoordinator} from "../services/CoordinatorService.jsx";
import {getCompany} from "../services/CompanyService.jsx";
import {getTeacher} from "../services/TeacherService.jsx";


export async function action({ request }) {
    const formData = await request.formData();
    const userData = Object.fromEntries(formData);

    try {
        const {data} = await auth(userData);
        const authority = data.authority[0].authority;

        localStorage.setItem("token", data.token);
        localStorage.setItem("authority", authority);
        localStorage.setItem("id", data.id);
        switch (authority) {
            case "STUDENT":
                const student = await getStudent(data.id);
                localStorage.setItem("firstName", student.firstName);
                localStorage.setItem("lastName", student.lastName);
                break;

            case "COMPANY":
                const company = await getCompany(data.id);
                console.log(company)
                localStorage.setItem("name", company.name);
                break;

            case "TEACHER":
                const teacher = await getTeacher(data.id);
                localStorage.setItem("firstName", teacher.firstName);
                localStorage.setItem("lastName", teacher.lastName);
                break;

            case "COORDINATOR":
                const coordinator = await getCoordinator(data.id);
                localStorage.setItem("email", coordinator.email);
                break;

        }
        return redirect("/home");
    } catch (e) {
        return redirect("/login")
    }


}

export function loginLoader() {
    if (isAuth()) {
        return redirect("/home");
    }
    return null;
}


function LoginForm() {


    return (
        <>
            <Form method="post">
                <div className="flex justify-center">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text">Username</span>
                        </div>
                        <input type="text" required name="username" className="input input-bordered w-full max-w-xs" />
                    </label>
                </div>
                <div className="flex justify-center">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text">Password</span>
                        </div>
                        <input type="password" required name="password" className="input input-bordered w-full max-w-xs" />
                    </label>
                </div>
                <div className="flex justify-center my-3">
                    <button className="btn btn-success">Log in</button>
                </div>

            </Form>
        </>
    )
}

export default LoginForm


