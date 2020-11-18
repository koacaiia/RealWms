package org.techtown.realwms;

public class Cargo {
    private String consignee;
    private String cargo_v;
    private String pqty;
    private String qty;
    private String bl;
    private String des;
    private String cont;
    private String seal;

    public Cargo(){}

    public Cargo(String consignee, String cargo_v, String qty, String pqty, String bl, String des
            , String cont, String seal) {
        this.consignee = consignee;
        this.cargo_v = cargo_v;
        this.pqty = pqty;
        this.qty = qty;
        this.bl = bl;
        this.des = des;
        this.cont = cont;
        this.seal = seal;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCargo_v() {
        return cargo_v;
    }

    public void setCargo_v(String cargo_v) {
        this.cargo_v = cargo_v;
    }

    public String getPqty() {
        return pqty;
    }

    public void setPqty(String pqty) {
        this.pqty = pqty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }
}


