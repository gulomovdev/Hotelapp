package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private String Id = UUID.randomUUID().toString();
    private String userId;
    private String bookingId;
}
