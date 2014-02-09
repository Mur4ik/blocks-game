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
        Vector3 dir = new Vector3(this.center).sub(cube);
        dir = HexCoords3.abs(dir);
//        return Math.max(Math.max(dir.x, dir.y), dir.z);
        return (dir.x + dir.y + dir.z) / 2f;
    }

    boolean intersects(Hex other) {
        return this.centerDistance(other) <= this.size + other.size;
    }

    void draw(SpriteBatch batch) {
//        batch.draw(getTextureRegion(), pos.q, pos.r, 1, 1);
    }

    public void update() {
        // bla bla
    }

    public boolean contains(Vector3 cube) {
        return centerDistance(cube) <= size;
    }

}
