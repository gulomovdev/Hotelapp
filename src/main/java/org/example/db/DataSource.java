package org.example.db;

import org.example.entity.*;
import org.example.entity.enums.Role;
import org.example.entity.enums.RoomType;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class DataSource {

    public static Scanner intScanner = new Scanner(System.in);
    public static Scanner strScanner = new Scanner(System.in);


    public static User currentUser;
    public void setCurrentUser(User user){
        currentUser = user;
    }

    public static User getCurrentUser(){
        return currentUser;
    }


    public static  ArrayList<Booking> bookings = new ArrayList<>();
    public static  ArrayList<Room> rooms = new ArrayList<>();
    public static   ArrayList <History> histories = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();

    public static Room room = new Room(UUID.randomUUID().toString(),1,2, RoomType.STANDARD,150.0);
   public static Address address = new Address("UZB","Andijan","Buloqboshi",231);
   public static Address address1 = new Address("UZB","a","a",231);
   public static Address address2 = new Address("UZB","b","b",231);
   public static Address address3 = new Address("UZB","c","c",231);

   public static  User userAdmin = new User("AD123","Navruz","Gulomov",address,100000.0, Role.ADMIN,"1111");
   public static  User user1  = new User("a","a","a",address1,50.0, Role.USER,"a");
   public static  User user2  = new User("b","b","b",address2,200.0, Role.USER,"b");
   public static  User user3  = new User("c","c","c",address3,150000.0, Role.USER,"c");
  static{
      users.add(userAdmin);
      users.add(user1);
      users.add(user2);
      users.add(user3);
      rooms.add(room);
  }



    public static void refreshScanner(){
        intScanner    =new Scanner(System.in);
        strScanner =new Scanner(System.in);
    }




}
