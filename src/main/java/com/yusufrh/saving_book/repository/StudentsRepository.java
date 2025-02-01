package com.yusufrh.saving_book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yusufrh.saving_book.entity.Students;

public interface StudentsRepository extends JpaRepository<Students, Long> {
    Page<Students> findByStatus(String status, Pageable pageable);
}
