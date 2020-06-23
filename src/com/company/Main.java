package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        JFrame jFrame  = new JFrame();
        jFrame.setBounds(10,10,700,600);
        jFrame.setTitle("Brick Breaker");
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.add(game);
    }
}
