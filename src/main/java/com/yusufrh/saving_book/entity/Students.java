package com.yusufrh.saving_book.entity;

import java.time.Instant;
import java.util.Set;

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
public class Students {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(length = 10,nullable = false, unique = true)
    private String nisn;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String status = "active"; //active, inactive

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy = "SYSTEM";
    
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private Instant created;

    @Column(name = "updated_by", length = 20, nullable = true)
    private String updatedBy;
    
    @Column(name = "updated_date", nullable = true)
    @LastModifiedDate
    private Instant updated;

    @OneToMany(mappedBy = "student") 
    private Set<Savings> savings;

    public Students() { 
        this.created = Instant.now();
    }
}
