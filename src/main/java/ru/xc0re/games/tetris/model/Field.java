package ru.xc0re.games.tetris.model;

import ru.xc0re.games.tetris.enums.SpaceType;

public class Field {

    public static int WIDTH = 10;
    public static int HEIGHT = 18;
    public static int BUFFER_HEIGHT = 4;

    private Space[][] field;

    private static Field instance;

    private Field() {

        field = new Space[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                field[i][j] = new Space();
                field[i][j].setType(SpaceType.Empty);
            }
        }

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

    public Space get(int x, int y) {
        return field[y][x];
    }

    public int checkLines() {

        int counter = 0;

        int broken = 0;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (Field.getInstance().get(j, i).getType() == SpaceType.NotEmpty)
                    counter++;
            }
            if (counter == WIDTH) {
                breakLine(i);
                collapse(i);
                broken++;
            }
            counter = 0;
        }

        if (broken < 0)
            emptyAll();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (Field.getInstance().get(j, i).getType() == SpaceType.Empty) {
                    Field.getInstance().get(j, i).setBlock(null);
                }
            }
        }

        return broken;
    }

    private void emptyAll() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (Field.getInstance().get(j, i).getType() == SpaceType.BrokenBlock) {
                    Field.getInstance().get(j, i).setType(SpaceType.Empty);
                }
            }
        }
    }

    private void collapse(int y) {
        for (int i = y; i > 0; i--) {
            for (int j = 0; j < WIDTH; j++) {
                if (Field.getInstance().get(j, i - 1).getType() == SpaceType.NotEmpty) {
                    Field.getInstance().get(j, i).setType(SpaceType.NotEmpty);
                    Field.getInstance().get(j, i - 1).setType(SpaceType.Empty);
                    Field.getInstance().get(j, i).setBlock(Field.getInstance().get(j, i - 1).getBlock());
                }
            }
            if (lineIsEmpty(i - 2))
                break;
        }
    }

    private boolean lineIsEmpty(int lineNumber) {
        int counter = 0;
        for (int i = 0; i < WIDTH; i++) {
            if (Field.getInstance().get(i, lineNumber).getType() == SpaceType.NotEmpty)
                counter++;
        }
        return counter == 0;
    }

    public void breakLine(int line) {
        for (int i = 0; i < WIDTH; i++) {
            Field.getInstance().get(i, line).setType(SpaceType.BrokenBlock);
        }
    }





}
