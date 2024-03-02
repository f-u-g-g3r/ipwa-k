import {useEffect, useState} from "react";
import {addNewGroup, getAllGroups, getGroupsByPage} from "../../../services/ClassGroupService.jsx";
import {Form, useFetcher, useFormAction} from "react-router-dom";
import {addTeacherToGroup, getTeacher, getTeachers} from "../../../services/TeacherService.jsx";
import Pagination from "../../pagination/pagination.jsx";

function ShowGroups() {

    const [groups, setGroups] = useState({content: []});
    const [newGroup, setNewGroup] = useState("");
    const [teacherNames, setTeacherNames] = useState({});
    const [teachers, setTeachers] = useState({});
    const [groupSelected, setGroupSelected] = useState(0);

    const fetchGroups = async (pageNumber = 0) => {
        try {
            setGroups(await getGroupsByPage(pageNumber));
        } catch (e) {
            console.log(e)
        }
    }

    const fetchTeachers = async () => {
        try {
            setTeachers(await getTeachers());
        } catch (e) {
            console.log(e)
        }
    }

    const fetchTeacherName = async (teacherId) => {
        try {
            if (teacherId != null) {
                const teacher = await getTeacher(teacherId);
                return `${teacher.firstName} ${teacher.lastName}`;
            }
            return "No teacher assigned";
        } catch (e) {
            console.log(e);
        }
    }

    const handleSubmit = async () => {
        const data = {
            name: newGroup
        }
        await addNewGroup(data).then(fetchGroups)
    }


    const handleAssignTeacher = async (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const teacherId = formData.get('teacherId');
        const groupId = formData.get('groupId');

        await addTeacherToGroup(groupId, teacherId).then(fetchGroups);

    }

    useEffect(() => {
        fetchGroups()
        fetchTeachers()
    }, [])

    useEffect(() => {
        const fetchData = async () => {
            const names = {};
            for (const group of groups) {
                const name = await fetchTeacherName(group.teacher);
                names[group.id] = name;
            }
            setTeacherNames(names);
        };
        fetchData();
    }, [groups]);

    return (
        <>
            <Form onSubmit={handleSubmit}>
                <div className="flex">
                    <label className="input input-bordered flex items-center gap-2">
                        New group:
                        <input type="text" className="grow" required
                               onChange={(event) => setNewGroup(event.target.value)}/>
                    </label>
                    <button type="submit" className="btn btn-success ms-2">Add</button>
                </div>
            </Form>
            {groups.content.length ?
                <>
                    <table className="table mt-10">
                        <thead>
                        <tr>
                            <td>Id</td>
                            <td>Name</td>
                            <td>Number of students</td>
                            <td>Teacher</td>
                            <td>Action</td>
                        </tr>
                        </thead>
                        <tbody>

                        {groups.content.map((group) =>
                            <tr key={group.id}>
                                <td>{group.id}</td>
                                <td>{group.name}</td>
                                <td>{group.students.length}</td>
                                <td>{teacherNames[group.id]}</td>
                                <td>
                                    <button className="btn btn-success" onClick={() => {
                                        assignTeacher.showModal();
                                        setGroupSelected(group.id)
                                    }}>Assign
                                        teacher to group
                                    </button>
                                </td>
                            </tr>)}
                        </tbody>
                    </table>
                    <Pagination data={groups} fetchAction={fetchGroups}/>
                </>
                : <></>}

            <dialog id="assignTeacher" className="modal">
                <div className="modal-box">
                    <form method="dialog" onSubmit={handleAssignTeacher}>
                        <h3 className="font-bold text-lg">Assign teacher to group</h3>
                        <p className="py-4">
                            <select name="teacherId" className="select select-bordered w-full">
                                {teachers.length ?
                                    teachers.map((teacher) =>
                                        `${teacher.firstName} ${teacher.lastName}` !== 'null null' ?
                                            <option
                                                value={teacher.id}>{`${teacher.firstName} ${teacher.lastName}`}</option> :
                                            <></>
                                    ) : <></>
                                }
                            </select>
                            <input type="hidden" name="groupId" value={groupSelected}/>
                        </p>
                        <div className="modal-action">
                            <button type="button" className="btn mx-2" onClick={() => assignTeacher.close()}>Close
                            </button>
                            <button type="submit" className="btn btn-warning mx-2"
                                    onClick={() => assignTeacher.close()}>Save
                            </button>
                        </div>
                    </form>
                </div>
            </dialog>
        </>
    )
}

export default ShowGroups