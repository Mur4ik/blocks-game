package cz.kotu.game.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Slider extends Block {

    // helper fields
    final Vector2 target = new Vector2();
    final Vector2 target1 = new Vector2();
    final Vector2 dir = new Vector2();
    final Vector2 velocity = new Vector2();
    final Rectangle rect = new Rectangle();

    @Override
    public void update() {
        textureRegion = T.blockTextureRegion.get(3);

//        target.set(MathUtils.round(pos.x), MathUtils.round(pos.y));

        if (target.dst2(pos) < 0.0001f) {
//                target.add(1, 0);
        }
        target1.set(target);

        // integral position (round)
        Vector2 round = new Vector2();
        round.set(pos);
        round.x = MathUtils.round(round.x);
        round.y = MathUtils.round(round.y);

        Vector2 trunc = new Vector2();
        trunc.set(pos).sub(round);

        final boolean snappedx = Math.abs(trunc.x) < 0.01f;
        final boolean snappedy = Math.abs(trunc.y) < 0.01f;

        Vector2 targetDir = new Vector2();
        targetDir.set(target).sub(pos);

        if (snappedx && snappedy) {

            if (Math.abs(targetDir.x) < Math.abs(targetDir.y)) {
                targetDir.x = 0;
            } else {
                targetDir.y = 0;
            }
        } else {
            if (snappedx) {
                targetDir.x = 0;
                target1.x = pos.x;
            } else {
                targetDir.y = 0;
                target1.y = pos.y;
            }
        }
        targetDir.scl(0.1f);

        pos.add(targetDir);

//            pos.set(target);

    }

    public Rectangle getRect() {
        rect.setPosition(pos);
        rect.setSize(1);
        return rect;
    }
}
