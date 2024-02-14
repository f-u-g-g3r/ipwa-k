package com.ipwa.kp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class_groups")
public class ClassGroup {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;

    @OneToMany(mappedBy = "classGroup", fetch = FetchType.LAZY)
    private List<Student> students= new ArrayList<>();

    @OneToMany(mappedBy = "classGroup", fetch = FetchType.LAZY)
    private List<Teacher> teachers= new ArrayList<>();

    public ClassGroup() {

    }

    public ClassGroup(String name, List<Student> students, List<Teacher> teachers) {
        this.name = name;
        this.students = students;
        this.teachers = teachers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }


    @Override
    public String toString() {
        return "ClassGroup{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
