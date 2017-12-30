package mainPackage;

public class ListElement {

    public String getRValue() {
        return rValue;
    }

    public void setRValue(String rValue) {
        this.rValue = rValue;
    }



    public String getFirstVal() {
        return firstVal;
    }

    public void setFirstVal(String firstVal) {
        this.firstVal = firstVal;
    }

    public String getSecondVal() {
        return secondVal;
    }

    public void setSecondVal(String secondVal) {
        this.secondVal = secondVal;
    }

    public String getThirdVal() {
        return thirdVal;
    }

    public void setThirdVal(String thirdVal) {
        this.thirdVal = thirdVal;
    }

    public String getQuattroVal() {
        return quattroVal;
    }

    public void setQuattroVal(String quattroVal) {
        this.quattroVal = quattroVal;
    }

    public String secondVal;
    public String thirdVal;
    public String quattroVal;
    public String rValue;
    public String firstVal;

    public ListElement(String value) {
        rValue = value;
    }
    public ListElement() {

    }
}

