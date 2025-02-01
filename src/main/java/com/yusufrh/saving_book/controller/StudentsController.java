package com.yusufrh.saving_book.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yusufrh.saving_book.dto.StudentsDTO;
import com.yusufrh.saving_book.entity.Students;
import com.yusufrh.saving_book.exception.DataNotFoundException;
import com.yusufrh.saving_book.exception.DuplicateEntryException;
import com.yusufrh.saving_book.repository.StudentsRepository;
import com.yusufrh.saving_book.response.BaseResponse;
import com.yusufrh.saving_book.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
public class StudentsController {
    private static final Logger logger = LoggerFactory.getLogger(StudentsController.class);
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private StudentService studentService;
    
    @PostMapping
    public ResponseEntity createStudent(@RequestBody Students entity) {
       try{
            if(entity==null) throw new IllegalArgumentException("Student cannot be null");
            
            logger.info("Attempting to save entity: {}", entity);
            studentsRepository.save(entity);
            BaseResponse<Object> baseResponse = new BaseResponse<>(0,"success", null);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            logger.error("DataIntegrityViolationException for NISN: {}", entity.getNisn(), e.getMessage()); 
            throw new DuplicateEntryException("Data mismatch or already exists for : " + entity.getNisn());
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch(ConstraintViolationException e) {
            logger.error(e.getMessage());
            throw new ConstraintViolationException("Data already exists for NISN: " + entity.getNisn(),e.getSQLException(), e.getSQL(),e.getConstraintName());
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("An error occurred while saving the student.");
        }
    }
    
    @GetMapping
    public ResponseEntity getStudents(@RequestParam(required = false) String status, 
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
        try{
            Page<StudentsDTO> pageStudents = studentService.getStudents(page, size, status);
            BaseResponse<StudentsDTO> baseResponse = new BaseResponse<>(0,"success", pageStudents);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("An error occurred while getting the students.");
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Students entity) {
        Map<String, Object> response = new HashMap<>();
        try {
            if(entity==null) throw new IllegalArgumentException("Student cannot be null");
            if(entity.getId() == null) throw new IllegalArgumentException("Id cannot be null");
            if(entity.getName() == null) throw new IllegalArgumentException("Name cannot be null");
            if(entity.getNisn() == null) throw new IllegalArgumentException("NISN cannot be null");

            Optional<Students> optStudent = studentsRepository.findById(entity.getId());
            if(optStudent.isPresent()) {
                Students existingStudent = optStudent.get();
                existingStudent.setName(entity.getName());
                existingStudent.setNisn(entity.getNisn());
                existingStudent.setStatus(entity.getStatus());
                studentsRepository.save(existingStudent);
                BaseResponse<Object> baseResponse = new BaseResponse<>(0,"success", null);
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            } else {
                throw new DataNotFoundException("Data not found.");
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("DataIntegrityViolationException for NISN: {}", entity.getNisn(), e.getMessage()); 
            throw new DuplicateEntryException("Data mismatch or already exists for : " + entity.getNisn());
        } catch(IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch(ConstraintViolationException e) {
            logger.error(e.getMessage());
            throw new ConstraintViolationException("Data already exists for NISN: " + entity.getNisn(),e.getSQLException(), e.getSQL(),e.getConstraintName());
        } catch(DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("An error occurred while updating the student.");
        }
    }
    
    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if(id == null) throw new IllegalArgumentException("Id cannot be null");
            Optional<Students> optStudent = studentsRepository.findById(id);
            if(optStudent.isPresent()) {
                Students student = optStudent.get();
                student.setStatus("inactive"); 
                studentsRepository.save(student);
                BaseResponse<Object> baseResponse = new BaseResponse<>(0,"success", null);
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            } else {
                throw new DataNotFoundException("Data not found.");
            }
        } catch(DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("An error occurred while deleting the student.");
        }
    }
}
