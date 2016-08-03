package ru.xc0re.games.tetris.model;

import static ru.xc0re.games.tetris.enums.Direction.Down;

public class Field {

    public static int WIDTH = 10;
    public static int HEIGHT = 18;
    public static int BUFFER_HEIGHT = 4;

    private Block[][] field;

    private static Field instance;

    private Field() {

        field = new Block[HEIGHT][WIDTH];

    }

    public static void destruct() {
        instance = null;
    }

    public static Field getInstance() {
        if (instance == null) {
            instance = new Field();
        }
        return instance;
    }

    public Block get(int x, int y) {
        return field[y][x];
    }

    public int checkLines() {

        int counter = 0;

        int broken = 0;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (field[i][j] != null)
                    counter++;
            }
            if (counter == WIDTH) {
                breakLine(i);
                collapse(i);
                broken++;
            }
            counter = 0;
        }

        return broken;
    }

    private void collapse(int y) {
        for (int i = y; i > 0; i--) {
            for (int j = 0; j < WIDTH; j++) {
                if (Field.getInstance().get(j, i - 1) != null) {
                    Field.getInstance().get(j, i - 1).move(Down);
                }
            }
            if (lineIsEmpty(i - 2))
                break;
        }
    }

    private boolean lineIsEmpty(int lineNumber) {
        int counter = 0;
        for (int i = 0; i < WIDTH; i++) {
            if (Field.getInstance().get(i, lineNumber) != null)
                counter++;
        }
        return counter == 0;
    }

    public void breakLine(int line) {
        for (int i = 0; i < WIDTH; i++) {
            Field.getInstance().set(i, line, null);
        }
    }


    public void set(int x, int y, Block block) {
        field[y][x] = block;
    }
}
