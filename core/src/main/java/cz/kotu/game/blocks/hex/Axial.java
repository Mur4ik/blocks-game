package cz.kotu.game.blocks.hex;

/**
 * @author Kotuc
 */
public class Axial {
    public int q; // == x
    public int r; // == y
    // x + y + z = 0

    public Axial() {
    }

    public Axial(int q, int r) {
        setAxial(q, r);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Axial other = (Axial) obj;
        if (this.q != other.q) {
            return false;
        }
        if (this.r != other.r) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.q;
        hash = 59 * hash + this.r;
        return hash;
    }


    @Override
    public String toString() {
        return "Axial(" + q + "," + r + ")";
    }

    public Axial setAxial(Axial axial) {
        return setAxial(axial.q, axial.r);
    }

    public Axial setAxial(int q, int r) {
        this.q = q;
        this.r = r;
        return this;
    }

    public Axial setCube(int x, int y, int z) {
        this.q = x;
        this.r = z;
        return this;
    }

    public Axial add(Axial dir) {
        this.q += dir.q;
        this.r += dir.r;
        return this;
    }

    int x() {
        return q;
    }

    int y() {
        return r;
    }

    int z() {
        return -q - r;
    }

    public int distance(Axial other) {
        return (Math.abs(this.x() - other.x()) + Math.abs(this.y() - other.y()) + Math.abs(this.z() - other.z())) / 2;
    }

}
