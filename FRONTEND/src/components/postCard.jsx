function PostCard() {
    return (
        <>
            <div className="card lg:card-side bg-base-100 shadow-lg my-10">
                <figure><img src="http://via.placeholder.com/200x200" alt="Album"/>
                </figure>
                <div className="card-body">
                    <h2 className="card-title">Work name</h2>
                    <p>Organization</p>
                    <li className="flex text-lg font-medium text-gray-400">
                        <ul className="mx-5">skill1</ul>
                        <ul className="mx-5">skill2</ul>
                        <ul className="mx-5">skill3</ul>
                        <ul className="mx-5">skill4</ul>
                    </li>
                </div>
            </div>
        </>
    )
}

export default PostCard