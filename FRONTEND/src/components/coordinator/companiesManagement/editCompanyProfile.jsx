import {Form, Link, redirect, useParams} from "react-router-dom";
import {addStudentToGroup, getStudent, updateStudent} from "../../../services/StudentService.jsx";
import {getCompany, getLogo, updateCompany} from "../../../services/CompanyService.jsx";
import {useEffect, useState} from "react";

export async function actionEditCompany({request}) {
    const formData = await request.formData();
    const companyData = Object.fromEntries(formData);

    await updateCompany(companyData, formData.get("companyId"));

    const company = await getCompany(formData.get("companyId"));
    localStorage.setItem("name", company.name);

    return redirect("/home")
}

function EditCompanyProfile() {
    let {id} = useParams();

    const [company, setCompany] = useState({});
    const [logo, setLogo] = useState("");

    const fetchCompany = async () => {
        try {
            setCompany(await getCompany(id));
        } catch (e) {
            console.log(e)
        }
    }

    const fetchLogo = async (logoPath) => {
        try {
            setLogo(await getLogo(logoPath));
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchCompany()
    }, []);

    useEffect(() => {
        fetchLogo(company.logoPath)
    }, [company]);


    return(
        <>
            <div className="flex justify-center">
                <Link to={`/home`} className="btn btn-neutral w-1/6 mt-10 mx-auto">Back</Link>
            </div>
            <p className="text-2xl font-bold text-center my-10">Edit profile</p>
            <Form method="post" className="w-full">
                <div className="text-lg flex justify-center w-ful my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Company name</span>
                        </div>
                        <input type="text" defaultValue={company.name} name="name"
                               className="input input-bordered w-full"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-ful my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Email</span>
                        </div>
                        <input type="text" defaultValue={company.email} name="email"
                               className="input input-bordered w-full"/>
                    </label>
                </div>
                <div className="text-lg flex justify-center w-full my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Username</span>
                        </div>
                        <input type="text" defaultValue={company.username} name="username"
                               className="input input-bordered w-full"/>
                    </label>
                </div>
                <div className="text-lg flex justify-center w-ful my-1">
                    <div className="w-full max-w-lg">
                        <a className="btn btn-neutral my-3">Reset password</a>
                    </div>
                </div>

                <div className="text-lg flex justify-center w-ful my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Address</span>
                        </div>
                        <input type="text" defaultValue={company.address} name="address"
                               className="input input-bordered w-full"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-ful my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Phone</span>
                        </div>
                        <input type="text" defaultValue={company.phone} name="phone"
                               className="input input-bordered w-full"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-ful my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Contacts</span>
                        </div>
                        <input type="text" defaultValue={company.contacts} name="contacts"
                               className="input input-bordered w-full"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-ful my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Registry code</span>
                        </div>
                        <input type="text" defaultValue={company.registryCode} name="registryCode"
                               className="input input-bordered w-full"/>
                    </label>
                </div>
                <div className="flex justify-center my-5">
                {company.logoPath !== null ?
                    <img src={logo} style={{width:"200px", height:"200px"}} id="myImage" alt="logo"/> :
                    <>Company has no logo</>}
                </div>

                <div className="text-lg flex justify-center w-ful my-1">
                    <div className="w-full max-w-lg">
                        <input type="hidden" name="companyId" value={company.id}/>
                        <input type="submit" className="btn btn-success my-3 w-full max-w-lg" value="Update profile" />
                    </div>
                </div>
            </Form>
        </>
    )
}

export default EditCompanyProfile