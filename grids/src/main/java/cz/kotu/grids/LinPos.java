package cz.kotu.grids;

/**
 * @author Kotuc
 *         Immutable
 */
public final class LinPos extends Pos {

    public final int i;

    public LinPos(int x, int y, int i) {
        super(x, y);
        this.i = i;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LinPos other = (LinPos) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.i != other.i) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        hash = 37 * hash + this.i;
        return hash;
    }

}
