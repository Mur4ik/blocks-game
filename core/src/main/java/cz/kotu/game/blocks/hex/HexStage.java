package cz.kotu.game.blocks.hex;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import cz.kotu.game.blocks.BaseStage;
import cz.kotu.game.blocks.Draggable;
import cz.kotu.game.blocks.T;

import java.text.DecimalFormat;
import java.util.*;

public class HexStage extends BaseStage {

    HexCoords3 hexCoords3 = new HexCoords3();

    final Array<Hex> hexes = new Array<Hex>();
    //    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));
    private Hex cursor;

    ShapeRenderer shapeRenderer;
    private Vector3 touch = new Vector3();

    final HexGrid grid = new HexGrid();

    final List<HexSet> groups = new ArrayList<HexSet>();

    final Set<HexSet> selectedGroups = new HashSet<HexSet>();

    HexSet editedGroup;

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

        cursor = new Hex();
        hexes.add(cursor);

//        {
//            HexGroup group = new HexGroup();
//            final int ID = 1;
//            group.set(0, 0, ID);
//            group.set(1, 0, ID);
//            group.set(1, 1, ID);
//            group.move(new Axial(2, 1));
//            groups.add(group);
//
//        }
//
//        {
//            HexGroup group = new HexGroup();
//            final int ID = 2;
//            group.set(0, 0, ID);
//            group.set(1, 0, ID);
//            group.set(0, 1, ID);
//            group.set(-1, -1, ID);
//            groups.add(group);
//        }

        initLevel1();

        {
            HexSet group = new HexSet();
            group.color = Color.PINK;
            editedGroup = group;
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

    private void initLevel1() {
        {
            HexSet group = new HexSet();
            group.color = Color.GREEN;
            group.addHex(0, 0);
            group.addHex(1, 0);
            group.addHex(1, 1);
            group.move(HexCoords3.toCube(new Axial(1, 1)));
            groups.add(group);

        }

        {
            HexSet group = new HexSet();
            group.color = Color.BLUE;
            group.addHex(0, 0);
            group.addHex(1, 0);
            group.addHex(0, 1);
            group.addHex(-1, -1);
            group.move(HexCoords3.toCube(new Axial(0, 3)));
            groups.add(group);
        }
        {
//            HexSet group = new HexSet();
//            group.color = Color.CYAN;
//            for (Axial axial : axialAreaInDistance(new Axial(0, 5), 5)) {
//                group.addHex(axial);
//            }
//            groups.add(group);
        }
        {
            HexSet group = new HexSet();
            group.color = Color.ORANGE;
            for (Axial axial : axialBorderInDistance(new Axial(0, 3), 3)) {
                group.addHex(axial);
            }
            // TODO implement equals of Hex using Axial with Axial - Hex.center.toAxial
            group.remove(group.getHex(HexCoords3.toCube(new Axial(3, 1))));
            group.remove(group.getHex(HexCoords3.toCube(new Axial(3, 2))));
            groups.add(group);
        }

    }

    List<Axial> axialAreaInDistance(Axial center, int N) {
        List<Axial> area = new ArrayList<Axial>();
        for (int dx = -N; dx <= N; dx++) {
            for (int dy = Math.max(-N, -dx - N); dy <= Math.min(N, -dx + N); dy++) {
                int dz = -dx - dy;
                Axial axial = new Axial().setCube(dx, dy, dz).add(center);
                area.add(axial);
            }
        }
        return area;
    }

    List<Axial> axialBorderInDistance(Axial center, int N) {
        List<Axial> border = axialAreaInDistance(center, N);
        for (Iterator<Axial> iterator = border.iterator(); iterator.hasNext(); ) {
            Axial axial = iterator.next();
            // remove all hexes that are not on border
            if (axial.distance(center) != N) {
                iterator.remove();
            }
        }
        return border;
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

        for (HexSet group : groups) {
            group.drawHexGrid(hexCoords3, shapeRenderer);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        // highlight selected groups
        shapeRenderer.setColor(Color.YELLOW);
        for (HexSet group : selectedGroups) {
            for (Hex hex : group) {
                hexCoords3.drawHex(hex.center, 0.8f, shapeRenderer);
            }
        }

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.rect(1, 1, 1, 1);
//        shapeRenderer.circle(q, r, radius);

        shapeRenderer.setColor(Color.WHITE);

        hexCoords3.drawHex(cursor.center, shapeRenderer);

        hexCoords3.drawHex(touch, shapeRenderer);

//        hexCoords3.drawHex(HexCoords3.round(touch.cpy()), shapeRenderer);

        hexCoords3.drawHex(HexCoords3.roundToHex(touch.cpy()), shapeRenderer);

        shapeRenderer.end();

        batch.setProjectionMatrix(combined.cpy());

        batch.begin();

        batch.setColor(Color.ORANGE);

        drawHexFull(new Vector3(), 1, batch);

        batch.end();

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
            font.draw(batch, "q: " + decimalFormat.format(cursor.center.x) +
                    " r: " + decimalFormat.format(cursor.center.y) +
                    " z: " + decimalFormat.format(cursor.center.z) +
                    " s: " + decimalFormat.format(cursor.center.x + cursor.center.y + cursor.center.z), 0, 9 * 16);
        }
        batch.end();

    }

    void drawHexFull(Vector3 origin, float size, SpriteBatch batch) {

        Vector3 o = new Vector3(origin);

        hexCoords3.project(o);

        batch.draw(T.hexTexture1, o.x - size * 56 / 64, o.y - 1 * size, 2 * size * 56 / 64, 2 * size);

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

                shapeRenderer.setColor(Color.BLACK);

                hexCoords3.drawHex(hexCoords3.toCube(axial), 1f, shapeRenderer);

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
                hexCoords3.drawHex(hexCoords3.toCube(axial), 0.3f, shapeRenderer);
                Vector3 project = hexCoords3.project(axial);

//                drawHex(new Vector3(2 * col, 2 * rd, -2 * col - 2 * rd), shapeRenderer);
            }
        }

