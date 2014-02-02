package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class BaseStage {

    final SpriteBatch batch = new SpriteBatch();

    final BitmapFont font = new BitmapFont();

    void init() {

    }

    void update() {

    }

    void draw(Matrix4 combined) {

    }

    public void pointerDown(float x, float y) {

    }

    public boolean touchDown(float x, float y, int pointer, int button) {
        return true;
    }

    public boolean touchDragged(float x, float y, int pointer) {
        return false;
    }

    public boolean touchUp(float x, float y, int pointer, int button) {
        return true;
    }


    public boolean keyTyped(char character) {
        return false;
    }
}
