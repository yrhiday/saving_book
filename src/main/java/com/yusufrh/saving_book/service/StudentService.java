package com.yusufrh.saving_book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.DocFlavor.READER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yusufrh.saving_book.dto.StudentsDTO;
import com.yusufrh.saving_book.entity.Students;
import com.yusufrh.saving_book.repository.StudentsRepository;
import com.yusufrh.saving_book.service.mapper.StudentsMapper;

@Service
public class StudentService {
    @Autowired
    private StudentsRepository studentRepository;
    
    public Page<StudentsDTO> getStudents(int page, int size, String status) {
        Page<Students> students;
        if(status == null) {
            students = studentRepository.findAll(PageRequest.of(page, size)); 
        } else {
            students = studentRepository.findByStatus(status, (PageRequest.of(page, size))); 
        }
        
        List<StudentsDTO> studentsDTOs = students.stream() 
            .map(StudentsMapper::toDTO) 
            .collect(Collectors.toList());
                
        return new PageImpl<>(studentsDTOs==null? new ArrayList<>():studentsDTOs, PageRequest.of(page, size), students.getTotalElements());
    }
}
