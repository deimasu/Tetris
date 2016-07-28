package ru.xc0re.games.tetris;

public class Main {

    public static void main(String[] args) {

        Thread game = new Thread(new Game());

        game.start();

    }

}
