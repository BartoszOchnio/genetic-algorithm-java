package Gui;

import Model.Chromosome;
import Model.EvolutionStats;
import Model.GaResult;
import Model.Test;
import Service.GeneticMath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.*;
import java.util.List;


public class Form {
    private JLabel jLabelA;
    private JLabel jLabelB;
    private JLabel jLabelN;
    private JLabel jLabelT;
    private JLabel jLabelD;
    private JLabel jLabelPk;
    private JLabel jLabelPm;
    private JTextField jTextFieldA;
    private JTextField jTextFieldB;
    private JTextField jTextFieldN;
    private JTextField jTextFieldT;
    private JTextField jTextFieldPk;
    private JTextField jTextFieldPm;
    private JTable jTable;
    private JButton jButtonStart, jButtonTestStart;
    private JComboBox<String> jComboBoxD;
    private JRadioButton radioButtonMax;
    private JRadioButton radioButtonMin;
    private JCheckBox checkBoxElite, checkBoxElite2;
    private JPanel chartContainer;
    private ChartPanel chartPanel;

    public Form() {
        JFrame frame = new JFrame("Algorytm Genetyczny");
        frame.setSize(1000, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel algorithmPanel = new JPanel();


        JPanel testPanel = new JPanel();
        checkBoxElite2 = new JCheckBox("Elita");
        jButtonTestStart = new JButton("Start");
        testPanel.add(checkBoxElite2);
        testPanel.add(jButtonTestStart);
        String[] testColumnNames = {"Lp.", "N", "Pk", "Pm", "T", "Favg(x)"};
        DefaultTableModel testModel = new DefaultTableModel(null, testColumnNames);
        JTable testTable = new JTable(testModel);
        JScrollPane testScrollPane = new JScrollPane(testTable);
        testScrollPane.setPreferredSize(new Dimension(800, 400));

        jButtonTestStart.addActionListener(e -> {
            try {
                handleTestStartButtonClick(testModel);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Błąd: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        testPanel.add(testScrollPane);

        tabbedPane.addTab("Algorytm", algorithmPanel);
        tabbedPane.addTab("Test", testPanel);
        frame.add(tabbedPane);

        jTextFieldA = new JTextField("-4", 5);
        jTextFieldB = new JTextField("12", 5);
        jTextFieldN = new JTextField("100", 5);
        jTextFieldT = new JTextField("100", 5);
        jTextFieldPk = new JTextField("0.8", 5);
        jTextFieldPm = new JTextField("0.001", 5);

        algorithmPanel.add(new JLabel("a:"));
        algorithmPanel.add(jTextFieldA);
        algorithmPanel.add(new JLabel("b:"));
        algorithmPanel.add(jTextFieldB);
        algorithmPanel.add(new JLabel("n:"));
        algorithmPanel.add(jTextFieldN);
        algorithmPanel.add(new JLabel("T:"));
        algorithmPanel.add(jTextFieldT);
        algorithmPanel.add(new JLabel("d:"));

        String[] dOptions = {"0.01", "0.001", "0.0001", "0.00001", "0.000001"};
        jComboBoxD = new JComboBox<>(dOptions);
        algorithmPanel.add(jComboBoxD);

        algorithmPanel.add(new JLabel("Pk:"));
        algorithmPanel.add(jTextFieldPk);
        algorithmPanel.add(new JLabel("Pm:"));
        algorithmPanel.add(jTextFieldPm);

        radioButtonMax = new JRadioButton("Maksimum");
        radioButtonMin = new JRadioButton("Minimum");
        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonMax);
        group.add(radioButtonMin);
        radioButtonMax.setSelected(true);

        algorithmPanel.add(radioButtonMax);
        algorithmPanel.add(radioButtonMin);

        checkBoxElite = new JCheckBox("Elita");
        algorithmPanel.add(checkBoxElite);

        jButtonStart = new JButton("Start");
        algorithmPanel.add(jButtonStart);

        String[] columnNames = {"Lp.", "xReal", "xBin", "f(x)", "%"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        jTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        algorithmPanel.add(scrollPane);

        chartContainer = new JPanel();
        chartContainer.setPreferredSize(new Dimension(800, 400));
        chartContainer.setLayout(new BorderLayout());
        algorithmPanel.add(chartContainer);

        jButtonStart.addActionListener(e -> {
            try {
                handleStartButtonClick(model);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Błąd: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void handleStartButtonClick(DefaultTableModel model) throws ParseException {
        int a = Integer.parseInt(jTextFieldA.getText());
        int b = Integer.parseInt(jTextFieldB.getText());
        int n = Integer.parseInt(jTextFieldN.getText());
        int t = Integer.parseInt(jTextFieldT.getText());
        double d = Double.parseDouble((String) jComboBoxD.getSelectedItem());
        double pk = Double.parseDouble(jTextFieldPk.getText());
        double pm = Double.parseDouble(jTextFieldPm.getText());
        boolean isMax = radioButtonMax.isSelected();
        boolean isElite = checkBoxElite.isSelected();

        model.setRowCount(0);

        GaResult result = GeneticMath.runGa(a, b, d, n, t, isMax, isElite, pk, pm, false);
        List<Chromosome> population = result.getPopulation();
        EvolutionStats stats = result.getStats();

        updateTableWithFrequency(population, model);

        chartContainer.removeAll();
        ChartPanel newChartPanel = createChart(stats);
        chartContainer.add(newChartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }

    private void handleTestStartButtonClick(DefaultTableModel model) throws ParseException {

        boolean isElite = checkBoxElite2.isSelected();
        boolean isMax = true;

        double d = Double.parseDouble((String) jComboBoxD.getSelectedItem());
        List<Test> testList = GeneticMath.gaTest(-4, 12, 0.0001, isMax, isElite);

        testList.sort((a, b) -> Double.compare(b.getAvgFx(), a.getAvgFx()));

        model.setRowCount(0);

        int index = 1;
        for (Test t : testList) {
            model.addRow(new Object[]{
                    index++,
                    t.getN(),
                    String.format("%.2f", t.getPk()),
                    String.format("%.4f", t.getPm()),
                    t.getT(),
                    t.getAvgFx()
            });
        }
    }
    private void updateTableWithFrequency(List<Chromosome> population, DefaultTableModel model) {
        Map<Double, Integer> frequencyMap = new HashMap<>();

        for (Chromosome c : population) {
            double fx = c.getFx();
            frequencyMap.put(fx, frequencyMap.getOrDefault(fx, 0) + 1);
        }

        List<Object[]> tableRows = new ArrayList<>();

        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            double fx = entry.getKey();
            int count = entry.getValue();
            double percent = 100.0 * count / population.size();

            Chromosome sample = population.stream()
                    .filter(c -> c.getFx() == fx)
                    .findFirst()
                    .orElse(null);

            if (sample != null) {
                tableRows.add(new Object[]{
                        0,
                        sample.getxReal().getValue(),
                        sample.getxBin().getValue(),
                        fx,
                        (int) percent
                });
            }
        }

        tableRows.sort((a, b) -> Double.compare((double) b[3], (double) a[3]));

        for (int i = 0; i < tableRows.size(); i++) {
            tableRows.get(i)[0] = i + 1;
        }

        model.setRowCount(0);
        for (Object[] row : tableRows) {
            model.addRow(row);
        }
    }

    private ChartPanel createChart(EvolutionStats stats) {
        XYSeries minSeries = new XYSeries("Min f(x)");
        XYSeries maxSeries = new XYSeries("Max f(x)");
        XYSeries avgSeries = new XYSeries("Avg f(x)");

        List<Double> minFx = stats.getMinFitness();
        List<Double> maxFx = stats.getMaxFitness();
        List<Double> avgFx = stats.getAvgFitness();

        for (int i = 0; i < minFx.size(); i++) {
            minSeries.add(i + 1, minFx.get(i));
            maxSeries.add(i + 1, maxFx.get(i));
            avgSeries.add(i + 1, avgFx.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(minSeries);
        dataset.addSeries(maxSeries);
        dataset.addSeries(avgSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Postęp ewolucji",
                "Iteracja",
                "f(x)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        chart.getXYPlot().getRangeAxis().setRange(-2.2, 2.2);

        return new ChartPanel(chart);
    }



}

