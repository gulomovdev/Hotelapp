package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.RoomType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private String ID = UUID.randomUUID().toString();
    private Integer floorNumber;
    private Integer roomNumber;
    private RoomType roomType;
    private Double price;
}
