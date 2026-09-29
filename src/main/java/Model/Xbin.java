package Model;

public class Xbin {
    private String value;

    public Xbin() {
    }

    public Xbin(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public double convertToInt() {
        int intValue = Integer.parseInt(this.value, 2);
        double doubleValue = (double) intValue;
        return doubleValue;
    }

}
