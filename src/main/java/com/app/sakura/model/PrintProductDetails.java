package com.app.sakura.model;

import java.util.HashMap;

public class PrintProductDetails {

    private String fujiNo;
    private String filter;
    private String manufacturer;
    private String typeDetail;
    private String height;
    private String innerD;
    private String outerD;
    private String packagingPerCaton;
    private String thread;
    private String note;
    private String imageName;
    private HashMap<String,String> reference;

    private PrintProductDetails(PrintProductDetailsBuilder builder){
        this.fujiNo = builder.fujiNo;
        this.filter = builder.filter;
        this.manufacturer = builder.manufacturer;
        this.typeDetail = builder.typeDetail;
        this.height = builder.height;
        this.innerD = builder.innerD;
        this.outerD = builder.outerD;
        this.packagingPerCaton = builder.packagingPerCaton;
        this.thread = builder.thread;
        this.note = builder.note;
        this.imageName = builder.imageName;
        this.reference = builder.reference;
    }

    public String getFujiNo() {
        return fujiNo;
    }

    public String getFilter() {
        return filter;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getTypeDetail() {
        return typeDetail;
    }

    public String getHeight() {
        return height;
    }

    public String getInnerD() {
        return innerD;
    }

    public String getOuterD() {
        return outerD;
    }

    public String getPackagingPerCaton() {
        return packagingPerCaton;
    }

    public String getThread() {
        return thread;
    }

    public String getNote() {
        return note;
    }

    public String getImageName() {
        return imageName;
    }

    public HashMap<String, String> getReference() {
        return reference;
    }

    public static class PrintProductDetailsBuilder {
        private String imageName;
        private String fujiNo;
        private String filter;
        private String manufacturer;
        private String typeDetail;
        private String height;
        private String innerD;
        private String outerD;
        private String packagingPerCaton;
        private String thread;
        private String note;
        private HashMap<String, String> reference;

        private PrintProductDetailsBuilder(){
            this.reference = new HashMap<>();
        }

        public static PrintProductDetailsBuilder newBuilder(){
            return new PrintProductDetailsBuilder();
        }

        public PrintProductDetailsBuilder setImageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        public PrintProductDetailsBuilder setFujiNo(String fujiNo) {
            this.fujiNo = fujiNo;
            return this;
        }

        public PrintProductDetailsBuilder setFilter(String filter) {
            this.filter = filter;
            return this;
        }

        public PrintProductDetailsBuilder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public PrintProductDetailsBuilder setTypeDetail(String typeDetail) {
            this.typeDetail = typeDetail;
            return this;
        }

        public PrintProductDetailsBuilder setHeight(String height) {
            this.height = height;
            return this;
        }

        public PrintProductDetailsBuilder setInnerD(String innerD) {
            this.innerD = innerD;
            return this;
        }

        public PrintProductDetailsBuilder setOuterD(String outerD) {
            this.outerD = outerD;
            return this;
        }

        public PrintProductDetailsBuilder setPackagingPerCaton(String packagingPerCaton) {
            this.packagingPerCaton = packagingPerCaton;
            return this;
        }

        public PrintProductDetailsBuilder setThread(String thread) {
            this.thread = thread;
            return this;
        }

        public PrintProductDetailsBuilder setNote(String note) {
            this.note = note;
            return this;
        }

        public PrintProductDetailsBuilder setReference(HashMap<String, String> reference) {
            this.reference = reference;
            return this;
        }

        public PrintProductDetailsBuilder addReference(String reference, String brand) {
            this.reference.put(reference,brand);
            return this;
        }

        public PrintProductDetails build(){
            return new PrintProductDetails(this);
        }
    }
}
