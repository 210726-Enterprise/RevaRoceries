package com.revature.service;

import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {

    private static final List<User> listOfUsers = new ArrayList<>();

    Scanner scanner;

    public UserService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void createUser(){
        System.out.println("Enter the following");

        User user = new User();

        System.out.print("First name: ");
        user.setFirstName(scanner.nextLine());
        System.out.println();

        System.out.print("Last name: ");
        user.setLastName(scanner.nextLine());
        System.out.println();

        System.out.print("Username: ");
        user.setUsername(scanner.nextLine());
        System.out.println();

        System.out.print("Password: ");
        user.setPassword(scanner.nextLine());
        System.out.println();

        listOfUsers.add(user);
        // instead
    }

    public void printUsers(){
        System.out.println("Users: ");
        System.out.println(listOfUsers);
    }

    public boolean login(){
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        return validate(username, password);
    }

    private boolean validate(String username, String password){

        User user = null;

        for(User u: listOfUsers){
            if(u.getUsername().equals(username)){
                user = u;
            }
        }

        if(user == null) {
            return false;
        }

        if(user.getPassword().equals(password)) {
            return true;
        } else{
            return false;
        }
    }
}
