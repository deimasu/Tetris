package ru.xc0re.games.tetris;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import ru.xc0re.games.tetris.enums.Direction;
import ru.xc0re.games.tetris.enums.FigureType;
import ru.xc0re.games.tetris.enums.SpaceType;
import ru.xc0re.games.tetris.model.Block;
import ru.xc0re.games.tetris.model.Field;
import ru.xc0re.games.tetris.model.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;

public class Game extends JFrame implements Runnable {

    private boolean initialized;
    private boolean draw = true;
    private boolean redraw = false;
    private boolean gameEnded;
    private boolean scored = false;
    private boolean figureStopped = true;

    private int score = 0;

    private Figure currentFigure;
    private Figure nextFigure;

    public static final int GAME_UNIT_SIZE = 20;
    public static final int WIDTH = GAME_UNIT_SIZE * 20;
    public static final int HEIGHT = GAME_UNIT_SIZE * 22;

    public static final int WIDTH_CORRECTION = 10;
    public static final int HEIGHT_CORRECTION = 10;

    public Game() {

        super("Tetris");

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);


    }


    public void run() {


        currentFigure = new Figure();
        nextFigure = new Figure();

        currentFigure.init();

        addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                myKeyEvt(e);
            }

            public void keyReleased(KeyEvent e) {}

            public void keyPressed(KeyEvent e) {
                myKeyEvt(e);
            }

            private void myKeyEvt(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT)
                {
                    currentFigure.moveLeft();
                    draw = true;
                    repaint();


                }
                else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT)
                {
                    currentFigure.moveRight();
                    draw = true;
                    repaint();

                }
                else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP)
                {
                    currentFigure.rotate();
                    draw = true;
                    repaint();

                }
                else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN)
                {
                    currentFigure.moveDown();
                    draw = true;
                    repaint();
                }
            }
        });

        while (!gameEnded) {

            try {
                Thread.sleep(450);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentFigure.moveDown()) {
                currentFigure = nextFigure;
                currentFigure.init();
                nextFigure = new Figure();
                figureStopped = true;
                int broken = Field.getInstance().checkLines();
                if (broken > 0) {
                    redraw = true;
                    draw = true;
                    scored = true;
                    repaint();
                    score += broken;
                }
            }
            draw = true;
            repaint();

        }

    }

    public void paint(Graphics g) {

        if (draw) {

            if (!initialized) {
                g.drawRect(2 * GAME_UNIT_SIZE - WIDTH_CORRECTION - 1, 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION - 1,
                        10 * GAME_UNIT_SIZE + 2, 18 * GAME_UNIT_SIZE + 2);

                g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 22));
                g.drawString("NEXT:", 13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 4 * GAME_UNIT_SIZE + HEIGHT_CORRECTION);
                g.drawString("LINES:", 13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 11 * GAME_UNIT_SIZE + HEIGHT_CORRECTION);

                initialized = true;
                draw = false;
            }

            for (int i = 0; i < Field.HEIGHT; i++) {
                for (int j = 0; j < Field.WIDTH; j++) {
                    if (Field.getInstance().get(j, i).getType() == SpaceType.MovingPart) {
                        g.setColor(Field.getInstance().get(j, i).getBlock().getColor());
                        g.fillRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                        if (Field.getInstance().get(j, i).getType() == SpaceType.MovingPart) {
                            g.setColor(Color.BLACK);
                            g.drawRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                    i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                    GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                        }
                    }
                    else if (Field.getInstance().get(j, i).getType() == SpaceType.MovingPartLastSeen) {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                GAME_UNIT_SIZE + 1, GAME_UNIT_SIZE + 1);
                    }
                    else if (redraw) {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                        if (Field.getInstance().get(j, i).getType() == SpaceType.NotEmpty) {
                            g.setColor(Field.getInstance().get(j, i).getBlock().getColor());
                            g.fillRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                    i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                    GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                            g.setColor(Color.BLACK);
                            g.drawRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                    i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                    GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                        }
                    }
//                    g.setColor(Color.BLACK);
//                    g.drawRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
//                            i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
//                            GAME_UNIT_SIZE, GAME_UNIT_SIZE);



                }
            }

            if (scored) {
                g.setColor(Color.WHITE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 12 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE * 3, GAME_UNIT_SIZE);
            }

            if (figureStopped) {
                g.setColor(Color.WHITE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE * 4 + 1, GAME_UNIT_SIZE * 4 + 1);
                g.setColor(Color.BLACK);
                g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 22));
                g.drawString(Integer.toString(score), 13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 13 * GAME_UNIT_SIZE + HEIGHT_CORRECTION);
            }

            drawFigure(g, nextFigure);

            scored = false;
            draw = false;
            redraw = false;
            figureStopped = false;

        }
    }

    public void drawFigure(Graphics g, Figure figure) {

        if (figure.getType() == FigureType.I) {
            if (figure.getDirection() == Direction.Up ||  figure.getDirection() == Direction.Down) {

                g.setColor(Color.CYAN);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 8 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 8 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else {

                g.setColor(Color.CYAN);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(16 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(16 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
        }
        else if (figure.getType() == FigureType.J) {
            if (figure.getDirection() == Direction.Up) {

                g.setColor(Color.BLUE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Right) {

                g.setColor(Color.BLUE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Down) {

                g.setColor(Color.BLUE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Left) {

                g.setColor(Color.BLUE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
        }
        else if (figure.getType() == FigureType.L) {
            if (figure.getDirection() == Direction.Up) {

                g.setColor(Color.ORANGE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Right) {

                g.setColor(Color.ORANGE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Down) {

                g.setColor(Color.ORANGE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Left) {

                g.setColor(Color.ORANGE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
        }
        else if (figure.getType() == FigureType.T) {
            if (figure.getDirection() == Direction.Up) {

                g.setColor(Color.MAGENTA);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Right) {

                g.setColor(Color.MAGENTA);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Down) {

                g.setColor(Color.MAGENTA);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else if (figure.getDirection() == Direction.Left) {

                g.setColor(Color.MAGENTA);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
        }
        else if (figure.getType() == FigureType.O) {

            g.setColor(Color.YELLOW);
            g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                    GAME_UNIT_SIZE * 2, GAME_UNIT_SIZE * 2);

            g.setColor(Color.BLACK);
            g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                    GAME_UNIT_SIZE, GAME_UNIT_SIZE);
            g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                    GAME_UNIT_SIZE, GAME_UNIT_SIZE);
            g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                    GAME_UNIT_SIZE, GAME_UNIT_SIZE);
            g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                    GAME_UNIT_SIZE, GAME_UNIT_SIZE);

        }
        else if (figure.getType() == FigureType.S) {
            if (figure.getDirection() == Direction.Up || figure.getDirection() == Direction.Down) {

                g.setColor(Color.GREEN);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else {

                g.setColor(Color.GREEN);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
        }
        else if (figure.getType() == FigureType.Z) {
            if (figure.getDirection() == Direction.Up || figure.getDirection() == Direction.Down) {

                g.setColor(Color.RED);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 7 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
            else {

                g.setColor(Color.RED);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.fillRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(15 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 6 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(13 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.drawRect(14 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 5 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        GAME_UNIT_SIZE, GAME_UNIT_SIZE);

            }
        }

    }
}
