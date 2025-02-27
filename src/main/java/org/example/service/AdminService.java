package org.example.service;
import org.example.entity.*;
import org.example.entity.enums.BookingState;
import org.example.entity.enums.Role;
import org.example.entity.enums.RoomType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Consumer;

import static org.example.db.DataSource.*;
public class AdminService {

    public static void service(){
      String menu  = """
               Admin Menu:
            0.Back🔙
            1.Add room
            2.Change room type
            3.Booking room
            4.Change booking state
            5.Show rooms
            6.Show users
            7.Show bookings
            8.Show history
            9.Cancel booking
            """;

      while(true){
          System.out.println(menu);
          switch (intScanner.nextInt()){
              case 0->{
                  System.out.println("See you in peace😊");
                  return;
              }
              case 1->{
                 addRoom();
              }
              case 2->{
                 chnageRoomState();
              }
              case 3->{
                  bookingRoom();
              }
              case 4->{
                 changeBookingState();
              }
              case 5->{
                 showRooms();
              }
              case 6->{
                  showUsers();
              }
              case 7->{
                  showBookings();
              }
              case 8->{
                  showHistory();
              }
              case 9->{
                  cancelBooking1();
              }
              
              default -> {
                  System.out.println("Input error😒...");
              }
          }
      }
    }

    private static void cancelBooking1() {
        System.out.println("Enter booking Id: ");
        String id  = strScanner.nextLine();
        boolean b1 = bookings.stream().anyMatch(b -> b.getId().equals(id));

        if(b1){
            Booking booking = bookings.stream().filter(b -> b.getId().equals(id)).findAny().orElse(null);
            int turganKunSoni =  (int) ChronoUnit.DAYS.between(booking.getDateOfReceipt(), booking.getDateOfSubmission());
            currentUser.setBalance(currentUser.getBalance() + (booking.getRoom().getPrice()*turganKunSoni));
            userAdmin.setBalance(userAdmin.getBalance()-(booking.getRoom().getPrice()*turganKunSoni));
            bookings.remove(booking);
            System.out.println("Booking has been cancelled");
            System.out.println("The user has been refunded😊 = > "+(booking.getRoom().getPrice()*turganKunSoni));

        }else{
            System.out.println("Such booking is not available😔...");
        }
    }


    private static void showHistory() {
        System.out.println("            Historys: ");
        System.out.println("===========================================================================");
        histories
                .stream()
                .forEach(System.out::println) ;
        System.out.println("===========================================================================");
    }

    static void showRooms() {
        System.out.println("     Rooms🌃:  ");
        System.out.println("===========================================================================");
               rooms
                       .stream()
                               .forEach(System.out::println) ;
        System.out.println("===========================================================================");

    }

    private static void showBookings() {
        System.out.println("         Bookings😊:  ");
        System.out.println("===============================================================================");
        bookings.stream().forEach(System.out::println);
        System.out.println("===============================================================================");
    }


    private static void showUsers() {
        System.out.println("     Users👀:  ");
        System.out.println("===========================================================================");
        users
                .stream()
                .forEach(System.out::println) ;
        System.out.println("===========================================================================");
    }


    private static void changeBookingState() {
        System.out.println("Enter booking Id: ");
        String id = strScanner.nextLine();
        Booking booking = bookings.stream().filter(b -> b.getId().equals(id)).findAny().orElse(null);

        if(booking!=null){
            bookingState(booking);
        }else{
            System.out.println("We do not have such a booking😔...");
        }

    }


    private static void bookingState(Booking booking) {
        String mn = """
   ==========================================================
                 Choose booking state: 
             1.Available
             2.Out Of Service
             3.Reserved
   ==========================================================
             """;
        System.out.println(mn);
        switch (intScanner.nextInt()){
            case 1 ->{
                booking.setBookingState(BookingState.AVAILABLE);
            }
            case 2 ->{
              booking.setBookingState(BookingState.OUT_OF_SERVICE);
            }
            case 3 ->{
            booking.setBookingState(BookingState.RESERVED);
            }
            default -> {
                System.out.println("Input error😒...");
            }
        }

    }


