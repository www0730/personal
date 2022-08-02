package com.zzptc.twds.pojo;

public class Scientific {
    private Integer scientificId;
    private Integer state;
    private String scientificName;
    private double para;
    public double getPara() {
        return para;
    }

    public void setPara(double para) {
        this.para = para;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private Double value;

    public Integer getScientificId() {
        return scientificId;
    }

    public void setScientificId(Integer scientificId) {
        this.scientificId = scientificId;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}