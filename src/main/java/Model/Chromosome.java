package Model;

public class Chromosome {
    private Xreal xReal;
    private XInt xInt;
    private Xbin xBin;
    private double fx;
    private double gx;
    private double probability;
    private double distribuant;
    private double random;
    private Xreal newXReal;

    private Xbin parent;
    private int crossoverPoint;
    private Xbin child;
    private Xbin crossedIndividual;
    private String mutationGene;
    private Xbin mutant;
    private Xreal finalXReal;

    public Chromosome(Chromosome other) {
        this.xReal = new Xreal(other.getxReal().getValue());
        this.xInt = new XInt(other.getxInt().getValue());
        this.xBin = new Xbin(other.getxBin().getValue());
        this.fx = other.getFx();
        this.gx = other.getGx();
        this.probability = other.getProbability();
        this.distribuant = other.getDistribuant();
        this.random = other.getRandom();
        this.newXReal = new Xreal(other.getNewXReal().getValue());

        this.parent = other.getParent() != null ? new Xbin(other.getParent().getValue()) : null;
        this.crossoverPoint = other.getCrossoverPoint();
        this.child = other.getChild() != null ? new Xbin(other.getChild().getValue()) : null;
        this.crossedIndividual = other.getCrossedIndividual() != null ? new Xbin(other.getCrossedIndividual().getValue()) : null;
        this.mutationGene = other.getMutationGene();
        this.mutant = other.getMutant() != null ? new Xbin(other.getMutant().getValue()) : null;
        this.finalXReal = other.getFinalXReal() != null ? new Xreal(other.getFinalXReal().getValue()) : null;
    }

    public Chromosome() {
        this.xReal = new Xreal();
        this.xInt = new XInt();
        this.xBin = new Xbin();
        this.newXReal = new Xreal();
        this.parent = new Xbin();
        this.child = new Xbin();
        this.crossedIndividual = new Xbin();
        this.mutant = new Xbin();
        this.finalXReal = new Xreal();
        mutationGene ="";
    }
    public Chromosome(Xreal xReal1, XInt xInt1, Xbin xBin, double gx, double probability, double distribuant, double random, Xreal newXReal, double fx) {
        this.xReal = xReal1;
        this.xInt = xInt1;
        this.xBin = xBin;
        this.gx = gx;
        this.probability = probability;
        this.distribuant = distribuant;
        this.random = random;
        this.newXReal = newXReal;
        this.fx = fx;
    }


    public Chromosome(double x) {
        this.xReal = new Xreal(x);
        this.xInt = new XInt();
        this.xBin = new Xbin();
        this.newXReal = new Xreal();
        this.parent = new Xbin();
        this.child = new Xbin();
        this.crossedIndividual = new Xbin();
        this.mutant = new Xbin();
        this.finalXReal = new Xreal();
        mutationGene ="";
    }

    public Chromosome(Xreal xReal, XInt xInt, Xbin xBin, double fx) {
        this.xReal = xReal;
        this.xInt = xInt;
        this.xBin = xBin;
        this.fx = fx;
    }

    public Xreal getxReal() {
        return xReal;
    }

    public void setxReal(Xreal xReal) {
        this.xReal = xReal;
    }

    public XInt getxInt() {
        return xInt;
    }

    public void setxInt(XInt xInt) {
        this.xInt = xInt;
    }

    public Xbin getxBin() {
        return xBin;
    }

    public void setxBin(Xbin xBin) {
        this.xBin = xBin;
    }

    public double getFx() {
        return fx;
    }
    public void setFx(double fx) {
        this.fx = fx;
    }

    public double getGx() {
        return gx;
    }

    public void setGx(double gx) {
        this.gx = gx;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getDistribuant() {
        return distribuant;
    }

    public void setDistribuant(double distribuant) {
        this.distribuant = distribuant;
    }

    public double getRandom() {
        return random;
    }

    public void setRandom(double random) {
        this.random = random;
    }

    public Xreal getNewXReal() {
        return newXReal;
    }

    public void setNewXReal(Xreal newXReal) {
        this.newXReal = newXReal;
    }

    public Xbin getParent() {
        return parent;
    }

    public void setParent(Xbin parent) {
        this.parent = parent;
    }

    public int getCrossoverPoint() {
        return crossoverPoint;
    }

    public void setCrossoverPoint(int crossoverPoint) {
        this.crossoverPoint = crossoverPoint;
    }

    public Xbin getChild() {
        return child;
    }

    public void setChild(Xbin child) {
        this.child = child;
    }

    public Xbin getCrossedIndividual() {
        return crossedIndividual;
    }

    public void setCrossedIndividual(Xbin crossedIndividual) {
        this.crossedIndividual = crossedIndividual;
    }

    public String getMutationGene() {
        return mutationGene;
    }

    public void setMutationGene(String mutationGene) {
        this.mutationGene = mutationGene;
    }

    public Xbin getMutant() {
        return mutant;
    }

    public void setMutant(Xbin mutant) {
        this.mutant = mutant;
    }

    public Xreal getFinalXReal() {
        return finalXReal;
    }

    public void setFinalXReal(Xreal finalXReal) {
        this.finalXReal = finalXReal;
    }
}
