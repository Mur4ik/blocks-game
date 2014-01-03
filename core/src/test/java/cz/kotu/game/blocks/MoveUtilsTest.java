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

        // shall stop
        Assert.assertEquals(0f, MoveUtils.goAccFastestDec(1, 0, 1, acc));

        // shall decelerate
//        Assert.assertEquals(-acc, MoveUtils.goAccFastestDec(1, 0.1f, 1, acc));
//        Assert.assertEquals(1f-acc, MoveUtils.goAccFastestDec(1, 0.1f, 1, acc));

//        Assert.assertEquals(1f-acc, MoveUtils.goAccFastestDec(1, 0.1f, 1, acc));

        // shall go back
        Assert.assertEquals(-acc, MoveUtils.goAccFastestDec(0, -1, 1, acc));

        // shall accelerate
        Assert.assertEquals(acc, MoveUtils.goAccFastestDec(0, 1, 1, acc));
    }
}
