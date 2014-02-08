package cz.kotu.game.blocks.hex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kotuc
 */
public class HexGrid<T> {

    public final Map<Axial, T> map = new HashMap<Axial, T>();

    public HexGrid() {

    }

    public T get(int q, int r) {
        return map.get(new Axial(q, r));
    }

    public T get(Axial pos) {
        return map.get(pos);
    }

    public T set(int q, int r, T val) {
        return set(new Axial(q, r), val);
    }

    public T set(Axial pos, T val) {
        return map.put(pos, val);
    }

}
