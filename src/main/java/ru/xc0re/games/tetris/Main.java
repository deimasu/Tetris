package ru.xc0re.games.tetris;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
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

    public static void main(String[] args) throws IOException, TextureCreationException, InterruptedException {

        RenderWindow window = new RenderWindow(new VideoMode(WIDTH, HEIGHT), "Tetris", WindowStyle.CLOSE);
        window.setFramerateLimit(60);

        Font font = new Font();
        font.loadFromStream(Main.class.getClassLoader().getResourceAsStream("arial.ttf"));

        Text scoreText = new Text();
        Text nextText = new Text();
        Text scoreNumber = new Text();
        Text pauseText = new Text();

        scoreText.setString("LINES:");
        scoreText.setFont(font);
        scoreText.setColor(Color.BLACK);
        scoreText.setCharacterSize(25);
        scoreText.setPosition(13 * GAME_UNIT_SIZE, 9 * GAME_UNIT_SIZE);

        scoreNumber.setFont(font);
        scoreNumber.setColor(Color.BLACK);
        scoreNumber.setCharacterSize(25);
        scoreNumber.setPosition(13.5f * GAME_UNIT_SIZE, 11 * GAME_UNIT_SIZE);

        nextText.setString("NEXT:");
        nextText.setFont(font);
        nextText.setColor(Color.BLACK);
        nextText.setCharacterSize(25);
        nextText.setPosition(13 * GAME_UNIT_SIZE, 2 * GAME_UNIT_SIZE);

        pauseText.setString("PAUSED");
        pauseText.setFont(font);
        pauseText.setColor(Color.BLACK);
        pauseText.setCharacterSize(40);
        pauseText.setPosition(WIDTH / 2 - 4 * GAME_UNIT_SIZE, HEIGHT / 2 - 3 * GAME_UNIT_SIZE);


        RectangleShape border = new RectangleShape();
        border.setSize(new Vector2f(GAME_UNIT_SIZE * Field.WIDTH, GAME_UNIT_SIZE * Field.HEIGHT));
        border.setOutlineColor(Color.BLACK);
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
                if (event.type == KEY_PRESSED) {
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
            }

            if (!paused) {

                if (timer == 20) {
                    if (current.moveDown()) {
                        current = next;
                        if (!current.init())
                            gameEnded = true;
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

            window.clear(Color.WHITE);

            window.draw(border);
            window.draw(nextText);
            drawNextFigure(window, next, sprite);
            window.draw(scoreText);
            window.draw(scoreNumber);

            for (int i = 0; i < Field.HEIGHT; i++) {
                for (int j = 0; j < Field.WIDTH; j++) {
                    if (Field.getInstance().get(j, i).getType() == MovingPart || Field.getInstance().get(j, i).getType() == NotEmpty) {
                        sprite.setColor(Field.getInstance().get(j, i).getBlock().getColor());
                        sprite.setPosition(GAME_UNIT_SIZE * 2 + j * GAME_UNIT_SIZE, GAME_UNIT_SIZE * 2 + i * GAME_UNIT_SIZE);
                        window.draw(sprite);
                    }
                }
            }

            if (paused)
                window.draw(pauseText);

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
