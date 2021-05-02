package com.LLD.splitwise;

import com.LLD.splitwise.controller.UserController;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while(true){
            System.out.println("new input?(Y/N)");
            String ques =in.nextLine();
            if(ques.equals("N"))
                break;
            System.out.println("Enter input");
            String input = in.nextLine();
            UserController.handleInput(input);
        }
    }
}
