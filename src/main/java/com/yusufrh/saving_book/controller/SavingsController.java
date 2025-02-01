package com.yusufrh.saving_book.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yusufrh.saving_book.dto.SavingsDTO;
import com.yusufrh.saving_book.entity.Savings;
import com.yusufrh.saving_book.entity.Students;
import com.yusufrh.saving_book.exception.DataNotFoundException;
import com.yusufrh.saving_book.exception.DuplicateEntryException;
import com.yusufrh.saving_book.repository.SavingsRepository;
import com.yusufrh.saving_book.repository.StudentsRepository;
import com.yusufrh.saving_book.response.BaseResponse;
import com.yusufrh.saving_book.service.BalanceService;
import com.yusufrh.saving_book.service.SavingsService;

@RestController
@RequestMapping("/api/v1/savings")
public class SavingsController {
    private static final Logger logger = LoggerFactory.getLogger(SavingsController.class);

    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private StudentsRepository studentRepository;
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private BalanceService balanceService;

    @PostMapping
    public ResponseEntity save(@RequestBody Savings entity) {
         Map<String, Object> response = new HashMap<>();
        try{
            if (entity==null) throw new IllegalArgumentException("Saving cannot be null");
            if(entity.getStudent().getId() == null) throw new IllegalArgumentException("Student id cannot be null");
             if(entity.getAmount() == null) throw new IllegalArgumentException("Amount cannot be null");
            if(entity.getAmount() < 0) throw new IllegalArgumentException("Amount cannot zero");
            if(entity.getTransactionDate() == null) throw new IllegalArgumentException("Transaction date cannot be null");
            
            Double balance = balanceService.getBalance(entity.getStudent().getId());
            if(balance < entity.getAmount() && entity.getType().equals("withdrawal")) throw new IllegalArgumentException("Balance is not enough");
            
            Long studentId = entity.getStudent().getId();
            Students student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
            entity.setStudent(student);
            savingsRepository.save(entity);
            BaseResponse<Object> baseResponse = new BaseResponse<>(0,"success", null);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            logger.error("DataIntegrityViolationException for saving => {}", e); 
            throw new DuplicateEntryException("Data mismatch for savings");
        } catch(IllegalArgumentException e) {
            logger.error("IllegalArgumentException for saving => {}", e); 
            throw new IllegalArgumentException(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("An error occurred while saving the savingss.");
        }
    }


    @GetMapping
    public ResponseEntity getSavings(@RequestParam(required = true) Long studentId,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
        try{
            Page<SavingsDTO> pageSaving = savingsService.getSSaving(page, size, studentId);
            BaseResponse<SavingsDTO> baseResponse = new BaseResponse<>(0,"success", pageSaving);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("An error occurred while getting the students.");
        }
    }
    
    @PutMapping
    public ResponseEntity update(@RequestBody Savings entity) {
        Map<String, Object> response = new HashMap<>();
        try {
            if(entity==null) throw new IllegalArgumentException("Saving cannot be null");
            if(entity.getId() == null) throw new IllegalArgumentException("Id cannot be null");
            Optional<Savings> optSaving = savingsRepository.findById(entity.getId());
            if(optSaving.isPresent()) {
                Savings existingSaving = optSaving.get();
                if(entity.getAmount() == null) throw new IllegalArgumentException("Amount cannot be null");
                if(entity.getAmount() < 0) throw new IllegalArgumentException("Amount cannot zero");
                if(entity.getTransactionDate() == null) throw new IllegalArgumentException("Transaction date cannot be null");

                existingSaving.setTransactionDate(entity.getTransactionDate());
                existingSaving.setAmount(entity.getAmount());
                savingsRepository.save(existingSaving);
                BaseResponse<Object> baseResponse = new BaseResponse<>(0,"success", null);
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            } else {
                throw new DataNotFoundException("Data not found.");
            }
        } catch(DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch(Exception e) {   
            logger.error(e.getMessage());
            throw new RuntimeException("An error occurred while updating the saving.");
        }
    }
}

