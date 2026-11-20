package com.example.crud.service;

import com.example.crud.dto.StudentRequest;
import com.example.crud.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    List<StudentResponse> getAll();

    StudentResponse getById(Long id);

    StudentResponse create(StudentRequest request);

    StudentResponse update(Long id, StudentRequest request);

    void delete(Long id);
}
