package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import cz.kotu.grids.GenericGrid;
import cz.kotu.grids.LinPos;
import cz.kotu.grids.LinearGrid;
import cz.kotu.grids.Pos;

public class Stage {


    final Array<Block> blocks = new Array<Block>();
    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));
    private Follower follower;

    void init() {

        // populate grid
        for (LinPos p : grid.getLinGrid()) {
            grid.set(p.i, new Square());
        }


        Block block = new Block().setPos(2.3f, 3);
        blocks.add(block);

        follower = new Follower();
        follower.textureRegion = T.blockTextureRegion.get(4);
        follower.setPos(8f, 3);
        blocks.add(follower);

    }

    void update() {
        for (Block block : blocks) {
            block.update();
        }
    }

    void draw(SpriteBatch batch1) {
        for (LinPos p : grid.getLinGrid()) {
            final Square square = grid.get(p.i);

            final int neighHash = GridUtils.getNeighHash(p, new Predicate<Pos>() {
                @Override
                public boolean evaluate(Pos pos) {
                    final Square nsquare = grid.get(pos);
                    if (nsquare == null) {
                        return false;
                    }
//                    return (pos.x + pos.y) % 2 == 0;
                    return nsquare.count > 0;
                }
            });

            if (square.count > 0) {

                final Sprite sprite = T.blockSprites[neighHash];

                sprite.setPosition(p.x, p.y);

                sprite.draw(batch1);
            } else {
                int image = neighHash / 4;

//            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, MathUtils.random(8)* 45);
//            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, 45);
                batch1.draw(T.blockTextureRegion.get(image), p.x, p.y, 1, 1);

            }
        }

        for (Block block : blocks) {
            block.draw(batch1);
        }
    }

    void pointerDown(float x1, float y1) {
        final int x = MathUtils.floor(x1);
        final int y = MathUtils.floor(y1);
        follower.target.set(x, y);
        final Square square = grid.get(x, y);
        if (square != null) {
            square.count++;
        }
    }


    /**
     * Contains info about single grid square
     */
    class Square {
        int image = 0;
        int count = 0;
    }


}
