package cz.kotu.game.blocks.hex;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import cz.kotu.game.blocks.BaseStage;
import cz.kotu.game.blocks.Draggable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexStage extends BaseStage {

    HexCoords3 hexCoords3 = new HexCoords3();

    final Array<Hex> hexes = new Array<Hex>();
    //    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));
    private Hex follower;

    private Hex halfling;

    ShapeRenderer shapeRenderer;
    private Vector3 touch = new Vector3();

    final HexGrid grid = new HexGrid();

    final List<HexGroup> groups = new ArrayList<HexGroup>();

    protected void init() {
        super.init();

        // populate grid
//        for (LinPos p : grid.getLinGrid()) {
//            grid.set(p.i, new Square());
//        }
        shapeRenderer = new ShapeRenderer();

//        {
//            Hex block = new Hex().setPos(2.3f, 3);
//            hexes.add(block);
//        }
//        {

        follower = new Hex();
        hexes.add(follower);
        halfling = new Hex();
        hexes.add(halfling);

        {
            HexGroup group = new HexGroup();
            final int ID = 1;
            group.set(0, 0, ID);
            group.set(1, 0, ID);
            group.set(1, 1, ID);
            group.move(new Axial(2, 1));
            groups.add(group);

        }

        {
            HexGroup group = new HexGroup();
            final int ID = 2;
            group.set(0, 0, ID);
            group.set(1, 0, ID);
            group.set(0, 1, ID);
            group.set(-1, -1, ID);
            groups.add(group);
        }


//        }
//        {
//            Slider slider = new Slider();
//            slider.textureRegion = T.blockTextureRegion.get(3);
//            slider.setPos(5, 6.4f);
//            slider.target.set(5, 6);
//            hexes.add(slider);
//        }
//
//        {
//            Slider slider = new Slider();
//            slider.textureRegion = T.blockTextureRegion.get(3);
//            slider.setPos(5, 6.4f);
//            slider.target.set(2, 6);
//            hexes.add(slider);
//        }

    }

    protected void update() {

        for (Hex block : hexes) {
            block.update();
        }


    }

    private <T> Iterable<T> getHexsOfType(final Class<T> clss) {
        Predicate<Hex> instanceOfPredicate = new Predicate<Hex>() {
            @Override
            public boolean evaluate(Hex block) {
                return clss.isInstance(block);
            }
        };
        return (Iterable<T>) new Predicate.PredicateIterable<Hex>(hexes, instanceOfPredicate);
    }

    protected void draw(Matrix4 combined) {
//        batch1.getProjectionMatrix();

        shapeRenderer.setProjectionMatrix(combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 0, 1);

//        shapeRenderer.line(q, r, x2, y2);
        shapeRenderer.rect(1, 1, 1, 1);
//        shapeRenderer.circle(q, r, radius);

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 0, 1);

        shapeRenderer.polyline(new float[]{1, 1, 2, 2});

//        Vector2[] verts = new Vector2[]{
//                new Vector2(0, 0),
//                new Vector2(1, 0),
//                new Vector2(0, 1),
//        };

//        projection.drawHexGrid(this.shapeRenderer);
//        hexCoords2.drawHexGrid(this.shapeRenderer);
//        hexCoords3.drawHex(0, 0, shapeRenderer);

//        hexCoords3.drawHexGrid(this.shapeRenderer);

        drawHexGrid(shapeRenderer);

        shapeRenderer.end();

        for (HexGroup group : groups) {
            group.drawHexGrid(hexCoords3, shapeRenderer);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.rect(1, 1, 1, 1);
//        shapeRenderer.circle(q, r, radius);

        shapeRenderer.setColor(1, 1, 1, 1);

        hexCoords3.drawHex(follower.pos, shapeRenderer);

        hexCoords3.drawHex(halfling.pos, shapeRenderer);

        hexCoords3.drawHex(touch, shapeRenderer);

//        hexCoords3.drawHex(HexCoords3.round(touch.cpy()), shapeRenderer);

        hexCoords3.drawHex(HexCoords3.roundToHex(touch.cpy()), shapeRenderer);

        shapeRenderer.end();

//        for (LinPos p : grid.getLinGrid()) {
//            final Square square = grid.get(p.i);
//
//            final int neighHash = GridUtils.getNeighHash(p, new Predicate<Axial>() {
//                @Override
//                public boolean evaluate(Axial pos) {
//                    final Square nsquare = grid.get(pos);
//                    if (nsquare == null) {
//                        return false;
//                    }
////                    return (pos.q + pos.r) % 2 == 0;
//                    return nsquare.count > 0;
//                }
//            });
//
//            if (square.count > 0) {
//
//                final Sprite sprite = T.blockSprites[neighHash];
//
//                sprite.setPosition(p.q, p.r);
//
//                sprite.draw(batch1);
//            } else {
//                int image = neighHash / 4;
//
////            batch.draw(blockTextureRegion.get(square.image), p.q, p.r, 0.5f, 0.5f, 1, 1, 1, 1, MathUtils.random(8)* 45);
////            batch.draw(blockTextureRegion.get(square.image), p.q, p.r, 0.5f, 0.5f, 1, 1, 1, 1, 45);
//                batch1.draw(T.blockTextureRegion.get(image), p.q, p.r, 1, 1);
//
//            }
//        }

        for (Hex block : hexes) {
//            block.draw(shapeRenderer);
        }

        batch.setProjectionMatrix(combined.cpy().scl(1 / 16f));

        batch.begin();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        decimalFormat.format(1.199); //"1.2"

//        font.setScale();
        {
            font.draw(batch, "q: " + decimalFormat.format(touch.x) +
                    " r: " + decimalFormat.format(touch.y) +
                    " z: " + decimalFormat.format(touch.z) +
                    " s: " + decimalFormat.format(touch.x + touch.y + touch.z), 0, 10 * 16);
        }
        {
            font.draw(batch, "q: " + decimalFormat.format(follower.pos.x) +
                    " r: " + decimalFormat.format(follower.pos.y) +
                    " z: " + decimalFormat.format(follower.pos.z) +
                    " s: " + decimalFormat.format(follower.pos.x + follower.pos.y + follower.pos.z), 0, 9 * 16);
        }
        {
            font.draw(batch, "q: " + decimalFormat.format(halfling.pos.x) +
                    " r: " + decimalFormat.format(halfling.pos.y) +
                    " z: " + decimalFormat.format(halfling.pos.z) +
                    " s: " + decimalFormat.format(halfling.pos.x + halfling.pos.y + halfling.pos.z), 0, 8 * 16);
        }


        batch.end();

    }


    void drawHexGrid(ShapeRenderer shapeRenderer) {
        int w = 6;
        int h = 4;

        for (int rd = 0; rd < h; rd++) {
            for (int coli = 0; coli < w; coli++) {
//                int c0 = 0;
                int c0 = 0 - (int) rd / 2;
                int col = c0 + coli;

                Axial axial = new Axial(col, rd);

                Integer v = (Integer) grid.get(axial);
                int val = 0;
                if (v != null) {
                    val = v;
                }

                shapeRenderer.setColor(val % 5, val % 3, val % 2, 1);
//                shapeRenderer.setColor(col % 2, rd % 2, (-col - rd) % 2, 1);
//                drawHex(2 * col + rd, 2 * rd + col, shapeRenderer);
//                drawHex(new Vector3(2 * col + rd, 2 * rd + col, 0), shapeRenderer);
//                drawHex(new Vector3(-2 * col + rd, -2 * rd + col, 0), shapeRenderer);
                hexCoords3.drawHex(hexCoords3.toCube(axial), 1f, shapeRenderer);
                Vector3 project = hexCoords3.project(axial);

//                drawHex(new Vector3(2 * col, 2 * rd, -2 * col - 2 * rd), shapeRenderer);
            }
        }

        for (int r = 0; r < h * 2; r++) {
            for (int q = 0; q < w * 2; q++) {
                shapeRenderer.setColor(q % 2, r % 2, (-q - r) % 2, 1);
                Vector3 v = new Vector3(q, r, 0);
                hexCoords3.project(v);
                int s = (int) v.x + (int) v.y + (int) v.z;
                switch (s % 2) {
                    case 0:
                        shapeRenderer.setColor(Color.WHITE);
                        break;
                    case 1:
                        shapeRenderer.setColor(Color.BLUE);
                        break;
                    case -1:
                        shapeRenderer.setColor(Color.GREEN);
                        break;
                    default:
                        shapeRenderer.setColor(Color.RED);
                        break;
                }
//                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.circle(v.x, v.y, 0.2f);
            }
        }

    }


    public void pointerDown(float x1, float y1) {
//        final int q = MathUtils.floor(x1);
//        final int r = MathUtils.floor(y1);
//        follower.target.set(q, r);
//        final Square square = grid.get(q, r);
//        if (square != null) {
//            square.count++;
//        }
    }

    /**
     * pointerId -> current Draggable
     */
    final Map<Integer, Draggable> draggedMap = new HashMap<Integer, Draggable>();

    public boolean touchDown(float x, float y, int pointer, int button) {
        for (Draggable draggable : getHexsOfType(Draggable.class)) {
            if (draggable.onPressed(x, y)) {
//                final Vector2 v = new Vector2();
//                draggable.getRect().getCenter(v);
//                v.sub(q, r);
//                draggable.target.add(v);
                draggedMap.put(pointer, draggable);
            }
        }
        return true;
    }

    public boolean touchDragged(float screenx, float screeny, int pointer) {
        hexCoords3.unproject(screenx, screeny, touch);

        return false;
    }

    public boolean touchUp(float x, float y, int pointer, int button) {
        final Draggable draggable = draggedMap.get(pointer);

        if (draggable != null) {
            if (draggable.onReleased(x, y)) {
                draggedMap.remove(pointer);
            }
        }

        Vector3 unproject = hexCoords3.unproject(x, y, new Vector3());
        Axial axial = hexCoords3.roundToAxial(unproject);
        Object v = grid.get(axial);
        int val = 0;
        if (v != null) {
            val = (Integer) v;
        }
        grid.set(axial, val + 1);

        return true;
    }

    @Override
    public boolean keyTyped(char character) {

        // follower goes by map
        switch (character) {
            case 'd':
                follower.pos.x++;
                follower.pos.z--;
                break;
            case 'e':
                follower.pos.y++;
                follower.pos.z--;
                break;
            case 'w':
                follower.pos.y++;
                follower.pos.x--;
                break;
            case 'a':
                follower.pos.z++;
                follower.pos.x--;
                break;
            case 'z':
                follower.pos.z++;
                follower.pos.y--;
                break;
            case 'q':
                follower.pos.x++;
                follower.pos.y--;
                break;
        }

        float sign = halfling.pos.x + halfling.pos.y + halfling.pos.z;

        switch (character) {
            case 'u':
                halfling.pos.x--;
                break;
            case 'i':
                halfling.pos.y++;
                break;
            case 'o':
//                halfling.pos.z--;
//                halfling.pos.z--;

                halfling.pos.z -= 0.66;
                halfling.pos.x += 0.33;
                halfling.pos.y += 0.33;

//                if (sign >= 0) {
//                    halfling.pos.z--;
//                } else {
//                    halfling.pos.q++;
//                    halfling.pos.r++;
//                }
                break;
            case 'j':
                halfling.pos.z++;
                break;
            case 'k':
                halfling.pos.y--;
                break;
            case 'l':
                halfling.pos.x++;
                break;
        }

        return super.keyTyped(character);
    }

}