        shapeRenderer.setColor(Color.BLACK);

        for (int r = 0; r < h * 2; r++) {
            for (int q = 0; q < w * 2; q++) {
//                shapeRenderer.setColor(q % 2, r % 2, (-q - r) % 2, 1);
                Vector3 v = new Vector3(q, r, 0);
                hexCoords3.project(v);
                shapeRenderer.circle(v.x, v.y, 0.2f);
            }
        }

    }


    public void pointerDown(float x1, float y1) {
//        final int q = MathUtils.floor(x1);
//        final int r = MathUtils.floor(y1);
//        cursor.target.set(q, r);
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
        // unproject in cube coords
        Vector3 unproject = hexCoords3.unproject(x, y, new Vector3());
        // round keyboard cursor to mouse position
        cursor.center.set(unproject);
        HexCoords3.roundToHex(cursor.center);

        if (button == Input.Buttons.LEFT) {
            pick(unproject);
        }

        if (button == Input.Buttons.RIGHT) {
            if (editedGroup != null) {
                editedGroup.addHex(HexCoords3.roundToAxial(unproject));
            }
        }

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

    private void pick(Vector3 cursorCube) {
        for (HexSet group : groups) {
            if (group.intersects(hexCoords3, cursorCube)) {
                // toggle group is selected
                if (!selectedGroups.remove(group)) {
                    selectedGroups.add(group);
                }
            }
        }
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

        Vector3 vector1 = new Vector3();

        // cursor goes by map
        switch (character) {
            case 'd':
                vector1.x++;
                vector1.z--;
                break;
            case 'e':
                vector1.y++;
                vector1.z--;
                break;
            case 'w':
                vector1.y++;
                vector1.x--;
                break;
            case 'a':
                vector1.z++;
                vector1.x--;
                break;
            case 'z':
                vector1.z++;
                vector1.y--;
                break;
            case 'x':
                vector1.x++;
                vector1.y--;
                break;
        }

        Vector3 vector2 = new Vector3();

        switch (character) {
            case 'u':
                // x--;
                vector2.set(-2, 1, 1).scl(1 / 3f);
                break;
            case 'i':
                // y++;
                vector2.set(-1, 2, -1).scl(1 / 3f);
                break;
            case 'o':
                // z --
                vector2.set(1, 1, -2).scl(1 / 3f);
                break;
            case 'j':
                // z++;
                vector2.set(-1, -1, 2).scl(1 / 3f);
                break;
            case 'k':
                // y--;
                vector2.set(1, -2, 1).scl(1 / 3f);
                break;
            case 'l':
                // x++;
                vector2.set(2, -1, -1).scl(1 / 3f);
                break;
        }

        cursor.center.add(vector1);
        cursor.center.add(vector2);

        switch (character) {
            case ' ':
                pick(cursor.center);
                break;
        }

//        Axial dir1 = hexCoords3.roundToAxial(vector1);
//        Axial dir2 = hexCoords3.roundToAxial(vector2);

        for (HexSet selectedGroup : selectedGroups) {
            selectedGroup.move(vector1);
            selectedGroup.move(vector2);
        }

        boolean rollback = false;
        for (HexSet selectedGroup : selectedGroups) {
            for (HexSet group : groups) {
                if (group == selectedGroup) {
                    // skip checking self
                    continue;
                }
                if (selectedGroups.contains(group)) {
                    // skip all selected group - they move together
                    continue;
                }
                if (selectedGroup.intersects(group)) {
                    // rollback
                    rollback = true;
                }
            }
        }

        if (rollback) {
            vector1.scl(-1);
            vector2.scl(-1);
            for (HexSet selectedGroup : selectedGroups) {
                selectedGroup.move(vector1);
                selectedGroup.move(vector2);
            }
        }

        return super.keyTyped(character);
    }

}
