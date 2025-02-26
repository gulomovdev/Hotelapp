package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.BookingState;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
   private String Id = UUID.randomUUID().toString();
   private Room room;
   private User user;
   private LocalDate dateOfReceipt;
   private LocalDate dateOfSubmission;
   private BookingState bookingState;

}
