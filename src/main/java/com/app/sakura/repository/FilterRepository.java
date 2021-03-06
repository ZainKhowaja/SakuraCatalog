package com.app.sakura.repository;

import com.app.sakura.entity.Filter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilterRepository extends JpaRepository<Filter,Integer> {
    Filter findByName(String name);
    
    List<Filter> findByActiveTrue();
}
