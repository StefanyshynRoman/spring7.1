package com.shpp.rstefanyshyn.spring.repository;

import com.shpp.rstefanyshyn.spring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}