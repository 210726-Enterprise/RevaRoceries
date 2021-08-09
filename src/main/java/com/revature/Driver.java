package com.revature;

import com.revature.models.User;
import com.revature.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService(scanner);


    public static void main(String[] args) {
        System.out.println("Welcome to RevaRoceries!");
        welcomeMenu();

    }

    public static void welcomeMenu(){

        System.out.println("What would you like to do?");
        System.out.println("1) Create an Account");
        System.out.println("2) Log In");
        System.out.println("3) List of Users");
        System.out.println("0) Exit");

        String temp = scanner.nextLine();

        switch (temp){
            case "1": userService.createUser();
                break;
            case "2":
                System.out.println(userService.login());
                break;
            case "3": userService.printUsers();
                break;
            case "0":
                System.exit(0);
            default:
                System.out.println("Please input only 1 or 2.");
        }

        welcomeMenu();
    }
}
