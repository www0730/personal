package com.zzptc.twds.pojo;

public class Oworkload {
    private Integer oid;
    private Integer state;
    private String otype;
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

    private Double ovalue;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype == null ? null : otype.trim();
    }

    public Double getOvalue() {
        return ovalue;
    }

    public void setOvalue(Double ovalue) {
        this.ovalue = ovalue;
    }
}