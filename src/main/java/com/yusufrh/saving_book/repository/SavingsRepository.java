package com.yusufrh.saving_book.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yusufrh.saving_book.entity.Savings;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
    Page<Savings> findByStudentIdOrderByTransactionDate(Long studentId, Pageable pageable);
    List<Savings> findByStudentId(Long studentId);
} 
