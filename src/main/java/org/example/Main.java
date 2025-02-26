package org.example;

import org.example.service.AuthService;
import org.example.db.DataSource;


public class Main {
    public static void main(String[] args) {

        while (true) {
            DataSource.refreshScanner();
            try {
                new AuthService().service();
            } catch (Exception e) {}
        }
    }
}