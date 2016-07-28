package ru.xc0re.games.tetris;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import ru.xc0re.games.tetris.enums.FigureType;
import ru.xc0re.games.tetris.enums.SpaceType;
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
                    while (!currentFigure.moveDown()) {}
                    draw = true;
                    redraw = true;
                    repaint();
                }
            }
        });

        while (!gameEnded) {

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentFigure.moveDown()) {
                currentFigure = new Figure();
                if (Field.getInstance().checkLines()) {
                    redraw = true;
                    draw = true;
                    repaint();
                }
            }
            draw = true;
            repaint();

            for (int i = 0; i < Field.HEIGHT; i++) {
                for (int j = 0; j < Field.WIDTH; j++) {
                    System.out.print(Field.getInstance().get(j, i).getType());
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }

    }

    public void paint(Graphics g) {

        if (draw) {

            if (!initialized) {
                g.drawRect(2 * GAME_UNIT_SIZE - WIDTH_CORRECTION, 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                        10 * GAME_UNIT_SIZE, 18 * GAME_UNIT_SIZE);

                initialized = true;
                draw = false;
            }

            for (int i = 0; i < Field.HEIGHT; i++) {
                for (int j = 0; j < Field.WIDTH; j++) {
                    if (Field.getInstance().get(j, i).getType() == SpaceType.NotEmpty
                            || Field.getInstance().get(j, i).getType() == SpaceType.MovingPart) {
                        g.setColor(Field.getInstance().get(j, i).getBlock().getColor());
                        g.fillRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                    }
                    else if (Field.getInstance().get(j, i).getType() == SpaceType.MovingPartLastSeen) {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                    }
                    else if (redraw) {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                                i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                                GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(j * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE - WIDTH_CORRECTION,
                            i * GAME_UNIT_SIZE + 2 * GAME_UNIT_SIZE + HEIGHT_CORRECTION,
                            GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                }
            }

            draw = false;
            redraw = false;

        }
    }
}
