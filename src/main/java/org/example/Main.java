package org.example;

import org.example.model.Human;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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