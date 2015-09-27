package com.tvoyagryvnia.util.password;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SimplePasswordGenerator implements PasswordGenerator {

    private String[] symbols = {"A", "a", "E", "e", "I", "i", "O", "o", "U", "u", "B", "b", "C", "c", "D", "d",
            "F", "f", "G", "g", "H", "h", "J", "j", "K", "k", "L", "l",
            "M", "m", "N", "n", "P", "p", "Q", "o", "R", "r", "S", "s", "T", "t", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};


    @Override
    public String generate() {
        Random r = new Random();
        StringBuilder passBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            passBuilder.append(symbols[r.nextInt(symbols.length)]);
        }
        return passBuilder.toString();
    }
}
