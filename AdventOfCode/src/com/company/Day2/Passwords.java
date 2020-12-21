package com.company.Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Passwords {

    LinkedList<String> input = new LinkedList<String>();

    LinkedList<Case> cases = new LinkedList<Case>();
    public Passwords() {
        File file = new File("./src/com/company/Day2/dayTwoInput.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                this.input.add(sc.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        splitInput();
    }

    public void splitInput(){
        String number="";
        String letter ="";
        String password = "";

        for(int i =0;i< this.input.size()-1;i++){
            switch (i%3){
                case 0:
                    number = this.input.get(i);
                    break;
                case 1:
                    letter = this.input.get(i);
                    break;
                case 2:
                    password = this.input.get(i);
                    this.cases.add(new Rule(number,letter,password));
                    break;
            }
        }
        assert cases.size() == input.size()/3;
    }

    public countValid(){
        for(Case case : cases){

        }
    }

    private Integer howManyLetters(String input){
        for(int i =0 ; i< input.length(); i++){

        }


        return output;
    }
    private class Case{
        public Rule(String number, String letter, String password){
            this.number = number;
            this.letter = letter;
            this.password = password;
        }
        public String number;
        public String letter;
        public String password;

    }
}
