package ru.xc0re.games.tetris;

import org.jsfml.graphics.*;

import java.io.IOException;

public class MyText extends Text {

    private Font font;

    public MyText(int size, String text, float x, float y, Color color) {
        font = new Font();
        try {
            font.loadFromStream(Main.class.getClassLoader().getResourceAsStream("standart.ttf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setFont(font);

        setString(text);
        setColor(color);
        setCharacterSize(size);
        setPosition(x, y);
    }

}
