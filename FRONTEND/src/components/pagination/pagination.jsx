import {useContext} from "react";
import {ActionContext} from "../post/posts.jsx";

function Pagination(props) {
    const context = useContext(ActionContext);
    const currentPage = props.posts.number;
    const totalPages = props.posts.totalPages;
    return (
        <>

            {totalPages < 10 || totalPages > 10 && currentPage <= 6 ?
                <div className="join flex justify-center mb-20">
                    {Array.from({length: Math.min(10, props.posts.totalPages)}, (_, index) => index + 1).map(pageNumber => (
                        <button
                            key={pageNumber}
                            className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                            onClick={() => context[0](pageNumber - 1)}
                            disabled={pageNumber - 1 === props.posts.number ? "disabled" : ""}
                        >
                            {pageNumber}
                        </button>
                    ))}
                    <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                            disabled="disabled">...
                    </button>
                    <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                            onClick={() => context[0](totalPages - 1)}>{totalPages}</button>
                </div>
                : totalPages > 10 && currentPage + 6 > totalPages ?
                    <div className="join flex justify-center mb-20">
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](0)}>1
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                disabled="disabled">...
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage - 3)}>{currentPage - 2}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage - 2)}>{currentPage - 1}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage - 1)}>{currentPage}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                disabled="disabled">{currentPage + 1}</button>
                        {currentPage + 1 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                    onClick={() => context[0](currentPage + 1)}>{currentPage + 2}</button> : ""
                        }
                        {currentPage + 2 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                    onClick={() => context[0](currentPage + 2)}>{currentPage + 3}</button> : ""
                        }
                        {currentPage + 3 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                    onClick={() => context[0](currentPage + 3)}>{currentPage + 4}</button> : ""
                        }
                        {currentPage + 4 < totalPages ?
                            <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                    onClick={() => context[0](currentPage + 4)}>{currentPage + 5}</button> : ""
                        }

                    </div>
                    : <div className="join flex justify-center mb-20">
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](0)}>1
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                disabled="disabled">...
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage - 3)}>{currentPage - 2}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage - 2)}>{currentPage - 1}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage - 1)}>{currentPage}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                disabled="disabled">{currentPage + 1}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage + 1)}>{currentPage + 2}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage + 2)}>{currentPage + 3}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage + 3)}>{currentPage + 4}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](currentPage + 4)}>{currentPage + 5}</button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                disabled="disabled">...
                        </button>
                        <button className="join-item btn btn-active btn-ghost text-3xl pt-6 px-8 pb-16"
                                onClick={() => context[0](totalPages - 1)}>{totalPages}</button>
                    </div>
            }


        </>
    )
}

export default Pagination;