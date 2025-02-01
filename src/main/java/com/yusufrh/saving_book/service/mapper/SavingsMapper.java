package com.yusufrh.saving_book.service.mapper;

import com.yusufrh.saving_book.dto.SavingsDTO;
import com.yusufrh.saving_book.entity.Savings;

public class SavingsMapper {
public static SavingsDTO toDTO(Savings savings) { 
        return new SavingsDTO(savings.getId(),
                savings.getTransactionDate(),
                savings.getType(),
                savings.getAmount(),
                savings.getStudent().getName(),   
                savings.getStudent().getNisn());
    }
}
