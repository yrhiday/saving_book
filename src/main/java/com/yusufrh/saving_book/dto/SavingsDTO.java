package com.yusufrh.saving_book.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
public class SavingsDTO {

    public SavingsDTO(Long id, Instant transactionDate, String type, Double amount, String studentName, String studentNisn) {
        this.id = id;
        this.transactionDate = transactionDate; 
        this.type = type;
        this.amount = amount;
        this.studentName = studentName; 
        this.studentNisn = studentNisn;
    }

    private Long id;
    private Instant transactionDate;
    private String type;
    private Double amount;
    private String studentName;
    private String studentNisn;
}
