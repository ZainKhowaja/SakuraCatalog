package com.app.sakura.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_reference")
public class ProductReference {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "reference_no")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "sakura_no")
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
