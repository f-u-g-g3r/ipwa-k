package com.ipwa.kp.services;

import com.ipwa.kp.controllers.exceptions.ClassGroupNotFoundException;
import com.ipwa.kp.models.ClassGroup;
import com.ipwa.kp.models.Student;
import com.ipwa.kp.models.Teacher;
import com.ipwa.kp.repositories.ClassGroupRepository;
import com.ipwa.kp.repositories.StudentRepository;
import com.ipwa.kp.repositories.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ClassGroupService {
    private final ClassGroupRepository repository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public ClassGroupService(ClassGroupRepository repository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<ClassGroup> getAll() {
        return repository.findAll();
    }

    public Page<ClassGroup> getAllByPage(Optional<String> sortBy, Optional<Integer> page, Optional<String> direction) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }
        return repository.findAll(PageRequest.of(
                page.orElse(0),
                5,
                sort, sortBy.orElse("id")
        ));
    }

    public ResponseEntity<?> createClassGroup(ClassGroup classGroup) {
        return ResponseEntity.ok(repository.save(classGroup));
    }

    public ResponseEntity<?> getClassGroupByName(String name) {
        ClassGroup group = repository.findByName(name)
                .orElseThrow(() -> new ClassGroupNotFoundException(name));
        return ResponseEntity.ok(group);
    }

    public void deleteGroupById(Long id) {
        List<Student> students = studentRepository.findAllByClassGroupId(id)
                .orElseThrow(() -> new ClassGroupNotFoundException(id));

        Teacher teacher = teacherRepository.findByClassGroupId(id)
                .orElseThrow(() -> new ClassGroupNotFoundException(id));

        for (Student student : students) {
            student.setClassGroup(null);
        }

        teacher.setClassGroup(null);

        repository.deleteById(id);
    }
}
