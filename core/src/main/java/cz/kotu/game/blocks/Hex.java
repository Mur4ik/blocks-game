package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * Coordinates:
 * x + y + z = 0
 *
 * distance = max (x2-x1, y2-y1, z2-z1)
 *
 * http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
 *
 * @author tkotula
 */
public class Hex {

    int x, y, z;


    void draw(SpriteBatch batch) {
//        batch.draw(getTextureRegion(), pos.x, pos.y, 1, 1);
    }

    public void update() {
        // bla bla
    }
}
