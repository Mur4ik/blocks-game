package cz.kotu.game.blocks.hex;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author tkotula
 */
public class HexGroup<T> extends HexGrid<T> {

    void drawHexGrid(HexCoords3 hexCoords3, ShapeRenderer shapeRenderer) {

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Map.Entry<Axial, T> entry : map.entrySet()) {

            Axial axial = entry.getKey();

            Integer v = (Integer) entry.getValue();
            int val = 0;
            if (v != null) {
                val = v;
            }

            switch (val) {
                case 1:
                    shapeRenderer.setColor(0, 1, 0, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
            }

//                shapeRenderer.setColor(col % 2, rd % 2, (-col - rd) % 2, 1);
//                drawHex(2 * col + rd, 2 * rd + col, shapeRenderer);
//                drawHex(new Vector3(2 * col + rd, 2 * rd + col, 0), shapeRenderer);
//                drawHex(new Vector3(-2 * col + rd, -2 * rd + col, 0), shapeRenderer);
            hexCoords3.drawHex(hexCoords3.toCube(axial), 1f - val * 0.05f, shapeRenderer);
            Vector3 project = hexCoords3.project(axial);

//                drawHex(new Vector3(2 * col, 2 * rd, -2 * col - 2 * rd), shapeRenderer);
        }

        shapeRenderer.end();

    }

    void move(Axial dir) {
        Set<Map.Entry<Axial, T>> entries = map.entrySet();

        final Map<Axial, T> temp = new HashMap<Axial, T>();
        for (Map.Entry<Axial, T> entry : entries) {
            Axial key = entry.getKey();
            key.add(dir);
            temp.put(key, entry.getValue());
        }
        map.clear();

        map.putAll(temp);
    }

    boolean intersects(HexCoords3 coords3, Vector3 cube) {
        Axial pick = coords3.roundToAxial(cube);
        return map.keySet().contains(pick);
    }

}
