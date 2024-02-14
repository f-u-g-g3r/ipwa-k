import {useEffect, useState} from "react";
import {addNewGroup, getAllGroups} from "../../../services/ClassGroupService.jsx";
import {Form, useFetcher, useFormAction} from "react-router-dom";
import {getTeacher} from "../../../services/TeacherService.jsx";

function ShowGroups() {

    const [groups, setGroups] = useState({});
    const [newGroup, setNewGroup] = useState("")
    const [teacherNames, setTeacherNames] = useState({});

    const fetchGroups = async () => {
        try {
            setGroups(await getAllGroups());
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

    useEffect(() => {
        fetchGroups()
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

    return(
        <>
            <Form onSubmit={handleSubmit}>
                <div className="flex">
                    <label className="input input-bordered flex items-center gap-2">
                        New group:
                        <input type="text" className="grow" placeholder="" required onChange={(event) => setNewGroup(event.target.value)} />
                    </label>
                    <button type="submit" className="btn btn-success ms-2">Add</button>
                </div>
            </Form>
            <table className="table">
                <thead>
                    <tr>
                        <td>Id</td>
                        <td>Name</td>
                        <td>Number of students</td>
                        <td>Teacher</td>
                    </tr>
                </thead>
                <tbody>
                    {groups.length ?
                    groups.map((group) =>
                        <tr>
                         <td>{group.id}</td>
                         <td>{group.name}</td>
                         <td>{group.students.length}</td>
                            <td>{teacherNames[group.id]}</td>
                        </tr>) :
                    <></>}
                </tbody>
            </table>
        </>
    )
}

export default ShowGroups