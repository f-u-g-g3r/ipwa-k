function LoginForm() {
    return (
        <>
            <form>
                <div className="flex justify-center">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text">Username</span>
                        </div>
                        <input type="text" placeholder="Type here" className="input input-bordered w-full max-w-xs"/>
                    </label>
                </div>
                <div className="flex justify-center">
                    <label className="form-control w-full max-w-xs">
                        <div className="label">
                            <span className="label-text">Password</span>
                        </div>
                        <input type="text" placeholder="Type here" className="input input-bordered w-full max-w-xs"/>
                    </label></div>
                <div className="flex justify-center my-3">
                    <button className="btn btn-success">Log in</button>
                </div>

            </form>
        </>
    )
}

export default LoginForm