    private static void bookingRoom() {
        User user  = new User();
        System.out.println("Enter Id: ");
        user.setId(strScanner.nextLine());

        User user1 = users.stream().filter(u -> u.getId().equals(user.getId())).findAny().orElse(null);


        if(user1==null){
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
            bookingMethod(user);
        }else{
            System.out.println("A user with this ID exists on the network😊...");
            bookingMethod(user1);

        }


    }


    private static void bookingMethod(User user) {

        System.out.println("Enter Room Id: ");
        String id = strScanner.nextLine();
        boolean bormi = rooms.stream().anyMatch(r -> r.getID().equals(id));

        if(bormi){
            boolean b1 = bookings.stream().anyMatch(b -> b.getRoom().getID().equals(id));
            if(b1){
                System.out.println("    This room is booked for these days:  ");
                System.out.println("===================================================================");
                for (int i = 0; i < bookings.size(); i++) {
                             if(Objects.equals(id,bookings.get(i).getRoom().getID())){
                                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                 System.out.println("Booking: => "+bookings.get(i).getDateOfReceipt().minusDays(1).format(formatter) + "  &&  "+ bookings.get(i).getDateOfSubmission().plusDays(1).format(formatter)+ " booking state: "+bookings.get(i).getBookingState());
                             }
                }
                System.out.println("===================================================================");

                System.out.println("Enter the day you want to book: ");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Please enter dates in dd-MM-yyyy format");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");

                String dateOfReceipt = strScanner.nextLine();
                LocalDate sana1 = LocalDate.parse(dateOfReceipt,DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                System.out.println("Enter how long you will stay at the hotel: ");
                String dateOfSubmission  = strScanner.nextLine();
                LocalDate sana2  = LocalDate.parse(dateOfSubmission,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                if (!testDay(sana1,sana2)) {
                    System.out.println("Enter the date using the header😡 ");
                    return;
                }
                if (testBoookingMetho(sana1,sana2,id)) {
                    System.out.println("Sorry this house is busy these days😔 ");
                }else{
                    Booking booking = new Booking();
                    booking.setUser(user);
                    booking.setRoom(rooms.stream().filter(r->r.getID().equals(id)).findAny().orElse(null));
                    booking.setDateOfReceipt(sana1);
                    booking.setDateOfSubmission(sana2);

                    int turganKunSoni =  (int) ChronoUnit.DAYS.between(booking.getDateOfReceipt().minusDays(1), booking.getDateOfSubmission().plusDays(1))-2;
                    if(user.getBalance()>=turganKunSoni*booking.getRoom().getPrice()){

                      booking.setBookingState(BookingState.RESERVED);
                      bookings.add(booking);

                      System.out.println("The room has been booked🫡...");
                      user.setBalance(user.getBalance()-turganKunSoni*booking.getRoom().getPrice());
                      userAdmin.setBalance(userAdmin.getBalance()+turganKunSoni*booking.getRoom().getPrice());
                      System.out.println("This amount has been debited from your account => "+turganKunSoni*booking.getRoom().getPrice());
                      History history = new History();
                      history.setBookingId(booking.getId());
                      history.setUserId(booking.getUser().getId());
                      histories.add(history);
                  }else{
                        ///puli kam
                        System.out.println("You don't have enough money to book a room😔...");
                    }


                }


            }else{
                /// hona hali bron qilinmagan
                System.out.println("Enter the day you want to book: ");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Please enter dates in dd-MM-yyyy format");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");

                String dateOfReceipt = strScanner.nextLine();
                LocalDate sana1 = LocalDate.parse(dateOfReceipt,DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                System.out.println("Enter how long you will stay at the hotel: ");
                String dateOfSubmission  = strScanner.nextLine();
                LocalDate sana2  = LocalDate.parse(dateOfSubmission,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                if (!testDay(sana1,sana2)) {
                    System.out.println("Enter the date using the header😡 ");
                    return;
                }
                Booking booking = new Booking();
                booking.setUser(user);
                booking.setRoom(rooms.stream().filter(r->r.getID().equals(id)).findAny().orElse(null));
                booking.setDateOfReceipt(sana1);
                booking.setDateOfSubmission(sana2);

                int turganKunSoni =  (int) ChronoUnit.DAYS.between(booking.getDateOfReceipt().minusDays(1), booking.getDateOfSubmission().plusDays(1))-2;
                if(user.getBalance()>=turganKunSoni*booking.getRoom().getPrice()){

                    booking.setBookingState(BookingState.RESERVED);
                    bookings.add(booking);


                    System.out.println("The room has been booked🫡...");
                    user.setBalance(user.getBalance()-(turganKunSoni*booking.getRoom().getPrice()));
                    userAdmin.setBalance(userAdmin.getBalance()+turganKunSoni*booking.getRoom().getPrice());
                    System.out.println("This amount has been debited from your account => "+(turganKunSoni*booking.getRoom().getPrice()));

                    History history = new History();
                    history.setBookingId(booking.getId());
                    history.setUserId(booking.getUser().getId());
                    histories.add(history);
                }else{
                    ///puli kam
                    System.out.println("You don't have enough money to book a room😔...");
                }


                ////////////////////////
            }


        }else{
            System.out.println("We do not have such a room😔...");
        }

    }

    private static boolean testBoookingMetho(LocalDate sana1, LocalDate sana2,String id) {

      return  bookings
                .stream()
                .filter(b-> b.getRoom().getID().equals(id))
                .filter(booking->booking.getBookingState()!=BookingState.AVAILABLE)
                .anyMatch(booking->!(sana2.isBefore(booking.getDateOfReceipt()) || sana1.isAfter(booking.getDateOfSubmission())));
  /*      for (int i = 0; i <bookings.size() ; i++) {
            if(Objects.equals(bookings.get(i).getRoom().getID(),id) ){
                Booking booking = bookings.get(i);
                if(!(sana2.isBefore(booking.getDateOfReceipt()) || sana1.isAfter(booking.getDateOfSubmission()))) {
                    if(booking.getBookingState()!=BookingState.AVAILABLE){
                        return true;
                    }
                }
            }
        }
        return false; */
    }


    private static void chnageRoomState() {
        System.out.println("Enter room Id: ");
        String id  = strScanner.nextLine();
        Room room = rooms.stream().filter(r -> r.getID().equals(id)).findAny().orElse(null);
      if(room!=null){
          roomType(room);
          System.out.println("The operation was successful🫡...");
      }else{
          System.out.println("We do not have such a room😔...");
      }

    }


    private static void addRoom() {

        Room room = new Room();
        System.out.println("Enter Room floor Number: ");
        room.setFloorNumber(intScanner.nextInt());
        System.out.println("Enter Room roomNumber: ");
        room.setRoomNumber(intScanner.nextInt());
        roomType(room);
            System.out.println("Enter Room price: ");
            room.setPrice(intScanner.nextDouble());
            boolean b = rooms
                    .stream()
                    .anyMatch(r -> r.getID().equals(room.getID()) || (r.getRoomNumber() == room.getRoomNumber() && r.getFloorNumber() == room.getFloorNumber()));

            if(!b){
                System.out.println("Room has been added to the Rooms line😊...");
                rooms.add(room);
            }else{
                System.out.println("We have this room😒...");
            }
        }



    private static void roomType(Room room) {
        String mn = """
   ==========================================================
                 Choose room type: 
             1.Standard
             2.Business
             3.Luxury
   ==========================================================
             """;
        System.out.println(mn);
        switch (intScanner.nextInt()){
            case 1 ->{
                 room.setRoomType(RoomType.STANDARD);
            }
            case 2 ->{
                room.setRoomType(RoomType.BUSINESS);
            }
            case 3 ->{
                room.setRoomType(RoomType.LUXURY);
            }
            default -> {
                System.out.println("Input error😒...");
            }
        }
    }

    private static boolean testDay(LocalDate data,LocalDate date2){
        LocalDate today = LocalDate.now();
        if(data.isAfter(today)){
            if(date2.isAfter(data)){
                return true;
            }

        }
        return false;
    }
}
