package ru.xc0re.games.tetris.model;

import org.jsfml.graphics.Color;
import ru.xc0re.games.tetris.enums.Direction;
import ru.xc0re.games.tetris.enums.FigureType;

import java.util.ArrayList;
import java.util.Random;

public class Figure {

    private FigureType type;

    private ArrayList<Block> figureParts = new ArrayList<Block>();

    private Color color;

    private Direction direction;

    public Figure() {

        Random random = new Random();

        int typeNumber = random.nextInt(7);
        int directionNumber = random.nextInt(4);

        switch (typeNumber) {
            case 0: {

                type = FigureType.I;
                color = Color.CYAN;

                break;

            }
            case 1: {

                type = FigureType.J;
                color = Color.BLUE;

                break;

            }
            case 2: {

                type = FigureType.L;
                color = new Color(255, 127, 0);

                break;

            }
            case 3: {

                type = FigureType.O;
                color = Color.YELLOW;

                break;

            }
            case 4: {

                type = FigureType.S;
                color = Color.GREEN;

                break;

            }
            case 5: {

                type = FigureType.T;
                color = Color.MAGENTA;

                break;

            }
            case 6: {

                type = FigureType.Z;
                color = Color.RED;

                break;

            }
        }

        switch (directionNumber) {
            case 0:
                direction = Direction.Up;
                break;
            case 1:
                direction = Direction.Right;
                break;
            case 2:
                direction = Direction.Down;
                break;
            case 3:
                direction = Direction.Left;
                break;
        }

//        init();

        for (Block b : figureParts) {
            b.toMoving();
            b.setBlock();
        }

    }

    public Color getColor() {
        return color;
    }

