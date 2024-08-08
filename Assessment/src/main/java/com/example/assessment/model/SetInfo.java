package com.example.assessment.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer setId;
    private String setName;

    private String createdBy;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp modifiedAt;

    private String domain;

    @Enumerated(EnumType.ORDINAL)
    private SetStatus status;
    @OneToMany
    @Cascade(value = CascadeType.ALL)
    @JoinColumn(name = "set_id")
    private List<Question> questions;

}
