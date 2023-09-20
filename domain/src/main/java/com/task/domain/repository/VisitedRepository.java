package com.task.domain.repository;

import com.task.domain.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitedRepository extends JpaRepository<Visit,Long> {
}
