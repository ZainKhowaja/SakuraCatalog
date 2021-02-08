package com.app.sakura.repository;

import com.app.sakura.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppConfigRepository extends JpaRepository<AppConfig,Integer> {
}
