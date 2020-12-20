package com.app.sakura.repository;

import com.app.sakura.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilterRepository extends JpaRepository<Filter,Integer> {
    Filter findByName(String name);
}
