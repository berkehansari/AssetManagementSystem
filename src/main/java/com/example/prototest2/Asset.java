package com.example.prototest2;

public class Asset {
    private int AUID;
    private String AName;
    private int AQuantity;
    private String AType;
    private String AReported;
    private String AccountUserName;
    private String AWhere;

    public Asset(int AUID, String AName, int AQuantity, String AType, String AReported, String AccountUserName, String AWhere) {
        this.AUID = AUID;
        this.AName = AName;
        this.AQuantity = AQuantity;
        this.AType = AType;
        this.AReported = AReported;
        this.AccountUserName = AccountUserName;
        this.AWhere = AWhere;
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
}
