import React, { useState } from "react";

function DeleteButton() {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return (
        <>
            <div className="flex items-center w-1/5 ms-10">

                <button className="btn btn-error" onClick={() => document.getElementById('my_modal_1').showModal()}>Delete Post
                </button>
                <dialog id="my_modal_1" className="modal">
                    <div className="modal-box">
                        <h3 className="font-bold text-lg">Вы действительно хотите удалить данный пост безвозвратно?</h3>
                        <div className="modal-action">
                            <form method="dialog">
                                {/* if there is a button in form, it will close the modal */}
                                <button className="btn">Нет</button>
                                <button className="btn btn-error">Удалить</button>
                            </form>
                        </div>
                    </div>
                </dialog>
            </div>
        </>
    );
}

export default DeleteButton;