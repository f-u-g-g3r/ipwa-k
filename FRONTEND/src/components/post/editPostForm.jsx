import {Form, Link, redirect, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getPost, getPostPdf, savePost, updatePost, uploadPostPdf} from "../../services/PostService.jsx";


export async function actionEditPostForm({request}) {
    const formData = await request.formData();
    const id = formData.get('postId');
    const file = formData.get('file');
    formData.delete('file');
    formData.delete('postId')
    const postData = Object.fromEntries(formData);
    console.log(postData)
    const {data} = await updatePost(postData, id);

    if (file.name !== "") {
        const fileFormData = new FormData();
        fileFormData.append('file', file, file.name);
        await uploadPostPdf(data.id, fileFormData);
    }

    return redirect(`/posts/${id}`)
}

function EditPostForm() {
    const [post, setPost] = useState({});
    const [pdf, setPdf] = useState("");

    let {id} = useParams();

    const fetchPost = async () => {
        try {
            setPost(await getPost(id))
        } catch (e) {
            console.log(e);
        }
    }

    const fetchPdf = async () => {
        try {
            if (post.pathToPdf !== null) {
                setPdf(await getPostPdf(post.pathToPdf))
            }
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        fetchPost();
    }, []);

    useEffect(() => {
        if (post.pathToPdf !== undefined) {
            fetchPdf()
        }
    }, [post])


    return(
        <>
            <Link to={`/posts/${id}`} className="btn btn-neutral w-1/12 mt-5">Back</Link>
            <div className="flex justify-center mb-20">
                <Form method="post" encType="multipart/form-data" className="w-2/3">
                    <div className="flex justify-center">
                        <label className="form-control w-full max-w-lg my-2">
                            <div className="label">
                                <span className="label-text">Töö nimetus</span>
                            </div>
                            <input type="text" name="workName" defaultValue={post.workName} className="input input-bordered w-full max-w-lg" required/>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <label className="form-control w-full max-w-lg my-2">
                            <div className="label">
                                <span className="label-text">Töö kirjeldus</span>
                            </div>
                            <textarea className="textarea textarea-bordered w-full max-w-lg h-24 max-h-96"
                                      name="workDescription" defaultValue={post.workDescription} required></textarea>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <label className="form-control w-full max-w-lg my-2">
                            <div className="label">
                                <span className="label-text">Palk</span>
                            </div>
                            <input type="number" name="salary" defaultValue={post.salary} className="input input-bordered w-full max-w-lg"/>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <label className="form-control w-full max-w-lg my-2">
                            <div className="label">
                                <span className="label-text">Nõuded</span>
                            </div>
                            <input type="text" name="claims" defaultValue={post.claims}  className="input input-bordered w-full max-w-lg" required/>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <label className="form-control w-full max-w-lg my-2">
                            <div className="label">
                                <span className="label-text">Lisainformatsioon</span>
                            </div>
                            <textarea name="additionalInfo" defaultValue={post.additionalInfo}
                                      className="textarea textarea-bordered w-full max-w-lg h-24 max-h-96"></textarea>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <label className="form-control w-full max-w-lg my-2">
                            <div className="label">
                                <span className="label-text">Aegumiskuupäev</span>
                            </div>
                            <input type="date" name="expiryDate" defaultValue={post.expiryDate} className="input input-bordered w-full max-w-lg" required/>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <label className="form-control w-full max-w-lg">
                            <div className="label">
                                <span className="label-text">PDF file or image</span>
                            </div>
                            <input type="file" name="file" className="file-input file-input-bordered w-full max-w-lg"/>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <input type="submit" className="btn btn-neutral w-full max-w-lg mt-10"/>
                    </div>

                    <input type="hidden" name="postId" value={post.id}/>
                </Form>
                <div className="w-1/3 mt-20">
                    <img src={pdf}/>
                </div>
            </div>
        </>
    )
}

export default EditPostForm