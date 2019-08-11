package com.codegym.repository;

import com.codegym.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<Task,Long> {
    Page<Task>findAllByNameStartsWith(String name, Pageable pageable);
    Page<Task> findAllByStatus(String status, Pageable pageable);

}
