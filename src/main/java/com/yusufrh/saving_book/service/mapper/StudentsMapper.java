package com.yusufrh.saving_book.service.mapper;

import com.yusufrh.saving_book.dto.StudentsDTO;
import com.yusufrh.saving_book.entity.Students;

public class StudentsMapper {
public static StudentsDTO toDTO(Students student) { 
        return new StudentsDTO(student.getId(),
                student.getNisn(),   
                student.getName(),
                student.getStatus());   
    }
}
