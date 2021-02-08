package com.app.sakura.entity;

import javax.persistence.*;

@Entity
@Table(name = "app_config")
public class AppConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "load_default_data")
    private boolean loadDefaultData;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isLoadDefaultData() {
        return loadDefaultData;
    }

    public void setLoadDefaultData(boolean loadDefaultData) {
        this.loadDefaultData = loadDefaultData;
    }
}
