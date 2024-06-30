import {useEffect, useState} from "react";
import {getCompany, getLogo, updateCompany, uploadLogo} from "../../services/CompanyService.jsx";
import {getId} from "../../services/AuthService.jsx";
import {Form, redirect} from "react-router-dom";

export async function actionCompanyProfile({request}) {
    const formData = await request.formData();
    const file = formData.get('file');
    formData.delete('file');
    const companyData = Object.fromEntries(formData);
    await updateCompany(getId(), companyData);
    if (file.name !== "") {
        const fileFormData = new FormData();
        fileFormData.append('file', file, file.name);
        await uploadLogo(getId(), fileFormData);
    }

    const company = await getCompany(getId());
    localStorage.setItem("name", company.name);

    return redirect("/company/profile")
}

function CompanyProfile() {
    const [company, setCompany] = useState({});
    const [pathToLogo, setPathToLogo] = useState("");

    const fetchCompany = async () => {
        try {
            setCompany(await getCompany(getId()));
        } catch (e) {
            console.log(e)
        }
    }

    const fetchLogo = async (logoPath) => {
        try {
            setPathToLogo(await getLogo(logoPath));
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
            <p className="text-2xl font-bold text-center my-10">Edit profile</p>
            <Form method="post" className="w-full" encType="multipart/form-data">
                <div className="text-lg flex justify-center w-full my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Company name</span>
                        </div>
                        <input type="text" defaultValue={company.name} name="name"
                               className="input input-bordered w-full" data-testid="company-name"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-full my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Email</span>
                        </div>
                        <input type="text" defaultValue={company.email} name="email"
                               className="input input-bordered w-full" data-testid="email"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-full my-1">
                    <div className="w-full max-w-lg">
                        <a className="btn btn-neutral my-3">Reset password</a>
                    </div>
                </div>

                <div className="text-lg flex justify-center w-full my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Address</span>
                        </div>
                        <input type="text" defaultValue={company.address} name="address"
                               className="input input-bordered w-full" data-testid="address"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-full my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Phone</span>
                        </div>
                        <input type="text" defaultValue={company.phone} name="phone"
                               className="input input-bordered w-full" data-testid="phone"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-full my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Contacts</span>
                        </div>
                        <input type="text" defaultValue={company.contacts} name="contacts"
                               className="input input-bordered w-full" data-testid="contacts"/>
                    </label>
                </div>

                <div className="text-lg flex justify-center w-full my-1">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Registry code</span>
                        </div>
                        <input type="text" defaultValue={company.registryCode} name="registryCode"
                               className="input input-bordered w-full" data-testid="registry-code"/>
                    </label>
                </div>

                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text text-lg">Company logo</span>
                        </div>
                        <input type="file" name="file" className="file-input file-input-bordered w-full max-w-lg"/>
                    </label>
                </div>
                <div className="flex justify-center my-5">
                {company.logoPath !== null ?

                        <img src={pathToLogo} style={{width:"200px", height:"200px"}} id="myImage" alt="logo"/>
                    :
                    <>Company has no logo yet</>}
                </div>

                <div className="text-lg flex justify-center w-full my-1">
                    <div className="w-full max-w-lg">
                        <p className="text-lg my-3">Username: {company.username}</p>
                        <input type="submit" className="btn btn-success my-3 w-full max-w-lg" value="Update profile" data-testid="submit"/>
                    </div>
                </div>
            </Form>
        </>
    )
}

export default CompanyProfile