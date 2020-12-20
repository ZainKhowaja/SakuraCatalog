package com.app.sakura.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;


@Entity(name = "type_detail")
public class TypeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "filter_id")
    private Filter filter;

    public TypeDetail() {
    }

    public TypeDetail(String name , Filter filter) {
        this.name = name;
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
