package com.yusufrh.saving_book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yusufrh.saving_book.dto.SavingsDTO;
import com.yusufrh.saving_book.entity.Savings;
import com.yusufrh.saving_book.repository.SavingsRepository;
import com.yusufrh.saving_book.service.mapper.SavingsMapper;

@Service
public class SavingsService {
    @Autowired
    private SavingsRepository savingsRepository;
    
    public Page<SavingsDTO> getSSaving(int page, int size, Long studentId) {
        Page<Savings> savings;
        savings = savingsRepository.findByStudentIdOrderByTransactionDate(studentId, PageRequest.of(page, size));
        List<SavingsDTO> savingsDTOs = savings.stream() 
            .map(SavingsMapper::toDTO) 
            .collect(Collectors.toList());
                
        return new PageImpl<>(savingsDTOs, PageRequest.of(page, size), savings.getTotalElements());    
    }
}
