package com.app.sakura.repository;

import com.app.sakura.entity.TypeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeDetailRepository extends JpaRepository<TypeDetail,Integer> {
    List<TypeDetail> findByFilterId(int id);
}
