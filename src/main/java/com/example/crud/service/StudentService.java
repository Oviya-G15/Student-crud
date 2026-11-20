package com.example.crud.service;

import com.example.crud.exception.StudentNotFoundException;
import com.example.crud.model.Student;
import com.example.crud.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student create(Student student) {
        student.setId(null);
        return repo.save(student);
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student update(Long id, Student updated) {
        Student existing = getById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setCourse(updated.getCourse());
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new StudentNotFoundException(id);
        repo.deleteById(id);
    }
}
