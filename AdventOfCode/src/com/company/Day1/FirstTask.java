package com.company.Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class FirstTask {
    LinkedList<Integer> input = new LinkedList<Integer>();

    public FirstTask() {
        File file = new File("./src/com/company/Day1/dayOne.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                this.input.add(parseInt(sc.next()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public Integer findOutputFor2() {
        Integer[] output = new Integer[2];
        for (Integer value : this.input) {
            for (Integer val : this.input) {
                if (value + val == 2020) {
                    output[0] = value;
                    output[1] = val;
                }
            }
        }
        return output[0] * output[1];
    }

    public Integer findOutputFor3() {
        Integer[] output = new Integer[3];
        for (Integer val1 : this.input) {
            for (Integer val2 : this.input) {
                for (Integer val3 : this.input) {
                    if ((val1 + val2 + val3) == 2020) {
                        output[0] = val1;
                        output[1] = val2;
                        output[2] = val3;
                    }
                }
            }
        }
        return output[0] * output[1] * output[2];
    }

}
