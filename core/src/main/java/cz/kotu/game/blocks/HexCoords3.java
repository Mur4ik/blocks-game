package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
 */
public class HexCoords3 {

    static final float H = (float) Math.sqrt(3) / 2;

    final Matrix4 hexCoords = new Matrix4();

    final Vector3 x;
    final Vector3 y;
    final Vector3 z;

    HexCoords3() {
        x = new Vector3(H, -0.5f, 0);
        y = new Vector3(0, 1f, 0);
        z = new Vector3().sub(x).sub(y);
//        z = new Vector3(-H, -0.5f, 0);

        hexCoords.val[Matrix4.M00] = x.x;
        hexCoords.val[Matrix4.M10] = x.y;
        hexCoords.val[Matrix4.M01] = y.x;
        hexCoords.val[Matrix4.M11] = y.y;
        hexCoords.val[Matrix4.M02] = z.x;
        hexCoords.val[Matrix4.M12] = z.y;

//        hexCoords.val[Matrix4.M00] = x.x;
//        hexCoords.val[Matrix4.M01] = x.y;
//        hexCoords.val[Matrix4.M10] = y.x;
//        hexCoords.val[Matrix4.M11] = y.y;
//        hexCoords.val[Matrix4.M20] = z.x;
//        hexCoords.val[Matrix4.M21] = z.y;

    }

    Vector3[] getHexVerts() {

//        return new Vector3[]{
//                new Vector3(1, 0,  -1),
//                new Vector3(1, 1,  -2),
//                new Vector3(0, 1,  -1),
//                new Vector3(-1, 0,  1),
//                new Vector3(-1, -1, 2),
//                new Vector3(0, -1,  1),
//        };

//        return new Vector3[]{
//                new Vector3(1, 0,  0),// -1
//                new Vector3(1, 1,  0),// -2
//                new Vector3(0, 1,  0),// -1
//                new Vector3(-1, 0, 0),//  1
//                new Vector3(-1, -1,0),//  2
//                new Vector3(0, -1, 0),//  1
//        };

        return new Vector3[]{
                new Vector3(1, 0, 0),
                new Vector3(1, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 1),
                new Vector3(0, 0, 1),
                new Vector3(1, 0, 1),
        };

    }

    void drawHex(Vector3 origin, ShapeRenderer shapeRenderer) {

        Vector3 o = new Vector3(origin);

        proj(o);

        Vector3[] verts = getHexVerts();

        float[] polys = new float[verts.length * 2];

        for (int i = 0; i < verts.length; i++) {

//             hexCoords.;

            proj(verts[i]);
            verts[i].add(o);

            polys[2 * i] = verts[i].x;
            polys[2 * i + 1] = verts[i].y;
        }

        shapeRenderer.polyline(polys);
    }

    void drawHexGrid(ShapeRenderer shapeRenderer) {
        int w = 6;
        int h = 4;

        for (float rd = 0; rd < h; rd++) {
            for (float coli = 0; coli < w; coli++) {
//                int c0 = 0;
                int c0 = 0 - (int) rd / 2;
                float col = c0 + coli;

                shapeRenderer.setColor(col % 2, rd % 2, (-col - rd) % 2, 1);
//                drawHex(2 * col + rd, 2 * rd + col, shapeRenderer);
//                drawHex(new Vector3(2 * col + rd, 2 * rd + col, 0), shapeRenderer);
//                drawHex(new Vector3(-2 * col + rd, -2 * rd + col, 0), shapeRenderer);
                drawHex(new Vector3(col, rd, -col - rd), shapeRenderer);
//                drawHex(new Vector3(2 * col, 2 * rd, -2 * col - 2 * rd), shapeRenderer);
            }
        }
    }

    private void proj(Vector3 uv) {
        uv.mul(hexCoords);
    }

    float z(Vector2 vec) {
        return z(vec.x, vec.y);
    }

    float z(float x, float y) {
        return -x - y;
    }

    /**
     * @param u num of column to right
     * @param v num of right upwards diagonal to right
     * @return vector in imaginary coordinates x, y, z
     */
    Vector2 toXyz(int u, int v) {
        return new Vector2(2 * u + v, 2 * v + u);
    }

    float distance(Vector2 v1, Vector2 v2) {
        Vector3 dir = new Vector3(v2.x - v1.x, v2.y - v1.y, z(v2) - z(v1));
        return Math.max(Math.max(dir.x, dir.y), dir.z);
    }

}
