package Model;

public class Test {
    private int n;
    private double pk;
    private double pm;
    private int t;
    private double avgFx;

    public Test() {}

    public Test(int n, double pk, double pm, int t, double avgFx) {
        this.n = n;
        this.pk = pk;
        this.pm = pm;
        this.t = t;
        this.avgFx = avgFx;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getPk() {
        return pk;
    }

    public void setPk(double pk) {
        this.pk = pk;
    }

    public double getPm() {
        return pm;
    }

    public void setPm(double pm) {
        this.pm = pm;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public double getAvgFx() {
        return avgFx;
    }

    public void setAvgFx(double avgFx) {
        this.avgFx = avgFx;
    }
}