    public FigureType getType() {
        return type;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean init() {

        if (type == FigureType.I) {
            if (direction == Direction.Down || direction == Direction.Up) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 2).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 3).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 3, this));

                direction = Direction.Up;

            } else {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 + 1, 0).isEmpty())
                    return false;


                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 + 1, 0, this));

                direction = Direction.Right;


            }
        } else if (type == FigureType.J) {
            if (direction == Direction.Up) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 2).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 2).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;


                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));



            } else if (direction == Direction.Right) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));



            } else if (direction == Direction.Down) {

                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 2).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));


            } else if (direction == Direction.Left) {

                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 0).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));



            }
        } else if (type == FigureType.L) {
            if (direction == Direction.Up) {

                if (!Field.getInstance().get(Field.WIDTH / 2, 2).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 2).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));



            } else if (direction == Direction.Right) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));



            } else if (direction == Direction.Down) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 2).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));




            } else if (direction == Direction.Left) {

                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 1).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));



            }
        } else if (type == FigureType.T) {
            if (direction == Direction.Up) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));



            } else if (direction == Direction.Right) {

                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 2).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));



            } else if (direction == Direction.Down) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 0).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));



            } else if (direction == Direction.Left) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 2).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));



            }
        } else if (type == FigureType.O) {

            if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                return false;
            if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                return false;
            if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                return false;
            if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                return false;

            figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
            figureParts.add(new Block(Field.WIDTH / 2, 0, this));
            figureParts.add(new Block(Field.WIDTH / 2, 1, this));
            figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));

            direction = Direction.Up;



        } else if (type == FigureType.S) {
            if (direction == Direction.Up || direction == Direction.Down) {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 2).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));

                direction = Direction.Up;



            } else {

                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 1).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));

                direction = Direction.Right;



            }
        } else if (type == FigureType.Z) {
            if (direction == Direction.Up || direction == Direction.Down) {

                if (!Field.getInstance().get(Field.WIDTH / 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 2).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));

                direction = Direction.Up;



            } else {

                if (!Field.getInstance().get(Field.WIDTH / 2 - 2, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 0).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2 - 1, 1).isEmpty())
                    return false;
                if (!Field.getInstance().get(Field.WIDTH / 2, 1).isEmpty())
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));

                direction = Direction.Right;


            }
        }
        return true;
    }

    public boolean moveDown() {

        // checking ability to move

        for (Block b : figureParts) {
            if (b.getY() == Field.HEIGHT - 1 || !Field.getInstance().get(b.getX(), b.getY() + 1).isEmpty()) {
                for (Block b1 : figureParts)
                    b1.stop();
                return true;
            }
        }

        for (Block b : figureParts) {
            b.clear();
        }

        // moving

        for (Block b : figureParts) {
            b.move(Direction.Down);
        }

        return false;
    }

    public boolean moveRight() {

        // checking ability to move

        for (Block b : figureParts) {
            if (b.getX() == Field.WIDTH - 1)
                return false;
            if (!Field.getInstance().get(b.getX() + 1, b.getY()).isEmpty())
                return false;
        }

        for (Block b : figureParts) {
            b.clear();
        }

        for (Block b : figureParts) {
            b.move(Direction.Right);
        }

        return true;
    }

    public boolean moveLeft() {

        // checking ability to move

        for (Block b : figureParts) {
            if (b.getX() == 0)
                return false;
            if (!Field.getInstance().get(b.getX() - 1, b.getY()).isEmpty())
                return false;
        }

        for (Block b : figureParts) {
            b.clear();
        }

        for (Block b : figureParts) {
            b.move(Direction.Left);
        }

        return true;
    }

    public boolean rotate() {

        if (type == FigureType.O)
            return true;

        if (type == FigureType.L) {
            if (direction == Direction.Up) {
                if (Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(0).getX() - 2);

                    figureParts.get(1).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Right;

                    return true;

                }
            } else if (direction == Direction.Right) {
                if (Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(this.figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(this.figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setY(figureParts.get(0).getY() - 2);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Down;

                    return true;

                }
            } else if (direction == Direction.Down) {
                if (Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(0).getX() + 2);

                    figureParts.get(1).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Left;

                    return true;

                }
            } else {
                if (Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setY(figureParts.get(0).getY() + 2);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() - 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Up;

                    return true;

                }
            }
        } else if (type == FigureType.J) {
            if (direction == Direction.Up) {
                if (Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setY(figureParts.get(0).getY() - 2);

                    figureParts.get(1).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Right;

                    return true;

                }
            } else if (direction == Direction.Right) {
                if (Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(this.figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(this.figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(0).getX() + 2);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Down;

                    return true;

                }
            } else if (direction == Direction.Down) {
                if (Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setY(figureParts.get(0).getY() + 2);

                    figureParts.get(1).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Left;

                    return true;

                }
            } else {
                if (Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(0).getX() - 2);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() - 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Up;

                    return true;

                }
            }
        } else if (type == FigureType.T) {
            if (direction == Direction.Up) {
                if (Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY());

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Right;

                    return true;

                }
            } else if (direction == Direction.Right) {
                if (Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX());
                    figureParts.get(0).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Down;

                    return true;

                }
            } else if (direction == Direction.Down) {
                if (Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY());

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() - 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Left;

                    return true;

                }
            } else {
                if (Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX());
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Up;

                    return true;

                }
            }
        } else if (type == FigureType.I) {
            if (direction == Direction.Up) {
                if (Field.getInstance().get(figureParts.get(1).getX() - 1, figureParts.get(1).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(1).getX() + 1, figureParts.get(1).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(1).getX() + 2, figureParts.get(1).getY()).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(1).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(1).getY());

                    figureParts.get(2).setX(figureParts.get(1).getX() + 1);
                    figureParts.get(2).setY(figureParts.get(1).getY());

                    figureParts.get(3).setX(figureParts.get(1).getX() + 2);
                    figureParts.get(3).setY(figureParts.get(1).getY());

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Right;

                    return true;

                }
            } else if (direction == Direction.Right) {
                if (Field.getInstance().get(figureParts.get(1).getX(), figureParts.get(1).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(1).getX(), figureParts.get(1).getY() + 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(1).getX(), figureParts.get(1).getY() + 2).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(1).getX());
                    figureParts.get(0).setY(figureParts.get(1).getY() - 1);

                    figureParts.get(2).setX(figureParts.get(1).getX());
                    figureParts.get(2).setY(figureParts.get(1).getY() + 1);

                    figureParts.get(3).setX(figureParts.get(1).getX());
                    figureParts.get(3).setY(figureParts.get(1).getY() + 2);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Up;

                    return true;

                }
            }
        } else if (type == FigureType.S) {
            if (direction == Direction.Up) {
                if (Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(3).setX(figureParts.get(1).getX());
                    figureParts.get(3).setY(figureParts.get(1).getY());

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Right;

                    return true;

                }
            } else if (direction == Direction.Right) {
                if (Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(1).setX(figureParts.get(3).getX());
                    figureParts.get(1).setY(figureParts.get(3).getY());

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Up;

                    return true;

                }
            }
        } else if (type == FigureType.Z) {
            if (direction == Direction.Up) {
                if (Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(3).setX(figureParts.get(1).getX());
                    figureParts.get(3).setY(figureParts.get(1).getY());

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Right;

                    return true;

                }
            } else if (direction == Direction.Right) {
                if (Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()).isEmpty()
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1).isEmpty()) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(1).setX(figureParts.get(3).getX());
                    figureParts.get(1).setY(figureParts.get(3).getY());

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.toMoving();
                        b.setBlock();
                    }

                    direction = Direction.Up;

                    return true;

                }
            }
        }

        return false;
    }

}
