package org.example.service;
import org.example.entity.Address;
import org.example.entity.User;
import org.example.entity.enums.Role;

import java.util.Objects;

import static org.example.db.DataSource.*;
public class AuthService {

    public static void service(){
        String menu = """
                 HOTEL MENU😊:
              0.Exit
              1.Sign Up
              2.Sign In
              """;
        while (true){
            System.out.println(menu);
            switch (intScanner.nextInt()){
                case 0 ->{
                    System.out.println("Thank you for choosing us😊...");
                    return;
                }
                case 1 ->{
                    signUp();
                }
                case 2 ->{
                    signIn();
                }
                default -> {
                    System.out.println("Input error, please try again😔...");
                }
            }
        }
    }

    private static void signIn() {
        System.out.println("Enter ID: ");
        String id  = strScanner.nextLine();

        System.out.println("Enter password: ");
        String password = strScanner.nextLine();


        User user = users
                .stream()
                .filter(u -> u.getId().equals(id) && u.getPassword().equals(password))
                .findAny()
                .orElse(null);

        if(user!=null){
            currentUser = user;
            if(Objects.equals(user.getRole(), Role.ADMIN)){
                AdminService.service();
            }else if(Objects.equals(user.getRole(), Role.USER)){
                UserService.service();
            }
        } else{
            System.out.println("Such a user does not exist or there was an input error😔...");
        }


    }

    private static void signUp() {
        //User
        User user  = new User();
        System.out.println("Enter Id: ");

        String id = strScanner.nextLine();
        user.setId(id);

        boolean b = users.stream().anyMatch(u -> u.getId().equals(id));
        if(b){
            User userT = users.stream().filter(u -> u.getId().equals(id)).findAny().orElse(null);

            if(userT.getPassword()==null){
                System.out.println("You have been logged in by admin😊...");
                System.out.println("Just enter your password:  ");
                String password = strScanner.nextLine();
                userT.setPassword(password);
                System.out.println("Password has been set🫡...");


            }else{
                System.out.println("You are in the system😇...");
            }


        }else{
            System.out.println("Enter name: ");
            user.setName(strScanner.nextLine());
            System.out.println("Enter Sur name: ");
            user.setSurName(strScanner.nextLine());
            System.out.println("Enter balance: ");
            user.setBalance(intScanner.nextDouble());
            user.setRole(Role.USER);
            System.out.println("Enter password: ");
            user.setPassword(strScanner.nextLine());
            //Address
            Address address = new Address();
            System.out.println("Enter country: ");
            address.setCountry(strScanner.nextLine());
            System.out.println("Enter region: ");
            address.setRegion(strScanner.nextLine());
            System.out.println("Enter city: ");
            address.setCity(strScanner.nextLine());
            System.out.println("Enter home number: ");
            address.setHomeNumber(intScanner.nextInt());
            user.setAddress(address);
            users.add(user);
            System.out.println("You have successfully registered😊...");
        }

        //Test

    }



}
