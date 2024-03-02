
function Pagination(props) {
    const currentPage = props.data.number;
    const totalPages = props.data.totalPages;
    return (
        <>
            {totalPages < 10 || totalPages > 10 && currentPage <= 6 ?
                <div className="join flex justify-center mb-20">
                    {Array.from({length: Math.min(10, props.data.totalPages)}, (_, index) => index + 1).map(pageNumber => (
                        <button
                            key={pageNumber}
                            className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                            onClick={() => props.fetchAction(pageNumber - 1)}
                            disabled={pageNumber - 1 === props.data.number ? "disabled" : ""}
                        >
                            {pageNumber}
                        </button>
                    ))}
                    {totalPages > 10 ?
                    <><button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                            disabled="disabled">...
                    </button>
                    <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                            onClick={() => props.fetchAction(totalPages - 1)}>{totalPages}</button></>
                     : <></>}
                </div>
                : totalPages > 10 && currentPage + 6 > totalPages ?
                    <div className="join flex justify-center mb-20">
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(0)}>1
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                disabled="disabled">...
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage - 3)}>{currentPage - 2}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage - 2)}>{currentPage - 1}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage - 1)}>{currentPage}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                disabled="disabled">{currentPage + 1}</button>
                        {currentPage + 1 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                    onClick={() => props.fetchAction(currentPage + 1)}>{currentPage + 2}</button> : ""
                        }
                        {currentPage + 2 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                    onClick={() => props.fetchAction(currentPage + 2)}>{currentPage + 3}</button> : ""
                        }
                        {currentPage + 3 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                    onClick={() => props.fetchAction(currentPage + 3)}>{currentPage + 4}</button> : ""
                        }
                        {currentPage + 4 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                    onClick={() => props.fetchAction(currentPage + 4)}>{currentPage + 5}</button> : ""
                        }

                    </div>
                    : <div className="join flex justify-center mb-20">
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(0)}>1
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                disabled="disabled">...
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage - 3)}>{currentPage - 2}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage - 2)}>{currentPage - 1}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage - 1)}>{currentPage}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                disabled="disabled">{currentPage + 1}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage + 1)}>{currentPage + 2}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage + 2)}>{currentPage + 3}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage + 3)}>{currentPage + 4}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(currentPage + 4)}>{currentPage + 5}</button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                disabled="disabled">...
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-2xl pt-2 px-4 pb-10"
                                onClick={() => props.fetchAction(totalPages - 1)}>{totalPages}</button>
                    </div>
            }


        </>
    )
}

export default Pagination;