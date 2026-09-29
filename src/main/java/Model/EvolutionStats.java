package Model;

import java.util.ArrayList;
import java.util.List;

public class EvolutionStats {
    private final List<Double> minFx = new ArrayList<>();
    private final List<Double> maxFx = new ArrayList<>();
    private final List<Double> avgFx = new ArrayList<>();

    public void addIterationStats(List<Chromosome> population) {
        double min = population.stream().mapToDouble(Chromosome::getFx).min().orElse(0);
        double max = population.stream().mapToDouble(Chromosome::getFx).max().orElse(0);
        double avg = population.stream().mapToDouble(Chromosome::getFx).average().orElse(0);

        minFx.add(min);
        maxFx.add(max);
        avgFx.add(avg);
    }

    public List<Double> getMinFitness() { return minFx; }
    public List<Double> getMaxFitness() { return maxFx; }
    public List<Double> getAvgFitness() { return avgFx; }

}