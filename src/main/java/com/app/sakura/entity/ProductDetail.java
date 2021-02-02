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

    @Column(name = "height_measurement")
    private String heightMeasurement;

    @Column(name = "out_dm")
    private String outDiameter;

    @Column(name = "outer_measurement")
    private String outerMeasurement;

    @Column(name = "inner_dm")
    private String innerDiameter;

    @Column(name = "inner_measurement")
    private String innerMeasurement;

    @Column(name = "out_sec_dm")
    private String outSecDiameter;

    @Column(name = "outer_sec_measurement")
    private String outerSecMeasurement;

    @Column(name = "inner_sec_dm")
    private String innerSecDiameter;

    @Column(name = "inner_sec_measurement")
    private String innerSecMeasurement;

    @Column(name = "contains")
    private String contains;

    @Column(name = "thread")
    private String thread;

    @Column(name = "note")
    private String note;

    @Column(name = "drain")
    private String drain;

    public String getDrain() {
        return drain;
    }

    public void setDrain(String drain) {
        this.drain = drain;
    }

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

    public String getHeightMeasurement() {
        return heightMeasurement;
    }

    public void setHeightMeasurement(String heightMeasurement) {
        this.heightMeasurement = heightMeasurement;
    }

    public String getOuterMeasurement() {
        return outerMeasurement;
    }

    public void setOuterMeasurement(String outerMeasurement) {
        this.outerMeasurement = outerMeasurement;
    }

    public String getInnerMeasurement() {
        return innerMeasurement;
    }

    public void setInnerMeasurement(String innerMeasurement) {
        this.innerMeasurement = innerMeasurement;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOutSecDiameter() {
        return outSecDiameter;
    }

    public void setOutSecDiameter(String outSecDiameter) {
        this.outSecDiameter = outSecDiameter;
    }

    public String getOuterSecMeasurement() {
        return outerSecMeasurement;
    }

    public void setOuterSecMeasurement(String outerSecMeasurement) {
        this.outerSecMeasurement = outerSecMeasurement;
    }

    public String getInnerSecDiameter() {
        return innerSecDiameter;
    }

    public void setInnerSecDiameter(String innerSecDiameter) {
        this.innerSecDiameter = innerSecDiameter;
    }

    public String getInnerSecMeasurement() {
        return innerSecMeasurement;
    }

    public void setInnerSecMeasurement(String innerSecMeasurement) {
        this.innerSecMeasurement = innerSecMeasurement;
    }
}
