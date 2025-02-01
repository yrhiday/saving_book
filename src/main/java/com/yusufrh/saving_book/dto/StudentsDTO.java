package com.yusufrh.saving_book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentsDTO {
    public StudentsDTO(Long id, String nisn, String name, String status) {
        this.id = id;
        this.nisn = nisn;
        this.name = name;
        this.status = status;
    }
    
    private Long id;
    private String nisn;
    private String name;
    private String status;
}
