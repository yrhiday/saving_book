package com.yusufrh.saving_book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yusufrh.saving_book.entity.Savings;
import com.yusufrh.saving_book.repository.SavingsRepository;

@Service
public class BalanceService {
    @Autowired
    private SavingsRepository savingsRepository;

    public Double getBalance(Long studentId) {
        List<Savings> savings = savingsRepository.findByStudentId(studentId);
        Double balance = 0.0;
        savings.sort((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()));
        for (Savings sav : savings) {
            if (sav.getType().equals("saving")) {
                balance += sav.getAmount();
            } else if (sav.getType().equals("withdrawal")) {
                balance -= sav.getAmount();
            }
        }

        return balance;

    }

}
