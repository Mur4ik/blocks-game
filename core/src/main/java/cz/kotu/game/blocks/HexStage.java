package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;

import java.util.HashMap;
import java.util.Map;

public class HexStage extends BaseStage {

    HexCoords hexCoords = new HexCoords();

    final Array<Hex> blocks = new Array<Hex>();
    //    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));
    private Follower follower;

    ShapeRenderer shapeRenderer;

    void init() {

        // populate grid
//        for (LinPos p : grid.getLinGrid()) {
//            grid.set(p.i, new Square());
//        }
        shapeRenderer = new ShapeRenderer();

//        {
//            Hex block = new Hex().setPos(2.3f, 3);
//            blocks.add(block);
//        }
//        {
//            follower = new Follower();
//            follower.textureRegion = T.blockTextureRegion.get(4);
//            follower.setPos(8f, 3);
//            blocks.add(follower);
//        }
//        {
//            Slider slider = new Slider();
//            slider.textureRegion = T.blockTextureRegion.get(3);
//            slider.setPos(5, 6.4f);
//            slider.target.set(5, 6);
//            blocks.add(slider);
//        }
//
//        {
//            Slider slider = new Slider();
//            slider.textureRegion = T.blockTextureRegion.get(3);
//            slider.setPos(5, 6.4f);
//            slider.target.set(2, 6);
//            blocks.add(slider);
//        }

    }

    void update() {

        for (Hex block : blocks) {
            block.update();
        }


    }

    private <T extends Hex> Iterable<T> getHexsOfType(final Class<T> clss) {
        Predicate<Hex> instanceOfPredicate = new Predicate<Hex>() {
            @Override
            public boolean evaluate(Hex block) {
                return clss.isInstance(block);
            }
        };
        return (Iterable<T>) new Predicate.PredicateIterable<Hex>(blocks, instanceOfPredicate);
    }

    void draw(Matrix4 combined) {
//        batch1.getProjectionMatrix();

        shapeRenderer.setProjectionMatrix(combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 0, 1);

//        shapeRenderer.line(x, y, x2, y2);
        shapeRenderer.rect(1, 1, 1, 1);
//        shapeRenderer.circle(x, y, radius);

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 0, 1);

        shapeRenderer.polyline(new float[]{1, 1, 2, 2});

//        Vector2[] verts = new Vector2[]{
//                new Vector2(0, 0),
//                new Vector2(1, 0),
//                new Vector2(0, 1),
//        };

        shapeRenderer.setColor(1, 0, 1, 1);

        hexCoords.drawHex(0, 0, shapeRenderer);

        shapeRenderer.setColor(0, 1, 1, 1);

        hexCoords.drawHex(1, 1, shapeRenderer);

//        shapeRenderer.rect(1, 1, 1, 1);
//        shapeRenderer.circle(x, y, radius);

        shapeRenderer.end();

//        for (LinPos p : grid.getLinGrid()) {
//            final Square square = grid.get(p.i);
//
//            final int neighHash = GridUtils.getNeighHash(p, new Predicate<Pos>() {
//                @Override
//                public boolean evaluate(Pos pos) {
//                    final Square nsquare = grid.get(pos);
//                    if (nsquare == null) {
//                        return false;
//                    }
////                    return (pos.x + pos.y) % 2 == 0;
//                    return nsquare.count > 0;
//                }
//            });
//
//            if (square.count > 0) {
//
//                final Sprite sprite = T.blockSprites[neighHash];
//
//                sprite.setPosition(p.x, p.y);
//
//                sprite.draw(batch1);
//            } else {
//                int image = neighHash / 4;
//
////            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, MathUtils.random(8)* 45);
////            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, 45);
//                batch1.draw(T.blockTextureRegion.get(image), p.x, p.y, 1, 1);
//
//            }
//        }

        for (Hex block : blocks) {
//            block.draw(shapeRenderer);
        }
    }

    public void pointerDown(float x1, float y1) {
//        final int x = MathUtils.floor(x1);
//        final int y = MathUtils.floor(y1);
//        follower.target.set(x, y);
//        final Square square = grid.get(x, y);
//        if (square != null) {
//            square.count++;
//        }
    }

    final Map<Integer, Slider> draggedMap = new HashMap<Integer, Slider>();

    public boolean touchDown(float x, float y, int pointer, int button) {
//        for (Slider slider : getHexsOfType(Slider.class)) {
//            if (slider.getRect().contains(x, y)) {
////                final Vector2 v = new Vector2();
////                slider.getRect().getCenter(v);
////                v.sub(x, y);
////                slider.target.add(v);
//                draggedMap.put(pointer, slider);
//            }
//        }
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

}
