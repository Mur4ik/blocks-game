package cz.kotu.game.blocks;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MoveUtilsTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testGoAccFastestDec() throws Exception {
        final float acc = 0.5f;

        // shall stop - acceleration allows that
        Assert.assertEquals(0f, MoveUtils.goAccFastestDec(5, 0, 100, 10));

        // shall start to break
        Assert.assertEquals(0.5f, MoveUtils.goAccFastestDec(1, 0, 1, acc));

        // shall decelerate
//        Assert.assertEquals(-acc, MoveUtils.goAccFastestDec(1, 0.1f, 1, acc));
//        Assert.assertEquals(1f-acc, MoveUtils.goAccFastestDec(1, 0.1f, 1, acc));

//        Assert.assertEquals(1f-acc, MoveUtils.goAccFastestDec(1, 0.1f, 1, acc));
        Assert.assertEquals(80f - 10f, MoveUtils.goAccFastestDec(80, 33, 100, 10));

        // respect maxV
        Assert.assertEquals(20f, MoveUtils.goAccFastestDec(10, 100, 20, 100));

        // can stop immediately after next step
        Assert.assertEquals(10f, MoveUtils.goAccFastestDec(10, 5, 100, 10), 0.1f);

        // can still accelerate since has large acc
        Assert.assertEquals(31.6f, MoveUtils.goAccFastestDec(10, 5, 100, 100), 0.1f);

        // would overshot if accelerates full
        Assert.assertEquals(12.5f, MoveUtils.goAccFastestDec(10, 15, 100, 5), 1);

        // would overshot if not slows down full acc
        Assert.assertEquals(8f, MoveUtils.goAccFastestDec(10, 15, 100, 2));

        // shall go back
        Assert.assertEquals(-acc, MoveUtils.goAccFastestDec(0, -10, 100, acc));

        // shall accelerate
        Assert.assertEquals(acc, MoveUtils.goAccFastestDec(0, 10, 100, acc));
    }

    public void testSimulateGoAccFastestDec() throws Exception {

        float x = 0;
        float v = 0;
        final int target = 100;

        for (int i = 0; i < 50; i++) {
            float v0 = v;
            v = MoveUtils.goAccFastestDec(v, target - x, 5, 1);
//            x += 0.5f*(v0 + v); // integrate
            x += v;
            System.out.println("step " + i + " v: " + v + " x: " + x);
        }

    }

    public void testSimulateIntegrateGoAccFastestDec() throws Exception {

        float x = 0;
        float v = 0;
        final int target = 100;

        for (int i = 0; i < 50; i++) {
            float v0 = v;
            v = MoveUtils.goAccFastestDec(v, target - x, 10, 2);
            x += 0.5f * (v0 + v); // integrate
//            x += v;
            System.out.println("step " + i + "\tv: " + v + "\tx: " + x);
        }

    }
}