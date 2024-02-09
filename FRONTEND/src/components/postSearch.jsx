
function PostSearch() {
    return(
        <>
            <div className="flex justify-center w-full">
                <label className="form-control w-2/3">
                    <div className="label">
                        <span className="label-text">What is your name?</span>
                    </div>
                    <input type="text" placeholder="Type here" className="input input-bordered w-2/3"/>
                </label>
            </div>
        </>
    )
}

export default PostSearch