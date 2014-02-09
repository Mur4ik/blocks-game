package cz.kotu.game.blocks.hex;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.HashSet;

/**
 * @author tkotula
 */
public class HexSet extends HashSet<Hex> {

    Color color = new Color(hashCode());

    void drawHexGrid(HexCoords3 hexCoords3, ShapeRenderer shapeRenderer) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Hex hex : this) {

            shapeRenderer.setColor(color);

            hexCoords3.drawHex(hex, shapeRenderer);

        }

        shapeRenderer.end();

    }

    void move(Vector3 dirCube) {

        for (Hex hex : this) {
            hex.center.add(dirCube);
        }

    }

    Hex getHex(Vector3 cube) {
        for (Hex hex : this) {
            if (hex.contains(cube)) {
                return hex;
            }
        }
        return null;
    }

    boolean intersects(HexCoords3 coords3, Vector3 cube) {
        for (Hex hex : this) {
            if (hex.contains(cube)) return true;
        }
        return false;
    }

    boolean intersects(HexSet other) {
        for (Hex hext : this) {
            for (Hex hexo : other) {
                if (hext.intersects(hexo)) {
                    return true;
                }
            }
        }
        return false;
    }

    HexSet intersection(HexSet other) {
        // TODO this is complicated with float coordinates
        HexSet intersection = new HexSet();
        intersection.addAll(this);
        intersection.retainAll(other);
        return intersection;
    }

    Hex addHex(Axial axial) {
        return addHex(axial.q, axial.r);
    }

    Hex addHex(int q, int r) {
        Hex hex = new Hex();
        hex.size = 0.9f;
        hex.setCenterAxial(q, r);
        add(hex);
        return hex;
    }
}
