package com.example.assessment.repository;

import com.example.assessment.model.OptionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<OptionModel,Integer> {
}
