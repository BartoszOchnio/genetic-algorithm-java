package Model;

public class Xreal {
    private double value;
    public Xreal() {
    }
    public Xreal(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public double convertToInt(double a, double b, int l) {
        return (this.value - a) * ((Math.pow(2, l) - 1) / (b - a));
    }
}
