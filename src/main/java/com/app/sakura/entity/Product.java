package com.app.sakura.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "product")
public class Product {
    @Id
    @Column(name = "sakura_no")
    private String sakuraNo;

    @Column(name = "ref_no")
    private String refrenceNo;

    @ManyToOne
    @JoinColumn(name = "manufactur_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeDetail typeDetail;

    @Column(name = "primary_app")
    private String primaryApplication;

    @ManyToOne
    @JoinColumn(name = "sakura_no" , referencedColumnName = "sakura_no",insertable = false,updatable = false, foreignKey = @ForeignKey(name = "none"))
    private ProductDetail productDetail;

    @Column(name = "last_updated")
    private String last_updated;

    @Column(name = "active")
    private int active;

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public String getSakuraNo() {
        return sakuraNo;
    }

    public void setSakuraNo(String sakuraNo) {
        this.sakuraNo = sakuraNo;
    }

    public String getRefrenceNo() {
        return refrenceNo;
    }

    public void setRefrenceNo(String refrenceNo) {
        this.refrenceNo = refrenceNo;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public TypeDetail getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(TypeDetail typeDetail) {
        this.typeDetail = typeDetail;
    }

    public String getPrimaryApplication() {
        return primaryApplication;
    }

    public void setPrimaryApplication(String primaryApplication) {
        this.primaryApplication = primaryApplication;
    }

}
