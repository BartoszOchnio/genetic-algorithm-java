package Service;

import Model.*;

import java.util.*;
import java.util.stream.Collectors;

public class GeneticMath {

    public static GaResult runGa(int a, int b, double d, int n, int t, boolean isMax, boolean isElite, double pk, double pm, boolean isTest) {

        List<Chromosome> population = GeneticMath.initializePopulation(a, b, d, n);
        EvolutionStats stats = new EvolutionStats();

        Chromosome elite = null;

        for (int i = 0; i < t; i++) {
            if (isElite) {
                Optional<Chromosome> currentBest = isMax
                        ? population.stream().max(Comparator.comparingDouble(Chromosome::getFx))
                        : population.stream().min(Comparator.comparingDouble(Chromosome::getFx));

                if (currentBest.isPresent()) {
                    Chromosome candidate = currentBest.get();
                    if (elite == null ||
                            (isMax && candidate.getFx() > elite.getFx()) ||
                            (!isMax && candidate.getFx() < elite.getFx())) {
                        elite = new Chromosome(candidate);
                    }
                }
            }

            selection(population, a, b, d, n, isMax, isElite);
            crossoverAndMutation(population, a, b, d, pk, pm);

            if (isElite && elite != null) {
                Chromosome finalElite = elite;
                boolean eliteSurvives = population.stream()
                        .anyMatch(c -> c.getFx() == finalElite.getFx());

                if (!eliteSurvives) {
                    int worstIndex = isMax
                            ? population.indexOf(population.stream().min(Comparator.comparingDouble(Chromosome::getFx)).orElse(population.get(0)))
                            : population.indexOf(population.stream().max(Comparator.comparingDouble(Chromosome::getFx)).orElse(population.get(0)));

                    population.set(worstIndex, new Chromosome(elite));
                }
            }

            if(!isTest)
                stats.addIterationStats(population);
        }


        return new GaResult(population, stats);
    }

    public static List<Test> gaTest(
            int a,
            int b,
            double d,
            boolean isMax,
            boolean isElite
    ) {
        List<Test> testList = new ArrayList<>();

        int[] populationSizes = {30, 60};
        double[] crossoverProbabilities = {0.6, 0.8};
        double[] mutationProbabilities = {0.0001, 0.001};
        int[] iterationCounts = {50, 100};

        int repetitions = 10;

        for (int n : populationSizes) {
            for (double pk : crossoverProbabilities) {
                for (double pm : mutationProbabilities) {
                    for (int t : iterationCounts) {

                        double finalAverageSum = 0.0;

                        for (int i = 0; i < repetitions; i++) {
                            GaResult result = runGa(a, b, d, n, t, isMax, isElite, pk, pm, true);

                            double populationAverage = result.getPopulation()
                                    .stream()
                                    .mapToDouble(Chromosome::getFx)
                                    .average()
                                    .orElse(0.0);

                            finalAverageSum += populationAverage;
                        }

                        double averageResult =
                                finalAverageSum / repetitions;

                        testList.add(new Test(n, pk, pm, t, averageResult));
                    }
                }
            }
        }

        return testList;
    }
    public static void crossoverAndMutation(List<Chromosome> population, double a, double b, double d, double pk, double pm) {

        updateNewPopulation(population);

        int l = findChromosomeSize(a, b, d);
        Random random = new Random();

        prepareParents(population, a, b, l, pk, random);
        assignCrossoverPoints(population, l, random);
        performCrossover(population);
        updateCrossedIndividuals(population);
        performMutation(population, l, pm, random);
        updateFinalRealValues(population, a, b, l, d);
        evaluatePopulation(population);

        for (Chromosome c: population ) {
            c.setxReal(c.getFinalXReal());
            convertChromosome(c, a, b, d);
        }
    }
    private static void updateNewPopulation(List<Chromosome> population){
        for (Chromosome c: population) {
            if (c.getNewXReal() != null) {
                c.setxReal(new Xreal(c.getNewXReal().getValue()));
            }
        }
    }
    private static void prepareParents(List<Chromosome> population, double a, double b, int l, double pk, Random random) {
        for (Chromosome c : population) {
            c.getxInt().setValue((int) c.getxReal().convertToInt(a, b, l));
            c.getxBin().setValue(c.getxInt().convertToBin(l));
            c.setParent(random.nextDouble() < pk ? new Xbin(c.getxBin().getValue()) : null);
        }
    }
    private static void assignCrossoverPoints(List<Chromosome> population, int l, Random random) {
        for (Chromosome c : population) {
            c.setCrossoverPoint(0);
        }
        List<Chromosome> parents = population.stream()
                .filter(c -> c.getParent() != null)
                .collect(Collectors.toList());

        for (int i = 0; i < parents.size(); i += 2) {
            if (i + 1 < parents.size()) {
                int crossoverPoint = random.nextInt(l - 1) + 1;
                parents.get(i).setCrossoverPoint(crossoverPoint);
                parents.get(i + 1).setCrossoverPoint(crossoverPoint);
            } else {
                parents.get(i).setCrossoverPoint(0);
            }
        }
    }

