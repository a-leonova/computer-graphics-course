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

    private final double MIN_ABSORPTION_VALUE = 0;
    private final double MAX_ABSORPTION_VALUE = 1;

    private final int SIZE_X = 101;
    private final int MIN_X = 0;
    private final int MAX_X = 100;

    private final int COUNT_RGB = 3;
    private final int MAX_RGB = 255;
    private final int MIN_RGB = 0;

    private final List<Absorption> absorptionPoints = new ArrayList<>();
    private final List<Emission> emissionPoints = new ArrayList<>();
    private final List<Charge> charges = new ArrayList<>();

    private double[] absorption = new double[SIZE_X];
    private int[][] emission = new int[SIZE_X][COUNT_RGB];

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
                    double greenIncrement = (double) (cur.getGreen() - prevEm.getGreen()) / (cur.getX() - prevEm.getX());
                    double blueIncrement = (double) (cur.getBlue() - prevEm.getBlue()) / (cur.getX() - prevEm.getX());
                    emission[i + prevEm.getX()][0] = (int)(prevEm.getRed() + i * redIncrement);
                    emission[i + prevEm.getX()][1] = (int)(prevEm.getGreen() + i * greenIncrement);
                    emission[i + prevEm.getX()][2] = (int)(prevEm.getBlue() + i * blueIncrement);
                }
            }
            prevEm = cur;
        }
    }

    public int getEmissionWidth(){
        return SIZE_X;
    }

    public int getEmissionHeight(){
        return COUNT_RGB;
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
            throw new IllegalArgumentException("Number of absorption points < 2 (at least must be start and end");
        }
        Absorption absorption = readNextAbsorption(scanner);
        if (absorption.getX() != 0) {
            throw new IllegalArgumentException("No data for adsorption start. Add it!");
        }
        absorptionPoints.add(absorption);
        for (int i = 0; i < numOfAbs - 2; i++) {
            absorptionPoints.add(readNextAbsorption(scanner));
        }
        absorption = readNextAbsorption(scanner);
        if (absorption.getX() != MAX_X) {
            throw new IllegalArgumentException("No data for adsorption end. Add it!");
        }
        absorptionPoints.add(absorption);
    }

    private void readEmissions(Scanner scanner) {
        int numOfEmissions = Integer.parseInt(readNextNumbers(scanner, 1)[0]);
        if (numOfEmissions < 2) {
            throw new IllegalArgumentException("Number of emission points < 2 (at least must be start and end");
        }
        Emission emission = readNextEmission(scanner);
        if (emission.getX() != 0) {
            throw new IllegalArgumentException("No data for emission start. Add it!");
        }
        emissionPoints.add(emission);
        for (int i = 0; i < numOfEmissions - 2; i++) {
            emissionPoints.add(readNextEmission(scanner));
        }
        emission = readNextEmission(scanner);
        if (emission.getX() != MAX_X) {
            throw new IllegalArgumentException("No data for emission end. Add it!");
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
        if (x < MIN_X || x > MAX_X || value < MIN_ABSORPTION_VALUE || value > MAX_ABSORPTION_VALUE) {
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
        if (x < MIN_X || x > MAX_X
                || red < MIN_RGB || red > MAX_RGB
                || green < MIN_RGB || green > MAX_RGB
                || blue < MIN_RGB || blue > MAX_RGB) {
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
        if (c < MIN_RGB) {
            return MIN_RGB;
        } else if (c > MAX_RGB) {
            return MAX_RGB;
        } else {
            return c;
        }
    }
}
