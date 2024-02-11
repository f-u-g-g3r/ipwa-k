
function PostSearch() {
    return(
        <>
            <div className="flex justify-center">
                <label className="form-control w-full mx-20">
                    <div className="label">
                        <span className="label-text">Search</span>
                    </div>
                    <input type="text" placeholder="Search" className="input input-bordered w-full text-2xl"/>
                </label>
            </div>
        </>
    )
}

export default PostSearch