    private static void performCrossover(List<Chromosome> population) {
        List<Chromosome> parents = population.stream()
                .filter(c -> c.getParent() != null && !c.getParent().getValue().equals("-"))
                .collect(Collectors.toList());

        for (int i = 0; i < parents.size() - 1; i += 2) {
            Chromosome p1 = parents.get(i);
            Chromosome p2 = parents.get(i + 1);
            int point = p1.getCrossoverPoint();

            p1.setChild(onePointCrossover(p1.getParent(), p2.getParent(), point));
            p2.setChild(onePointCrossover(p2.getParent(), p1.getParent(), point));
        }
    }
    private static void updateCrossedIndividuals(List<Chromosome> population) {
        for (Chromosome c : population) {
            if (c.getParent() != null && c.getChild() != null && c.getCrossoverPoint() > 0) {
                c.setCrossedIndividual(c.getChild());
            } else {
                c.setCrossedIndividual(c.getxBin());
            }
        }
    }

    private static void performMutation(List<Chromosome> population, int l, double pm, Random random) {
        for (Chromosome c : population) {
            String original = c.getCrossedIndividual().getValue();
            char[] chars = original.toCharArray();
            StringBuilder mutatedIndices = new StringBuilder();

            for (int i = 0; i < chars.length; i++) {
                if (random.nextDouble() < pm) {
                    chars[i] = (chars[i] == '0') ? '1' : '0';
                    mutatedIndices.append(i + 1).append(", ");
                }
            }

            c.setMutant(new Xbin(new String(chars)));
            c.setMutationGene(mutatedIndices.length() > 0 ? mutatedIndices.toString() : "-");
        }
    }
    private static void updateFinalRealValues(List<Chromosome> population, double a, double b, int l, double d) {
        for (Chromosome c : population) {
            XInt xInt = new XInt();
            xInt.setValue(c.getMutant().convertToInt());

            Xreal xReal = new Xreal();
            xReal.setValue(round(xInt.convertToReal(a, b, l), inversePrecision(d)));

            c.setFinalXReal(xReal);
        }
    }
    private static void evaluatePopulation(List<Chromosome> population) {
        for (Chromosome c : population) {
            c.setFx(evaluate(c.getFinalXReal().getValue()));
        }
    }

    public static Xbin onePointCrossover(Xbin parent1, Xbin parent2, int point) {
        String b1 = parent1.getValue();
        String b2 = parent2.getValue();
        String child = b1.substring(0, point) + b2.substring(point);
        return new Xbin(child);
    }

    public static void convertChromosome(Chromosome chromosome, double a, double b, double d) {
        int l = findChromosomeSize(a, b, d);
        chromosome.getxInt().setValue((int) chromosome.getxReal().convertToInt(a, b, l));
        chromosome.getxBin().setValue(chromosome.getxInt().convertToBin(l));
        chromosome.setFx(evaluate(chromosome.getxReal().getValue()));
    }

    public static void selection(List<Chromosome> population ,int a, int b, double d, int n, boolean isMax, boolean isElite){
        double fxMin = Double.POSITIVE_INFINITY;
        double fxMax = Double.NEGATIVE_INFINITY;

        for (Chromosome c: population) {
            convertChromosome(c, a, b, d);

            if (c.getFx() < fxMin)
                fxMin = c.getFx();
            if (c.getFx() > fxMax)
                fxMax = c.getFx();

        }
        double sumGx = convertFxToGx(population, fxMin, fxMax, d, isMax);
        findProbabilitiesAndDistribuant(population, sumGx);
        selectNewPopulation(population);
    }

    public static List<Chromosome> initializePopulation(int a, int b, double d, int n) {
        List<Chromosome> population = new ArrayList<>();
        Random random = new Random();

        int precision = inversePrecision(d);

        for (int i = 0; i < n; i++) {
            double x = round(a + (b - a) * random.nextDouble(), precision);
            Chromosome c = new Chromosome(x);
            population.add(c);
        }
        return population;
    }

    public static void selectNewPopulation(List<Chromosome> chromosomeList){
        Random random = new Random();

        for (int i = 0; i < chromosomeList.size(); i++) {
            double r = random.nextDouble();
            chromosomeList.get(i).setRandom(r);

            for (Chromosome c : chromosomeList) {
                if (r <= c.getDistribuant()) {
                    Xreal newReal = new Xreal(c.getxReal().getValue());
                    chromosomeList.get(i).setNewXReal(newReal);
                    break;
                }
            }
        }

    }

    public static void findProbabilitiesAndDistribuant(List<Chromosome> chromosomeList, double sum) {
        double cumulativeSum = 0;
        if (sum == 0.0) {
            double equalProb = 1.0 / chromosomeList.size();
            for (Chromosome c : chromosomeList) {
                c.setProbability(equalProb);
                cumulativeSum += c.getProbability();
                c.setDistribuant(cumulativeSum);
            }
            return;
        }
        for (Chromosome c : chromosomeList) {
            c.setProbability(c.getGx() / sum);
            cumulativeSum += c.getProbability();
            c.setDistribuant(cumulativeSum);
        }
    }

    public static double convertFxToGx(List<Chromosome> chromosomeList, double fxMin, double fxMax, double d, boolean isMax) {
        double sum = 0;
        for (Chromosome c: chromosomeList) {
            if (isMax) {
                c.setGx(c.getFx() - fxMin + d);
                sum += c.getGx();
            } else {
                c.setGx(fxMax - c.getFx() + d);
                sum += c.getGx();
            }
        }
        return sum;
    }

    public static int findChromosomeSize(double a, double b, double d) {
        return (int) Math.ceil(Math.log((b - a) / d + 1) / Math.log(2));
    }

    public static double evaluate(double real) {
        return Math.cos(20 * Math.PI * real) - Math.sin(real);
    }

    public static int inversePrecision(double d) {
        int precision = 0;
        while (d < 1) {
            d *= 10;
            precision++;
        }
        return precision;
    }

    private static double round(double value, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(value * scale) / scale;
    }

}