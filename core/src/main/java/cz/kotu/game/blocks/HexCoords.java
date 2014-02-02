package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

/**
 * @author tkotula
 */
class HexCoords {

    static final float H = (float) Math.sqrt(3) / 2;

    final Vector2 u;
    final Vector2 v;

    final Matrix3 hexCoords = new Matrix3();

    HexCoords() {
        this(new Vector2(1, 0), new Vector2(0.5f, H));
    }

    HexCoords(Vector2 u1, Vector2 v1) {
        u = u1;
        v = v1;
        hexCoords.val[Matrix3.M00] = u.x;
        hexCoords.val[Matrix3.M10] = u.y;
        hexCoords.val[Matrix3.M01] = v.x;
        hexCoords.val[Matrix3.M11] = v.y;
    }

    Vector2 toXY(float u, float v) {
        return new Vector2();// u*u;
    }

    void drawHex(float u, float v, ShapeRenderer shapeRenderer) {
        Vector2 origin = new Vector2(u, v);
        origin.mul(hexCoords);

        Vector2[] verts = getHexVerts();

        float[] polys = new float[verts.length * 2];

        for (int i = 0; i < verts.length; i++) {

//             hexCoords.;

            verts[i].mul(hexCoords);
            verts[i].add(origin);

            polys[2 * i] = verts[i].x;
            polys[2 * i + 1] = verts[i].y;
        }

        shapeRenderer.polyline(polys);
    }

    Vector2[] getHexVerts() {
        return new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(1, 1),
                new Vector2(0, 2),
                new Vector2(-1, 2),
                new Vector2(-1, 1),
        };
    }


    void drawHexGrid(ShapeRenderer shapeRenderer) {
        int w = 6;
        int h = 4;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                shapeRenderer.setColor(x % 2, y % 2, (-x - y) % 2, 1);
                drawHex(3 * x, 3 * y, shapeRenderer);
            }
        }
    }
}


class HexCoords2 extends HexCoords {

    HexCoords2() {
        super(new Vector2(1, 0), new Vector2(-0.5f, H));
    }

    @Override
    Vector2[] getHexVerts() {
        return new Vector2[]{
                new Vector2(1, 0),
                new Vector2(1, 1),
                new Vector2(0, 1),
                new Vector2(-1, 0),
                new Vector2(-1, -1),
                new Vector2(0, -1),
        };
    }
}

/**
 * http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
 */
class HexCoords3 extends HexCoords {

    final Vector2 x;
    final Vector2 y;
    final Vector2 z;

    HexCoords3() {
        super(new Vector2(H, -0.5f), new Vector2(0, 1f));
        x = u;
        y = v;
        z = new Vector2().sub(x).sub(y);
    }

    @Override
    Vector2[] getHexVerts() {

        return new Vector2[]{
                new Vector2(1, 0),  // -1
                new Vector2(1, 1),  // -2
                new Vector2(0, 1),  // -1
                new Vector2(-1, 0), // 1
                new Vector2(-1, -1),// 2
                new Vector2(0, -1), // 1
        };
    }

    void drawHexGrid(ShapeRenderer shapeRenderer) {
        int w = 6;
        int h = 4;

        for (float v = 0; v < h; v++) {
            for (float u0 = 0; u0 < w; u0++) {
                float u = u0 - (int) v / 2;
                shapeRenderer.setColor(u % 2, v % 2, (-u - v) % 2, 1);
                drawHex(2 * u + v, 2 * v + u, shapeRenderer);
            }
        }
    }

}