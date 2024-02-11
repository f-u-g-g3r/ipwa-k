import {savePost, uploadPostPdf} from "../../services/PostService.jsx";
import {Form, redirect} from "react-router-dom";
import {getId} from "../../services/AuthService.jsx";
import {uploadLogo} from "../../services/CompanyService.jsx";

export async function actionPostForm({request}) {
    const formData = await request.formData();
    const file = formData.get('file');
    formData.delete('file');

    const postData = Object.fromEntries(formData);
    const {data} = await savePost(postData);

    if (file.name !== "") {
        const fileFormData = new FormData();
        fileFormData.append('file', file, file.name);
        await uploadPostPdf(data.id, fileFormData);
    }

    return redirect("/posts")
}

function PostForm() {


    return (
        <div className="">
            <Form method="post" encType="multipart/form-data">
                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg my-2">
                        <div className="label">
                            <span className="label-text">Töö nimetus</span>
                        </div>
                        <input type="text" name="workName" className="input input-bordered w-full max-w-lg" required/>
                    </label>
                </div>

                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg my-2">
                        <div className="label">
                            <span className="label-text">Töö kirjeldus</span>
                        </div>
                        <textarea className="textarea textarea-bordered w-full max-w-lg h-24 max-h-96"
                                  name="workDescription" required></textarea>
                    </label>
                </div>

                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg my-2">
                        <div className="label">
                            <span className="label-text">Palk</span>
                        </div>
                        <input type="text" name="salary" className="input input-bordered w-full max-w-lg"/>
                    </label>
                </div>

                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg my-2">
                        <div className="label">
                            <span className="label-text">Nõuded</span>
                        </div>
                        <input type="text" name="claims" className="input input-bordered w-full max-w-lg" required/>
                    </label>
                </div>

                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg my-2">
                        <div className="label">
                            <span className="label-text">Lisainformatsioon</span>
                        </div>
                        <textarea name="additionalInfo"
                                  className="textarea textarea-bordered w-full max-w-lg h-24 max-h-96"></textarea>
                    </label>
                </div>

                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg my-2">
                        <div className="label">
                            <span className="label-text">Aegumiskuupäev</span>
                        </div>
                        <input type="date" name="expiryDate" className="input input-bordered w-full max-w-lg" required/>
                    </label>
                </div>

                <div className="flex justify-center">
                    <label className="form-control w-full max-w-lg">
                        <div className="label">
                            <span className="label-text">PDF file</span>
                        </div>
                        <input type="file" name="file" className="file-input file-input-bordered w-full max-w-lg"/>
                    </label>
                </div>

                <div className="flex justify-center">
                    <input type="submit" className="btn btn-neutral w-full max-w-lg mt-10"/>
                </div>
            </Form>

        </div>
    )
}

export default PostForm