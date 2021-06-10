package com.zy.horizontalproject27;

public class GunAddOilStatusInfo {
    private int messageType;//消息类型
    public int npanel;
    public int userId;
    public int gunStaus;
    public int returnPayType;
    public String oilCount;
    public String moneyCount;
    public String priceCount;
    public int discountMode;
    public String realCount;
    public String discountCount;
    public String balance;
    public int unit;
    public int integral;

    public int getNpanel() {
        return npanel;
    }

    public void setNpanel(int npanel) {
        this.npanel = npanel;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGunStaus() {
        return gunStaus;
    }

    public void setGunStaus(int gunStaus) {
        this.gunStaus = gunStaus;
    }

    public int getReturnPayType() {
        return returnPayType;
    }

    public void setReturnPayType(int returnPayType) {
        this.returnPayType = returnPayType;
    }

    public String getOilCount() {
        return oilCount;
    }

    public void setOilCount(String oilCount) {
        this.oilCount = oilCount;
    }

    public String getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(String priceCount) {
        this.priceCount = priceCount;
    }

    public int getDiscountMode() {
        return discountMode;
    }

    public void setDiscountMode(int discountMode) {
        this.discountMode = discountMode;
    }

    public String getRealCount() {
        return realCount;
    }

    public void setRealCount(String realCount) {
        this.realCount = realCount;
    }

    public String getDiscountCount() {
        return discountCount;
    }

    public void setDiscountCount(String discountCount) {
        this.discountCount = discountCount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public GunAddOilStatusInfo(int msgType, int npanel, int userId, int gunStaus, int returnPayType, String oilCount, String moneyCount, String priceCount, int discountMode, String realCount, String discountCount, String balance, int unit, int integral) {
        this.messageType = msgType;
        this.npanel = npanel;
        this.userId = userId;
        this.gunStaus = gunStaus;
        this.returnPayType = returnPayType;
        this.oilCount = oilCount;
        this.moneyCount = moneyCount;
        this.priceCount = priceCount;
        this.discountMode = discountMode;
        this.realCount = realCount;
        this.discountCount = discountCount;
        this.balance = balance;
        this.unit = unit;
        this.integral = integral;
    }

    public GunAddOilStatusInfo(int npanel, int userId, int gunStaus, int returnPayType, String oilCount, String moneyCount, String priceCount, int discountMode, String realCount, String discountCount, String balance, int unit, int integral) {
        this.npanel = npanel;
        this.userId = userId;
        this.gunStaus = gunStaus;
        this.returnPayType = returnPayType;
        this.oilCount = oilCount;
        this.moneyCount = moneyCount;
        this.priceCount = priceCount;
        this.discountMode = discountMode;
        this.realCount = realCount;
        this.discountCount = discountCount;
        this.balance = balance;
        this.unit = unit;
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "GunAddOilStatusInfo{" +
                "npanel=" + npanel +
                ", userId=" + userId +
                ", gunStaus=" + gunStaus +
                ", returnPayType=" + returnPayType +
                ", oilCount='" + oilCount + '\'' +
                ", moneyCount='" + moneyCount + '\'' +
                ", priceCount='" + priceCount + '\'' +
                ", discountMode=" + discountMode +
                ", realCount='" + realCount + '\'' +
                ", discountCount='" + discountCount + '\'' +
                ", balance='" + balance + '\'' +
                ", unit=" + unit +
                ", integral=" + integral +
                '}';
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
