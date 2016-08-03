package ru.xc0re.games.tetris.model;

import org.jsfml.graphics.Color;
import ru.xc0re.games.tetris.enums.Direction;

public class Block {

    private Figure figure;

    private int x;
    private int y;

    public Block(int x, int y, Figure figure) {

        this.figure = figure;

        this.x = x;
        this.y = y;

        Field.getInstance().set(x, y, this);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void clear() {
        Field.getInstance().set(x, y, null);
    }

    public void stop() {
        figure.setMoving(false);
    }

    public void move(Direction direction) {

        Field.getInstance().set(x, y, null);

        if (direction == Direction.Down) {

            if (y < Field.HEIGHT - 1)
                y++;

            setBlock();

        }
        else if (direction == Direction.Right) {

            if (x < Field.WIDTH - 1)
                x++;

            setBlock();

        }
        else if (direction == Direction.Left) {

            if (x > 0)
                x--;

            setBlock();

        }
    }

    public Color getColor() {
        return figure.getColor();
    }

    public void setBlock() {
        Field.getInstance().set(x, y, this);
    }

    public Figure getFigure() {
        return figure;
    }
}
