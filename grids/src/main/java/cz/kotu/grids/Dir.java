package cz.kotu.grids;

/**
 * @author Kotuc
 */
public class Dir {
    public static final Dir
            E = new Dir(1, 0),
            NE = new Dir(1, 1),
            N = new Dir(0, 1),
            NW = new Dir(-1, 1),
            W = new Dir(-1, 0),
            SW = new Dir(-1, -1),
            S = new Dir(0, -1),
            SE = new Dir(1, -1);

    private final int dx, dy;

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    private Dir(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    Dir[] values4() {
        return new Dir[]{E, N, W, S};
    }

    Dir[] values8() {
        return new Dir[]{E, NE, N, NW, W, SW, S, SE};
    }

}