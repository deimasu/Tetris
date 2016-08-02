package ru.xc0re.games.tetris.model;

import org.jsfml.graphics.Color;
import ru.xc0re.games.tetris.enums.Direction;
import ru.xc0re.games.tetris.enums.SpaceType;

public class Block {

    private Figure figure;

    private int x;
    private int y;

    public Block(int x, int y, Figure figure) {

        this.figure = figure;

        this.x = x;
        this.y = y;

        Field.getInstance().get(x, y).setBlock(this);
        Field.getInstance().get(x, y).setType(SpaceType.MovingPart);

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
        Field.getInstance().get(x, y).setType(SpaceType.Empty);
    }

    public void stop() {
        Field.getInstance().get(x, y).setType(SpaceType.NotEmpty);
    }

    public void move(Direction direction) {
        if (direction == Direction.Down) {

            if (y < Field.HEIGHT - 1)
                y++;

            Field.getInstance().get(x, y).setBlock(this);
            Field.getInstance().get(x, y).setType(SpaceType.MovingPart);
        }
        else if (direction == Direction.Right) {

            if (x < Field.WIDTH - 1)
                x++;

            Field.getInstance().get(x, y).setBlock(this);
            Field.getInstance().get(x, y).setType(SpaceType.MovingPart);

        }
        else if (direction == Direction.Left) {

            if (x > 0)
                x--;

            Field.getInstance().get(x, y).setBlock(this);
            Field.getInstance().get(x, y).setType(SpaceType.MovingPart);

        }
    }

    public Color getColor() {
        return figure.getColor();
    }

    public void toMoving() {
        Field.getInstance().get(x, y).setType(SpaceType.MovingPart);
    }

    public void setBlock() {
        Field.getInstance().get(x, y).setBlock(this);
    }

    public boolean isMoving() {
        return Field.getInstance().get(x, y).getType() == SpaceType.MovingPart;
    }
}
