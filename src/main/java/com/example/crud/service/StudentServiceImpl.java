package com.example.crud.service;

import com.example.crud.dto.StudentRequest;
import com.example.crud.dto.StudentResponse;
import com.example.crud.entity.Student;
import com.example.crud.exception.DuplicateResourceException;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public StudentResponse create(StudentRequest request) {
        String email = request.getEmail().trim();
        if (repository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already exists: " + email);
        }
        Student student = new Student();
        copy(request, student);
        return toResponse(repository.save(student));
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = findOrThrow(id);
        String email = request.getEmail().trim();
        if (repository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateResourceException("Email already exists: " + email);
        }
        copy(request, student);
        return toResponse(repository.save(student));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    // ---------- helpers ----------
    private Student findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    private void copy(StudentRequest req, Student s) {
        s.setName(req.getName().trim());
        s.setEmail(req.getEmail().trim());
        s.setCourse(req.getCourse().trim());
    }

    private StudentResponse toResponse(Student s) {
        return new StudentResponse(s.getId(), s.getName(), s.getEmail(), s.getCourse());
    }
}
