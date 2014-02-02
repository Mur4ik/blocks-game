package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import cz.kotu.grids.GenericGrid;
import cz.kotu.grids.LinPos;
import cz.kotu.grids.LinearGrid;
import cz.kotu.grids.Pos;

import java.util.HashMap;
import java.util.Map;

public class GridStage extends BaseStage {

    private SpriteBatch batch;

    final Array<Block> blocks = new Array<Block>();
    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));
    private Follower follower;

    void init() {

        batch = new SpriteBatch();

        // populate grid
        for (LinPos p : grid.getLinGrid()) {
            grid.set(p.i, new Square());
        }

        {
            Block block = new Block().setPos(2.3f, 3);
            blocks.add(block);
        }
        {
            follower = new Follower();
            follower.textureRegion = T.blockTextureRegion.get(4);
            follower.setPos(8f, 3);
            blocks.add(follower);
        }
        {
            Slider slider = new Slider();
            slider.textureRegion = T.blockTextureRegion.get(3);
            slider.setPos(5, 6.4f);
            slider.target.set(5, 6);
            blocks.add(slider);
        }

        {
            Slider slider = new Slider();
            slider.textureRegion = T.blockTextureRegion.get(3);
            slider.setPos(5, 6.4f);
            slider.target.set(2, 6);
            blocks.add(slider);
        }

    }

    void update() {

        for (Block block : blocks) {
            block.update();
        }


    }

    private <T extends Block> Iterable<T> getBlocksOfType(final Class<T> clss) {
        Predicate<Block> instanceOfPredicate = new Predicate<Block>() {
            @Override
            public boolean evaluate(Block block) {
                return clss.isInstance(block);
            }
        };
        return (Iterable<T>) new Predicate.PredicateIterable<Block>(blocks, instanceOfPredicate);
    }

    void draw(Matrix4 combined) {
        batch.setProjectionMatrix(combined);

        batch.begin();

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

                sprite.draw(batch);
            } else {
                int image = neighHash / 4;

//            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, MathUtils.random(8)* 45);
//            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, 45);
                batch.draw(T.blockTextureRegion.get(image), p.x, p.y, 1, 1);

            }
        }

        for (Block block : blocks) {
            block.draw(batch);
        }
        batch.end();
    }

    public void pointerDown(float x1, float y1) {
        final int x = MathUtils.floor(x1);
        final int y = MathUtils.floor(y1);
        follower.target.set(x, y);
        final Square square = grid.get(x, y);
        if (square != null) {
            square.count++;
        }
    }

    final Map<Integer, Slider> draggedMap = new HashMap<Integer, Slider>();

    public boolean touchDown(float x, float y, int pointer, int button) {
        for (Slider slider : getBlocksOfType(Slider.class)) {
            if (slider.getRect().contains(x, y)) {
//                final Vector2 v = new Vector2();
//                slider.getRect().getCenter(v);
//                v.sub(x, y);
//                slider.target.add(v);
                draggedMap.put(pointer, slider);
            }
        }
        return true;
    }

    public boolean touchDragged(float x, float y, int pointer) {
        return false;
    }

    public boolean touchUp(float x, float y, int pointer, int button) {
        final Slider slider = draggedMap.get(pointer);

        if (slider != null) {
            final int fx = MathUtils.floor(x);
            final int fy = MathUtils.floor(y);
            slider.target.set(fx, fy);
            draggedMap.remove(pointer);
        }
        return true;
    }


    /**
     * Contains info about single grid square
     */
    class Square {
        int image = 0;
        int count = 0;
    }


}
