package Model;

public class XInt {
    private double value;

    public XInt() {
    }

    public XInt(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public String convertToBin(int l) {
        int roundedValue = (int) Math.round(this.value);
        String binary = Integer.toBinaryString(roundedValue);
        return String.format("%" + l + "s", binary).replace(' ', '0');
    }

    public double convertToReal(double a, double b, int l) {
        return value * (b - a) / (Math.pow(2, l) - 1) + a;
    }


}
