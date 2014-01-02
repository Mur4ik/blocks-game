package cz.kotu.game.blocks;

import com.badlogic.gdx.utils.Predicate;
import cz.kotu.grids.Dir;
import cz.kotu.grids.Pos;

public class GridUtils {

    public static int getNeighHash(Pos pos, Predicate<Pos> predicate) {
        int hash = ((predicate.evaluate(pos.inDir(Dir.E))) ? 1 : 0)
                + ((predicate.evaluate(pos.inDir(Dir.N))) ? 2 : 0)
                + ((predicate.evaluate(pos.inDir(Dir.W))) ? 4 : 0)
                + ((predicate.evaluate(pos.inDir(Dir.S))) ? 8 : 0);
        return hash;
    }

}
