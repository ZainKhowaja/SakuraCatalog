package com.app.sakura.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "product_detail")
public class ProductDetail implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "sakura_no")
    private String sakuraNo;

    @Column(name = "height")
    private String height;

    @Column(name = "out_dm")
    private String outDiameter;

    @Column(name = "inner_dm")
    private String innerDiameter;

    @Column(name = "contains")
    private String contains;

    @Column(name = "thread")
    private String thread;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSakuraNo() {
        return sakuraNo;
    }

    public void setSakuraNo(String sakuraNo) {
        this.sakuraNo = sakuraNo;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOutDiameter() {
        return outDiameter;
    }

    public void setOutDiameter(String outDiameter) {
        this.outDiameter = outDiameter;
    }

    public String getInnerDiameter() {
        return innerDiameter;
    }

    public void setInnerDiameter(String innerDiameter) {
        this.innerDiameter = innerDiameter;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }
}
