package org.example;

import org.example.model.Human;

public class Main {
    public static void main(String[] args) {
        Human myFriend = Human.builder()
                .firstName("Liza")
                .lastName("lastName")
                .build();

        System.out.println(myFriend);

        myFriend.setFirstName("Elizaveta");

        System.out.println(myFriend);
    }
}