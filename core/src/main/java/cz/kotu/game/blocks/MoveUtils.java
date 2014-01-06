package cz.kotu.game.blocks;

public class MoveUtils {

    /**
     * Goes with maximum acceleration and velocity to reach dist = 0
     * Assuming vel and dist are on one line.
     *
     * @param v      current velocity - will be modified
     * @param d      target distance (and direction)
     * @param maxV   maximum velocity
     * @param maxAcc maximum acceleration
     */
    static float goAccFastestDec(float v, float d, float maxV, float maxAcc) {

        if (d < 0) {
            return -goAccFastestDec(-v, -d, maxV, maxAcc);
        }

        // can stop immediately after this step
        if (Math.abs(v - d) <= maxAcc) {
//            return d;
        }

        // maximum velocity for d that can be decelerated to stop properly without overshot
        final float limitV = (float) Math.sqrt(2 * d * maxAcc);

        if (v >= limitV) {
            // need to decelerate - almost there and will overshot
            v -= maxAcc;
            // do not decelerate more than necessary
//            v = Math.max(limitV, v - maxAcc);
//            v = Math.min(v, limitV);
        } else {
            // accelerate, but keep in mind limits
            v += maxAcc;
            v = Math.min(v, maxV);
            v = Math.min(v, limitV);
        }

//        v = Math.min(v, d);

        return v;

    }
}
