package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

class Follower extends Block {

    // helper fields
    final Vector2 target = new Vector2();
    final Vector2 target1 = new Vector2();
    final Vector2 dir = new Vector2();

    public void init() {
        target.set(MathUtils.round(pos.x), MathUtils.round(pos.y));
    }

    @Override
    public void update() {

        if (target.dst2(pos) < 0.0001) {
//                target.add(1, 0);
        }
        target1.set(target);
        final boolean snappedx = Math.abs(MathUtils.round(pos.x) - pos.x) < 0.01;
        final boolean snappedy = Math.abs(MathUtils.round(pos.y) - pos.y) < 0.01;
        if (snappedx && snappedy) {
            if (Math.abs(target1.x - pos.x) < Math.abs(target1.y - pos.y)) {
                target1.x = pos.x;
            } else {
                target1.y = pos.y;
            }
        } else {
            if (snappedx) {
                target1.x = pos.x;
            } else {
                target1.y = pos.y;
            }
        }
        dir.set(target1).sub(pos).scl(0.1f);

        pos.add(dir);

//            pos.set(target);


    }

    @Override
    void draw(SpriteBatch batch) {
        batch.draw(T.blockTextureRegion.get(6), target.x, target.y, 1, 1);
        super.draw(batch);
    }
}
