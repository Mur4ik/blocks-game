package cz.kotu.game.blocks.hex;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Hex {

    final Vector3 center = new Vector3();

    float size = 1;

    public void setCenterAxial(int q, int r) {
        HexCoords3.setCubeFromAxial(center, new Axial(q, r));
    }

    float centerDistance(Hex other) {
        return centerDistance(other.center);
    }

    float centerDistance(Vector3 cube) {
        return HexCoords3.hexDistance(this.center, cube);
    }

    boolean intersects(Hex other) {
        // from how the size is currently calculated - need to scale
        return this.centerDistance(other) <= (this.size + other.size);
    }

    void draw(SpriteBatch batch) {
//        batch.draw(getTextureRegion(), pos.q, pos.r, 1, 1);
    }

    public void update() {
        // bla bla
    }

    public boolean contains(Vector3 cube) {
        // from how the size is currently calculated - need to scale
        return centerDistance(cube) <= size / 2f;
    }

}
