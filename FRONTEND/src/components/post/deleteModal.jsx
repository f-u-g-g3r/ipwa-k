import React, {useContext, useEffect} from "react";
import {ActionContext} from "../company/homeCompany.jsx";

function deleteModal(props) {
    const value = useContext(ActionContext);
    const handleDelete = value[0];
    const closeModal = value[1];
    return(
        <>
            <dialog id="modalWindowId" className="modal modal-open">
                <div className="modal-box">
                    <h3 className="font-bold text-lg">Are you sure you want to delete post "{props.post.workName}"?</h3>
                    <div className="modal-action">
                        <form method="dialog">
                            <button className="btn" onClick={() => closeModal()}>No</button>
                            <button
                                className="btn btn-error"
                                onClick={() => {
                                    handleDelete(props.post.id);
                                }}
                            >Delete
                            </button>
                        </form>
                    </div>
                </div>
            </dialog>
        </>
    )
}

export default deleteModal;