package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.model.volumeRendering.Absorption;
import com.nsu.fit.leonova.model.volumeRendering.Charge;
import com.nsu.fit.leonova.model.volumeRendering.Emission;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigFileLoader {

    private final List<Absorption> absorptionPoints = new ArrayList<>();
    private final List<Emission> emissionPoints = new ArrayList<>();
    private final List<Charge> charges = new ArrayList<>();

    private double[] absorption = new double[101];
    private int[][] emission = new int[101][3];

    public ConfigFileLoader(File file) {
        try (Scanner scanner = new Scanner(file)) {
            readAbsorptions(scanner);
            readEmissions(scanner);
            readCharges(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Absorption prevAbs = new Absorption(-1, 0.0);
        for (Absorption cur : absorptionPoints) {
            if (cur.getX() != prevAbs.getX()) {
                absorption[cur.getX()] = cur.getValue();
                for (int i = 1; i < cur.getX() - prevAbs.getX(); i++) {
                    double increment = (cur.getValue() - prevAbs.getValue()) / (cur.getX() - prevAbs.getX());
                    absorption[i + prevAbs.getX()] = prevAbs.getValue() + i * increment;
                }
            }
            prevAbs = cur;
        }
        Emission prevEm = new Emission(-1, 0, 0, 0);
        for (Emission cur : emissionPoints) {
            if (cur.getX() != prevEm.getX()) {
                emission[cur.getX()][0] = cur.getRed();
                emission[cur.getX()][1] = cur.getGreen();
                emission[cur.getX()][2] = cur.getBlue();
                for (int i = 1; i < cur.getX() - prevEm.getX(); i++) {
                    double redIncrement = (double) (cur.getRed() - prevEm.getRed()) / (cur.getX() - prevEm.getX());
                    double greenIncrement = (double) (cur.getGreen() - prevEm.getRed()) / (cur.getX() - prevEm.getX());
                    double blueIncrement = (double) (cur.getBlue() - prevEm.getBlue()) / (cur.getX() - prevEm.getX());
                    emission[i + prevEm.getX()][0] = trim((int)(prevEm.getRed() + i * redIncrement));
                    emission[i + prevEm.getX()][1] = trim((int)(prevEm.getGreen() + i * greenIncrement));
                    emission[i + prevEm.getX()][2] = trim((int)(prevEm.getBlue() + i * blueIncrement));
                }
            }
            prevEm = cur;
        }
    }


    public List<Absorption> getAbsorptionPoints() {
        return absorptionPoints;
    }

    public List<Emission> getEmissionPoints() {
        return emissionPoints;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public double[] getAbsorption() {
        return absorption;
    }

    public int[][] getEmission() {
        return emission;
    }

    private void readAbsorptions(Scanner scanner) {
        int numOfAbs = Integer.parseInt(readNextNumbers(scanner, 1)[0]);
        if (numOfAbs < 2) {
            throw new IllegalArgumentException("Number of absorption points < 2 (at least at start and end");
        }
        Absorption absorption = readNextAbsorption(scanner);
        if (absorption.getX() != 0) {
            throw new IllegalArgumentException("No data for start. Add it!");
        }
        absorptionPoints.add(absorption);
        for (int i = 0; i < numOfAbs - 2; i++) {
            absorptionPoints.add(readNextAbsorption(scanner));
        }
        absorption = readNextAbsorption(scanner);
        if (absorption.getX() != 100) {
            throw new IllegalArgumentException("No data for end. Add it!");
        }
        absorptionPoints.add(absorption);
    }

    private void readEmissions(Scanner scanner) {
        int numOfEmissions = Integer.parseInt(readNextNumbers(scanner, 1)[0]);
        if (numOfEmissions < 2) {
            throw new IllegalArgumentException("Number of absorption points < 2 (at least at start and end");
        }
        Emission emission = readNextEmission(scanner);
        if (emission.getX() != 0) {
            throw new IllegalArgumentException("No data for start. Add it!");
        }
        emissionPoints.add(emission);
        for (int i = 0; i < numOfEmissions - 2; i++) {
            emissionPoints.add(readNextEmission(scanner));
        }
        emission = readNextEmission(scanner);
        if (emission.getX() != 100) {
            throw new IllegalArgumentException("No data for end. Add it!");
        }
        emissionPoints.add(emission);
    }

    private void readCharges(Scanner scanner) {
        int numOfCharges = Integer.parseInt(readNextNumbers(scanner, 1)[0]);
        if (numOfCharges < 1) {
            throw new IllegalArgumentException("There is no charges at all.. Add it!");
        }
        for (int i = 0; i < numOfCharges; i++) {
            charges.add(readNextCharge(scanner));
        }
    }

    private Absorption readNextAbsorption(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 2);
        int x = Integer.parseInt(numbers[0]);
        double value = Double.parseDouble(numbers[1]);
        if (x < 0 || x > 100 || value < 0 || value > 1) {
            throw new IllegalArgumentException("Absorption in range [0; 1] and X - [0; 100]");
        }
        return new Absorption(x, value);
    }

    private Emission readNextEmission(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 4);
        int x = Integer.parseInt(numbers[0]);
        int red = Integer.parseInt(numbers[1]);
        int green = Integer.parseInt(numbers[2]);
        int blue = Integer.parseInt(numbers[3]);
        if (x < 0 || x > 100
                || red < 0 || red > 255
                || green < 0 || green > 255
                || blue < 0 || blue > 255) {
            throw new IllegalArgumentException("Emission X - [0; 100] and red, green blue in range [0; 255]");
        }
        return new Emission(x, red, green, blue);
    }

    private Charge readNextCharge(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 4);
        double x = Double.parseDouble(numbers[0]);
        double y = Double.parseDouble(numbers[1]);
        double z = Double.parseDouble(numbers[2]);
        double q = Double.parseDouble(numbers[3]);
        return new Charge(x, y, z, q);
    }

    private String[] readNextNumbers(Scanner scanner, int n) {
        String line;
        do {
            line = scanner.nextLine();
            int commentBegin = line.indexOf("//");
            if (commentBegin != -1) {
                line = line.substring(0, commentBegin);
            }
        } while ("".equals(line));
        String[] numbers = line.split(" ");
        if (numbers.length != n) {
            throw new IllegalArgumentException("Bad number of parameters");
        }
        return numbers;
    }

    private int trim(int c) {
        if (c < 0) {
            return 0;
        } else if (c > 255) {
            return 255;
        } else {
            return c;
        }
    }
}
