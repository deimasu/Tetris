package ru.xc0re.games.tetris.model;

import org.jsfml.graphics.Color;
import ru.xc0re.games.tetris.enums.Direction;
import ru.xc0re.games.tetris.enums.FigureType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static ru.xc0re.games.tetris.enums.Direction.*;
import static ru.xc0re.games.tetris.enums.FigureType.*;

public class Figure {

    private FigureType type;

    private ArrayList<Block> figureParts = new ArrayList<Block>();

    private Color color;

    private Direction direction;

    private boolean moving;

    public Figure() {

        Random random = new Random();

        int typeNumber = random.nextInt(7);
        int directionNumber = random.nextInt(4);

        switch (typeNumber) {
            case 0: {

                type = I;
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
                direction = Up;
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

        if (type == I) {
            if (direction == Direction.Down || direction == Up) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 2) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 3) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 3, this));

                direction = Up;

            } else {

                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 + 1, 0) != null)
                    return false;


                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 + 1, 0, this));

                direction = Direction.Right;


            }
        } else if (type == FigureType.J) {
            if (direction == Up) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 2) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 2) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;


                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));


            } else if (direction == Direction.Right) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));


            } else if (direction == Direction.Down) {

                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 2) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));


            } else if (direction == Direction.Left) {

                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 0) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));


            }
        } else if (type == FigureType.L) {
            if (direction == Up) {

                if (Field.getInstance().get(Field.WIDTH / 2, 2) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 2) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));


            } else if (direction == Direction.Right) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));


            } else if (direction == Direction.Down) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 2) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));


            } else if (direction == Direction.Left) {

                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 1) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));


            }
        } else if (type == FigureType.T) {
            if (direction == Up) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));


            } else if (direction == Direction.Right) {

                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 2) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));


            } else if (direction == Direction.Down) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 0) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 0, this));


            } else if (direction == Direction.Left) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 2) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));


            }
        } else if (type == FigureType.O) {

            if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                return false;
            if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                return false;
            if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                return false;
            if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                return false;

            figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
            figureParts.add(new Block(Field.WIDTH / 2, 0, this));
            figureParts.add(new Block(Field.WIDTH / 2, 1, this));
            figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));

            direction = Up;


        } else if (type == FigureType.S) {
            if (direction == Up || direction == Direction.Down) {

                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 2) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2, 2, this));

                direction = Up;


            } else {

                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 1) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 2, 1, this));

                direction = Direction.Right;


            }
        } else if (type == FigureType.Z) {
            if (direction == Up || direction == Direction.Down) {

                if (Field.getInstance().get(Field.WIDTH / 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 2) != null)
                    return false;

                figureParts.add(new Block(Field.WIDTH / 2, 0, this));
                figureParts.add(new Block(Field.WIDTH / 2, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 1, this));
                figureParts.add(new Block(Field.WIDTH / 2 - 1, 2, this));

                direction = Up;


            } else {

                if (Field.getInstance().get(Field.WIDTH / 2 - 2, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 0) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2 - 1, 1) != null)
                    return false;
                if (Field.getInstance().get(Field.WIDTH / 2, 1) != null)
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

        ArrayList<Block> copyOfFigure = new ArrayList<Block>(figureParts);

        copyOfFigure.sort(new Comparator<Block>() {
            public int compare(Block o1, Block o2) {
                return o1.getY() > o2.getY() ? -1 : o1.getY() == o2.getY() ? 0 : 1;
            }
        });

        // checking ability to move

        for (Block b : copyOfFigure) {
            if (b.getY() == Field.HEIGHT - 1 || Field.getInstance().get(b.getX(), b.getY() + 1) != null
                    && !Field.getInstance().get(b.getX(), b.getY() + 1).getFigure().equals(Field.getInstance().get(b.getX(), b.getY()).getFigure())) {
                for (Block b1 : figureParts)
                    b1.stop();
                return true;
            }
        }

        for (Block b : copyOfFigure) {
            b.clear();
        }

        // moving

        for (Block b : copyOfFigure) {
            b.move(Direction.Down);
        }

        return false;
    }

    public boolean moveRight() {

        ArrayList<Block> copyOfFigure = new ArrayList<Block>(figureParts);

        copyOfFigure.sort(new Comparator<Block>() {
            public int compare(Block o1, Block o2) {
                return o1.getX() > o2.getX() ? -1 : o1.getX() == o2.getX() ? 0 : 1;
            }
        });

        // checking ability to move

        for (Block b : copyOfFigure) {
            if (b.getX() == Field.WIDTH - 1)
                return false;
            if (Field.getInstance().get(b.getX() + 1, b.getY()) != null
                    && !Field.getInstance().get(b.getX() + 1, b.getY()).getFigure().equals(Field.getInstance().get(b.getX(), b.getY()).getFigure()))
                return false;
        }

        for (Block b : copyOfFigure) {
            b.clear();
        }

        for (Block b : copyOfFigure) {
            b.move(Direction.Right);
        }

        return true;
    }

    public boolean moveLeft() {

        ArrayList<Block> copyOfFigure = new ArrayList<Block>(figureParts);

        copyOfFigure.sort(new Comparator<Block>() {
            public int compare(Block o1, Block o2) {
                return o1.getX() > o2.getX() ? 1 : o1.getX() == o2.getX() ? 0 : -1;
            }
        });

        // checking ability to move

        for (Block b : copyOfFigure) {
            if (b.getX() == 0)
                return false;
            if (Field.getInstance().get(b.getX() - 1, b.getY()) != null
                    && !Field.getInstance().get(b.getX() - 1, b.getY()).getFigure().equals(Field.getInstance().get(b.getX(), b.getY()).getFigure()))
                return false;
        }

        for (Block b : copyOfFigure) {
            b.clear();
        }

        for (Block b : copyOfFigure) {
            b.move(Direction.Left);
        }

        return true;
    }

    public void rotate() {
        if (type == I) {
            if (direction == Up) {
                if (figureParts.get(1).getX() > 0
                        && figureParts.get(1).getX() + 2 < Field.WIDTH
                        && Field.getInstance().get(figureParts.get(1).getX() - 1, figureParts.get(1).getY()) == null
                        && Field.getInstance().get(figureParts.get(1).getX() + 1, figureParts.get(1).getY()) == null
                        && Field.getInstance().get(figureParts.get(1).getX() + 2, figureParts.get(1).getY()) == null) {

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
                        b.setBlock();
                    }

                    direction = Right;

                }
            } else if (direction == Right) {
                if (figureParts.get(1).getY() > 0
                        && figureParts.get(1).getY() + 2 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(1).getX(), figureParts.get(1).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(1).getX(), figureParts.get(1).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(1).getX(), figureParts.get(1).getY() + 2) == null) {

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
                        b.setBlock();
                    }

                    direction = Up;
                }
            }
        } else if (type == J) {
            if (direction == Up) {
                if (figureParts.get(2).getY() > 0
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && figureParts.get(2).getX() > 0
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()) == null
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Right;
                }
            } else if (direction == Right) {
                if (figureParts.get(2).getY() > 0
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Down;
                }
            } else if (direction == Down) {
                if (figureParts.get(2).getX() > 0
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()) == null
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Left;
                }
            } else if (direction == Left) {
                if (figureParts.get(2).getY() > 0
                        && figureParts.get(2).getX() > 0
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() - 1);

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Up;
                }
            }
        } else if (type == L) {
            if (direction == Up) {
                if (figureParts.get(2).getX() > 0
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()) == null
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Right;
                }
            } else if (direction == Right) {
                if (figureParts.get(2).getX() > 0
                        && figureParts.get(2).getY() > 0
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Down;
                }
            } else if (direction == Down) {
                if (figureParts.get(2).getX() > 0
                        && figureParts.get(2).getY() > 0
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()) == null
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Left;
                }
            } else if (direction == Left) {
                if (figureParts.get(2).getX() > 0
                        && figureParts.get(2).getY() > 0
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() + 1);

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() - 1);

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Up;
                }
            }
        } else if (type == T) {
            if (direction == Up) {
                if (figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1) == null) {

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
                        b.setBlock();
                    }

                    direction = Right;
                }
            } else if (direction == Right) {
                if (figureParts.get(2).getX() > 0
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY()) == null) {

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
                        b.setBlock();
                    }

                    direction = Down;
                }
            } else if (direction == Down) {
                if (figureParts.get(2).getY() > 0
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1) == null) {

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
                        b.setBlock();
                    }

                    direction = Left;
                }
            } else if (direction == Left) {
                if (figureParts.get(2).getX() + 1 < Field.WIDTH
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY()) == null) {

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
                        b.setBlock();
                    }

                    direction = Up;
                }
            }
        } else if (type == S) {
            if (direction == Up) {
                if (figureParts.get(2).getY() > 0
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Right;
                }
            } else if (direction == Right) {
                if (figureParts.get(2).getY() > 0
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Up;
                }
            }
        } else if (type == Z) {
            if (direction == Up) {
                if (figureParts.get(2).getX() > 0
                        && figureParts.get(2).getY() > 0
                        && Field.getInstance().get(figureParts.get(2).getX() - 1, figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() - 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() - 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX());
                    figureParts.get(1).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(3).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(3).setY(figureParts.get(2).getY());

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Right;
                }
            } else if (direction == Right) {
                if (figureParts.get(2).getY() > 0
                        && figureParts.get(2).getX() + 1 < Field.WIDTH
                        && figureParts.get(2).getY() + 1 < Field.HEIGHT
                        && Field.getInstance().get(figureParts.get(2).getX() + 1, figureParts.get(2).getY() - 1) == null
                        && Field.getInstance().get(figureParts.get(2).getX(), figureParts.get(2).getY() + 1) == null) {

                    for (Block b : figureParts) {
                        b.clear();
                    }

                    figureParts.get(0).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(0).setY(figureParts.get(2).getY() - 1);

                    figureParts.get(1).setX(figureParts.get(2).getX() + 1);
                    figureParts.get(1).setY(figureParts.get(2).getY());

                    figureParts.get(3).setX(figureParts.get(2).getX());
                    figureParts.get(3).setY(figureParts.get(2).getY() + 1);

                    for (Block b : figureParts) {
                        b.setBlock();
                    }

                    direction = Up;
                }
            }
        }
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

}
