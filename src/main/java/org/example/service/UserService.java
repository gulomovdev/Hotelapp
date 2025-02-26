package org.example.service;
import org.example.entity.Booking;
import org.example.entity.History;
import org.example.entity.enums.BookingState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.example.db.DataSource.*;



public class UserService {

    public static void service(){
              String menu = """
                       User Menu: 
                   0.Back🔙
                   1.Show rooms
                   2.Booking room
                   3.Cancel booking
                   4.Booking history
                   5.Balance
                   """;
              while(true){
                  System.out.println(menu);
                  switch (intScanner.nextInt()){
                      case 0->{
                          System.out.println("See you in peace😊");
                          return;
                      }
                      case 1->{
                        showRoomsU();
                      }
                      case 2->{
                      bookingRoomU();
                      }
                      case 3->{
                        cancelBooking();
                      }
                      case 4->{
                        bookingHistory();
                      }
                      case 5->{
                         balance();
                      }
                      default -> {
                          System.out.println("Input error😒...");
                      }
                  }
              }
    }

    private static void balance() {
        String menu = """
                 Balance Menu:
             0.Back🔙
             1.Show Balance
             2.Topping up the balance
             """;
        System.out.println(menu);
        switch (intScanner.nextInt()){
            case 0->{
                return;
            }
            case 1->{
                System.out.println("  User Balance:  => "+currentUser.getBalance());

            }
            case 2->{
                System.out.println("Enter summa:  ");
                Double sum = intScanner.nextDouble();
                currentUser.setBalance(currentUser.getBalance()+sum);
                System.out.println("The money has been added to the balance😊...");
                return;
            }

            default -> {
                System.out.println("Input eror😒...");
            }

        }
    }

    private static void bookingHistory() {
        System.out.println("            User Historys: ");
        System.out.println("===========================================================================");
        histories
                .stream().filter(h->h.getUserId().equals(currentUser.getId()))
                .forEach(System.out::println) ;
        System.out.println("===========================================================================");
    }

    private static void cancelBooking() {

        System.out.println("Enter booking Id: ");
        String id  = strScanner.nextLine();
        boolean b1 = bookings.stream().anyMatch(b -> b.getId().equals(id));

        if(b1){
            Booking booking = bookings.stream().filter(b -> b.getId().equals(id)).findAny().orElse(null);
            int turganKunSoni =  (int) ChronoUnit.DAYS.between(booking.getDateOfReceipt(), booking.getDateOfSubmission());
            currentUser.setBalance(currentUser.getBalance() + (0.8*booking.getRoom().getPrice()*turganKunSoni));
            userAdmin.setBalance(userAdmin.getBalance()-(0.8*booking.getRoom().getPrice()*turganKunSoni));
            bookings.remove(booking);
            System.out.println("Booking has been cancelled");
            System.out.println("This amount will be deducted from you => "+(0.2*booking.getRoom().getPrice()*turganKunSoni));

        }else{
            System.out.println("Such booking is not available😔...");
        }



    }




    private static void bookingRoomU() {
        System.out.println("Enter room Id: ");
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
                        System.out.println("Booking: => "+bookings.get(i).getDateOfReceipt().format(formatter) + "  &&  "+ bookings.get(i).getDateOfSubmission().format(formatter)+ " booking state: "+bookings.get(i).getBookingState());
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
                if (testBoookingMetho(sana1,sana2,id)) {
                    System.out.println("Sorry this house is busy these days😔 ");
                }else{
                    Booking booking = new Booking();
                    booking.setUser(currentUser);
                    booking.setRoom(rooms.stream().filter(r->r.getID().equals(id)).findAny().orElse(null));
                    booking.setDateOfReceipt(sana1);
                    booking.setDateOfSubmission(sana2);

                    int turganKunSoni =  (int) ChronoUnit.DAYS.between(booking.getDateOfReceipt().minusDays(1), booking.getDateOfSubmission().plusDays(1))-2;
                    if(currentUser.getBalance()>=turganKunSoni*booking.getRoom().getPrice()){

                        booking.setBookingState(BookingState.RESERVED);
                        bookings.add(booking);

                        System.out.println("The room has been booked🫡...");
                        currentUser.setBalance(currentUser.getBalance()-turganKunSoni*booking.getRoom().getPrice());
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
                System.out.println("Enter the day you want to book: ");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Please enter dates in dd-MM-yyyy format");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");

                String dateOfReceipt = strScanner.nextLine();
                LocalDate sana1 = LocalDate.parse(dateOfReceipt,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                System.out.println("Enter how long you will stay at the hotel: ");
                String dateOfSubmission  = strScanner.nextLine();
                LocalDate sana2  = LocalDate.parse(dateOfSubmission,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                Booking booking = new Booking();
                booking.setUser(currentUser);
                booking.setRoom(rooms.stream().filter(r->r.getID().equals(id)).findAny().orElse(null));
                booking.setDateOfReceipt(sana1);
                booking.setDateOfSubmission(sana2);

                int turganKunSoni =  (int) ChronoUnit.DAYS.between(booking.getDateOfReceipt().minusDays(1), booking.getDateOfSubmission().plusDays(1))-2;
                if(currentUser.getBalance()>=turganKunSoni*booking.getRoom().getPrice()){

                    booking.setBookingState(BookingState.RESERVED);
                    bookings.add(booking);

                    System.out.println("The room has been booked🫡...");
                    currentUser.setBalance(currentUser.getBalance()-(turganKunSoni*booking.getRoom().getPrice()));
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
            }

        else{
            System.out.println("We do not have such a room😔...");
        }
    }

    private static boolean testBoookingMetho(LocalDate sana1, LocalDate sana2,String id) {
        for (int i = 0; i <bookings.size() ; i++) {
            if(Objects.equals(bookings.get(i).getRoom().getID(),id) ){
                Booking booking = bookings.get(i);
                if(!(sana2.isBefore(booking.getDateOfReceipt()) || sana1.isAfter(booking.getDateOfSubmission()))) {
                    if(booking.getBookingState()!=BookingState.AVAILABLE){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static void showRoomsU() {

        System.out.println("     Rooms🌃:  ");
        System.out.println("===========================================================================");
        rooms
                .stream()
                .forEach(System.out::println) ;
        System.out.println("===========================================================================");
    }
}
