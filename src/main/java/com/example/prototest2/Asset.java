package com.example.prototest2;

public class Asset {
    private int AUID;
    private String AName;
    private int AQuantity;
    private String AType;
    private String AReported;
    private String AccountUserName;
    private String AWhere;

    private String expense;
    private double currentValue;
    private double purchaseValue;
    private double interest;

    public Asset(int AUID, String AName, int AQuantity, String AType, String AReported, String AccountUserName, String AWhere, String expense, double currentValue, double purchaseValue, double interest) {
        this.AUID = AUID;
        this.AName = AName;
        this.AQuantity = AQuantity;
        this.AType = AType;
        this.AReported = AReported;
        this.AccountUserName = AccountUserName;
        this.AWhere = AWhere;
        this.currentValue = currentValue;
        this.expense = expense;
        this.purchaseValue = purchaseValue;
        this.interest = interest;
    }

    public int getAUID() {
        return AUID;
    }

    public String getAName() {
        return AName;
    }

    public int getAQuantity() {
        return AQuantity;
    }

    public String getAType() {
        return AType;
    }

    public String getAReported() {
        return AReported;
    }

    public String getAccountUserName() {
        return AccountUserName;
    }

    public String getAWhere() {
        return AWhere;
    }

    public String getExpense() {
        return expense;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getPurchaseValue() {
        return purchaseValue;
    }

    public double getInterest() {
        return interest;
    }

    public void setAName(String AName) {
        this.AName = AName;
    }

    public void setAQuantity(int AQuantity) {
        this.AQuantity = AQuantity;
    }

    public void setAType(String AType) {
        this.AType = AType;
    }

    public void setAReported(String AReported) {
        this.AReported = AReported;
    }

    public void setAccountUserName(String AccountUserName) {
        this.AccountUserName = AccountUserName;
    }

    public void setAWhere(String AWhere) {
        this.AWhere = AWhere;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
    public void setExpense(String expense) {
        this.expense = expense;
    }
}
