package Model;

import java.util.List;

public class GaResult {
    private final List<Chromosome> population;
    private final EvolutionStats stats;

    public GaResult(List<Chromosome> population, EvolutionStats stats) {
        this.population = population;
        this.stats = stats;
    }
    public GaResult(List<Chromosome> population) {
        this.population = population;
        stats = null;
    }

    public List<Chromosome> getPopulation() {
        return population;
    }

    public EvolutionStats getStats() {
        return stats;
    }
}
