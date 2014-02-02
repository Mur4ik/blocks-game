package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

/**
* @author tkotula
*/
class HexCoords {

    final Vector2 u;
    final Vector2 v;

    final Matrix3 hexCoords = new Matrix3();

    HexCoords() {
        u = new Vector2(1, 0);
        v = new Vector2(0.5f, 0.8f);
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

        Vector2[] verts = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(1, 1),
                new Vector2(0, 2),
                new Vector2(-1, 2),
                new Vector2(-1, 1),
        };

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

}
