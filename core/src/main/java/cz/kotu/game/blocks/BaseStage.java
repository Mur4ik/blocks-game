package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import cz.kotu.grids.GenericGrid;
import cz.kotu.grids.LinPos;
import cz.kotu.grids.LinearGrid;
import cz.kotu.grids.Pos;

import java.util.HashMap;
import java.util.Map;

public class BaseStage {

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


}
