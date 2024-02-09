function PostForm() {
    return (
        <div className="">
            <div className="flex justify-center">
                <label className="form-control w-full max-w-lg my-2">
                    <div className="label">
                        <span className="label-text">Töö nimetus</span>
                    </div>
                    <input type="text" className="input input-bordered w-full max-w-lg" required/>
                </label>
            </div>

            <div className="flex justify-center">
                <label className="form-control w-full max-w-lg my-2">
                    <div className="label">
                        <span className="label-text">Töö kirjeldus</span>
                    </div>
                    <textarea className="textarea textarea-bordered w-full max-w-lg h-24" required></textarea>
                </label>
            </div>

            <div className="flex justify-center">
                <label className="form-control w-full max-w-lg my-2">
                    <div className="label">
                        <span className="label-text">Palk</span>
                    </div>
                    <input type="text" className="input input-bordered w-full max-w-lg"/>
                </label>
            </div>

            <div className="flex justify-center">
                <label className="form-control w-full max-w-lg my-2">
                    <div className="label">
                        <span className="label-text">Nõuded</span>
                    </div>
                    <input type="text" className="input input-bordered w-full max-w-lg" required/>
                </label>
            </div>

            <div className="flex justify-center">
                <label className="form-control w-full max-w-lg my-2">
                    <div className="label">
                        <span className="label-text">Lisainformatsioon</span>
                    </div>
                    <textarea className="textarea textarea-bordered w-full max-w-lg h-24"></textarea>
                </label>
            </div>

            <div className="flex justify-center">
                <label className="form-control w-full max-w-lg my-2">
                    <div className="label">
                        <span className="label-text">Aegumiskuupäev</span>
                    </div>
                    <input type="date" className="input input-bordered w-full max-w-lg" required/>
                </label>
            </div>

            <div className="flex justify-center">
                <label className="form-control w-full max-w-lg">
                    <div className="label">
                        <span className="label-text">PDF file</span>
                    </div>
                    <input type="file" className="file-input file-input-bordered w-full max-w-lg"/>
                </label>
            </div>

            <div className="flex justify-center">
                <input type="submit" className="btn btn-neutral w-full max-w-lg mt-10" />
            </div>

        </div>
    )
}

export default PostForm