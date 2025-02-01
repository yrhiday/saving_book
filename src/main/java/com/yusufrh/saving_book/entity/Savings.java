package com.yusufrh.saving_book.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString   
@EntityListeners(AuditingEntityListener.class)
public class Savings {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private Instant transactionDate;

    @Column(nullable = false)
    private String type; // "saving" or "withdrawal"

    @Column(nullable = false)
    private Double amount;

    @ManyToOne 
    @JoinColumn(name = "student_id", nullable = false) 
    private Students student;
    
    @Column(length = 20, nullable = false)
    private String createdBy = "SYSTEM";
    
    @Column(nullable = false)
    @CreatedDate
    private Instant created;

    @Column(length = 20, nullable = true)
    private String updatedBy;
    
    @Column(nullable = true)
    @LastModifiedDate
    private Instant updated;

    public Savings() { 
        this.transactionDate = Instant.now(); 
    }

    public Savings(String type, Double amount) { 
        this(); 
        this.type = type; 
        this.amount = amount; 
    }
}
