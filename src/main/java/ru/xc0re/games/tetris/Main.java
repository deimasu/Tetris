package ru.xc0re.games.tetris;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import ru.xc0re.games.tetris.enums.Direction;
import ru.xc0re.games.tetris.enums.FigureType;
import ru.xc0re.games.tetris.model.Field;
import ru.xc0re.games.tetris.model.Figure;

import java.io.IOException;

import static org.jsfml.window.Keyboard.Key.*;
import static org.jsfml.window.event.Event.Type.CLOSED;
import static org.jsfml.window.event.Event.Type.KEY_PRESSED;
import static org.jsfml.window.event.Event.Type.MOUSE_BUTTON_PRESSED;
import static ru.xc0re.games.tetris.enums.SpaceType.MovingPart;
import static ru.xc0re.games.tetris.enums.SpaceType.NotEmpty;

public class Main {

    public static final int GAME_UNIT_SIZE = 20;
    public static final int WIDTH = GAME_UNIT_SIZE * 20;
    public static final int HEIGHT = GAME_UNIT_SIZE * 22;

    static boolean gameEnded = false;
    static Figure next;
    static Figure current;
    static int score;
    static boolean paused = false;
    private static boolean restart;

    public static void main(String[] args) throws IOException, TextureCreationException, InterruptedException {

        RenderWindow window = new RenderWindow(new VideoMode(WIDTH, HEIGHT), "Tetris", WindowStyle.CLOSE);
        window.setFramerateLimit(60);

        Image icon = new Image();
        icon.loadFromStream(Main.class.getClassLoader().getResourceAsStream("icon.png"));
        window.setIcon(icon);


        MyText scoreText = new MyText(20, "LINES:", 13 * GAME_UNIT_SIZE, 9 * GAME_UNIT_SIZE, Color.WHITE);
        MyText nextText = new MyText(20, "NEXT:", 13 * GAME_UNIT_SIZE, 2 * GAME_UNIT_SIZE, Color.WHITE);
        MyText scoreNumber = new MyText(20, "", 13.5f * GAME_UNIT_SIZE, 11 * GAME_UNIT_SIZE, Color.WHITE);
        MyText pauseText = new MyText(35, "PAUSED", WIDTH / 2 - 5 * GAME_UNIT_SIZE, HEIGHT / 2 - 3 * GAME_UNIT_SIZE, Color.BLACK);
        MyText restartText = new MyText(20, "RESTART", 13 * GAME_UNIT_SIZE, 15 * GAME_UNIT_SIZE, Color.WHITE);
        MyText restartQuestionText = new MyText(20, "Do you want to restart?", 2 * GAME_UNIT_SIZE, 10 * GAME_UNIT_SIZE, Color.WHITE);
        MyText finalScoreText = new MyText(20, "Game over. Your score: ", 2 * GAME_UNIT_SIZE, 8 * GAME_UNIT_SIZE, Color.WHITE);
        MyText yesText = new MyText(20, "YES", 4 * GAME_UNIT_SIZE, 13 * GAME_UNIT_SIZE, Color.WHITE);
        MyText noText = new MyText(20, "NO", 14 * GAME_UNIT_SIZE, 13 * GAME_UNIT_SIZE, Color.WHITE);

        RectangleShape yesButton = new RectangleShape();
        yesButton.setSize(new Vector2f(4 * GAME_UNIT_SIZE, 2 * GAME_UNIT_SIZE));
        yesButton.setFillColor(Color.BLACK);
        yesButton.setOutlineColor(Color.WHITE);
        yesButton.setOutlineThickness(2);
        yesButton.setPosition(new Vector2f(3.5f * GAME_UNIT_SIZE, 12.5f * GAME_UNIT_SIZE));

        RectangleShape noButton = new RectangleShape();
        noButton.setSize(new Vector2f(3 * GAME_UNIT_SIZE, 2 * GAME_UNIT_SIZE));
        noButton.setFillColor(Color.BLACK);
        noButton.setOutlineColor(Color.WHITE);
        noButton.setOutlineThickness(2);
        noButton.setPosition(new Vector2f(13.5f * GAME_UNIT_SIZE, 12.5f * GAME_UNIT_SIZE));

        RectangleShape restartButton = new RectangleShape();
        restartButton.setSize(new Vector2f(7 * GAME_UNIT_SIZE, 2 * GAME_UNIT_SIZE));
        restartButton.setFillColor(Color.BLACK);
        restartButton.setOutlineColor(Color.WHITE);
        restartButton.setOutlineThickness(2);
        restartButton.setPosition(new Vector2f(12.5f * GAME_UNIT_SIZE, 14.5f * GAME_UNIT_SIZE));

        RectangleShape pauseRect = new RectangleShape();
        pauseRect.setSize(new Vector2f(220, 70));
        pauseRect.setFillColor(Color.WHITE);
        pauseRect.setPosition(WIDTH / 2 - 6 * GAME_UNIT_SIZE, HEIGHT / 2 - 4 * GAME_UNIT_SIZE);


        RectangleShape border = new RectangleShape();
        border.setSize(new Vector2f(GAME_UNIT_SIZE * Field.WIDTH, GAME_UNIT_SIZE * Field.HEIGHT));
        border.setFillColor(Color.BLACK);
        border.setOutlineColor(Color.WHITE);
        border.setOutlineThickness(3);
        border.setPosition(GAME_UNIT_SIZE * 2, GAME_UNIT_SIZE * 2);

        Image image = new Image();
        image.loadFromStream(Main.class.getClassLoader().getResourceAsStream("tile.jpg"));

        Texture texture = new Texture();
        texture.loadFromImage(image);

        Sprite sprite = new Sprite();
        sprite.setTexture(texture);

        current = new Figure();
        current.init();
        next = new Figure();

        long timer = 0;

        while (window.isOpen()) {


            for (Event event : window.pollEvents()) {
                if (event.type == CLOSED) {
                    window.close();
                }
                if (event.type == KEY_PRESSED && !restart && !gameEnded) {
                    if (event.asKeyEvent().key == UP && !paused) {
                        try {
                            current.rotate();
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                    if (event.asKeyEvent().key == DOWN && !paused) {
                        current.moveDown();
                    }
                    if (event.asKeyEvent().key == LEFT && !paused) {
                        current.moveLeft();
                    }
                    if (event.asKeyEvent().key == RIGHT && !paused) {
                        current.moveRight();
                    }
                    if (event.asKeyEvent().key == SPACE && !paused) {
                        while (!current.moveDown());
                    }
                    if (event.asKeyEvent().key == PAUSE) {
                        paused = !paused;
                    }
                }
                if (event.type == MOUSE_BUTTON_PRESSED && !paused) {
                    if (event.asMouseButtonEvent().button == Mouse.Button.LEFT) {
                        if (event.asMouseButtonEvent().position.x > restartButton.getPosition().x
                                && event.asMouseButtonEvent().position.x < restartButton.getPosition().x + restartButton.getSize().x
                                && event.asMouseButtonEvent().position.y > restartButton.getPosition().y
                                && event.asMouseButtonEvent().position.y < restartButton.getPosition().y + restartButton.getSize().y
                                && !restart)
                            restart = true;
                        if (event.asMouseButtonEvent().position.x > yesButton.getPosition().x
                                && event.asMouseButtonEvent().position.x < yesButton.getPosition().x + yesButton.getSize().x
                                && event.asMouseButtonEvent().position.y > yesButton.getPosition().y
                                && event.asMouseButtonEvent().position.y < yesButton.getPosition().y + yesButton.getSize().y) {
                            if (gameEnded && restart) {

                                Field.destruct();
                                score = 0;
                                timer = 0;
                                current = new Figure();
                                current.init();
                                next = new Figure();
                                restart = false;
                                gameEnded = false;
                                finalScoreText.setString("Game over. Your score: ");

                            }
                            else if (restart) {
                                Field.destruct();
                                score = 0;
                                timer = 0;
                                current = new Figure();
                                current.init();
                                next = new Figure();
                                restart = false;
                            }
                        }
                        if (event.asMouseButtonEvent().position.x > noButton.getPosition().x
                                && event.asMouseButtonEvent().position.x < noButton.getPosition().x + noButton.getSize().x
                                && event.asMouseButtonEvent().position.y > noButton.getPosition().y
                                && event.asMouseButtonEvent().position.y < noButton.getPosition().y + noButton.getSize().y) {
                            if (gameEnded && restart) {
                                window.close();
                            }
                            else if (restart) {
                                restart = false;
                            }
                        }
                    }
                }
            }

            if (!restart) {

                if (!paused) {

                    if (timer == 20) {
                        if (current.moveDown()) {
                            current = next;
                            if (!current.init()) {
                                gameEnded = true;
                                restart = true;
                                finalScoreText.setString(finalScoreText.getString() + Integer.toString(score));
                            }
                            next = new Figure();
                            int broken = Field.getInstance().checkLines();
                            if (broken > 0) {
                                score += broken;
                            }
                        }
                        timer = 0;
                    }

                    scoreNumber.setString(Integer.toString(score));

                    timer += 1;


                }

                window.clear(Color.BLACK);

                window.draw(border);
                window.draw(nextText);
                drawNextFigure(window, next, sprite);
                window.draw(scoreText);
                window.draw(scoreNumber);
                window.draw(restartButton);
                window.draw(restartText);

                for (int i = 0; i < Field.HEIGHT; i++) {
                    for (int j = 0; j < Field.WIDTH; j++) {
                        if (Field.getInstance().get(j, i).getType() == MovingPart || Field.getInstance().get(j, i).getType() == NotEmpty) {
                            sprite.setColor(Field.getInstance().get(j, i).getBlock().getColor());
                            sprite.setPosition(GAME_UNIT_SIZE * 2 + j * GAME_UNIT_SIZE, GAME_UNIT_SIZE * 2 + i * GAME_UNIT_SIZE);
                            window.draw(sprite);
                        }
                    }
                }

                if (paused) {
                    window.draw(pauseRect);
                    window.draw(pauseText);
                }
            }else {
                window.clear(Color.BLACK);
                if (gameEnded) {
                    window.draw(finalScoreText);
                }

                window.draw(restartQuestionText);

                window.draw(yesButton);
                window.draw(noButton);
                window.draw(yesText);
                window.draw(noText);

            }

            window.display();
        }

    }

    static void drawNextFigure(RenderWindow window, Figure figure, Sprite sprite) {

        sprite.setColor(figure.getColor());

        if (figure.getType() == FigureType.I) {
            if (figure.getDirection() == Direction.Down || figure.getDirection() == Direction.Up) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 7 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else {

                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(16 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);

            }
        } else if (figure.getType() == FigureType.J) {
            if (figure.getDirection() == Direction.Up) {

                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Right) {

                sprite.setPosition(13 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Down) {

                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Left) {

                sprite.setPosition(13 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);

            }
        } else if (figure.getType() == FigureType.L) {
            if (figure.getDirection() == Direction.Up) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Right) {

                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(13 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Down) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Left) {

                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);

            }
        } else if (figure.getType() == FigureType.T) {
            if (figure.getDirection() == Direction.Up) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Right) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);


            } else if (figure.getDirection() == Direction.Down) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else if (figure.getDirection() == Direction.Left) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);

            }
        } else if (figure.getType() == FigureType.O) {

            sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
            window.draw(sprite);
            sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
            window.draw(sprite);
            sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
            window.draw(sprite);
            sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
            window.draw(sprite);

        } else if (figure.getType() == FigureType.S) {
            if (figure.getDirection() == Direction.Up || figure.getDirection() == Direction.Down) {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(13 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);

            }
        } else if (figure.getType() == FigureType.Z) {
            if (figure.getDirection() == Direction.Up || figure.getDirection() == Direction.Down) {

                sprite.setPosition(15 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 6 * GAME_UNIT_SIZE);
                window.draw(sprite);

            } else {

                sprite.setPosition(14 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(15 * GAME_UNIT_SIZE, 5 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(13 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);
                sprite.setPosition(14 * GAME_UNIT_SIZE, 4 * GAME_UNIT_SIZE);
                window.draw(sprite);

            }
        }
    }
}
