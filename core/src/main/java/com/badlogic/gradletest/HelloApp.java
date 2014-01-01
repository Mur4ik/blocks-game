
package com.badlogic.gradletest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import cz.kotu.grids.GenericGrid;
import cz.kotu.grids.LinPos;
import cz.kotu.grids.LinearGrid;
import cz.kotu.grids.Pos;


public class HelloApp extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    final Array<TextureRegion> blockTextureRegion = new Array<TextureRegion>(16);
    Texture blocks0Texture;
    OrthographicCamera camera;

    Tile tile = new Tile();

    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));
    private LinearGrid blockTexs;

    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        camera.translate(-2, -2);

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");


        initBlockTextures();


//		try {
//			new FreeTypeFontGenerator(Gdx.files.internal("test.fnt"));
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		Bullet.init();

        // populate grid
        for (LinPos p : grid.getLinGrid()) {
            grid.set(p.i, new Square());
        }

        tile.init();

    }

    private void initBlockTextures() {

        // size of one block
        final int BS = 50;

        blocks0Texture = new Texture(Gdx.files.internal("blocks0.png"));

        blockTexs = new LinearGrid(5, 3);

        for (LinPos p : blockTexs) {
            blockTextureRegion.add(new TextureRegion(blocks0Texture, p.x * BS, p.y * BS, BS, BS));
        }

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        tile.update();

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(img, 0, 0);

        for (LinPos p : grid.getLinGrid()) {
            final Square square = grid.get(p.i);

            int neighHash = GridUtils.getNeighHash(p, new Predicate<Pos>() {
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

            neighHash /= 2;

            int image = neighHash;

//            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, MathUtils.random(8)* 45);
//            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 0.5f, 0.5f, 1, 1, 1, 1, 45);
            batch.draw(blockTextureRegion.get(image), p.x, p.y, 1, 1);

        }

        batch.draw(blockTextureRegion.get(2), tile.pos.x, tile.pos.y, 1, 1);

        Sprite sprite = new Sprite(blockTextureRegion.get(1));
//        sprite.setRegionWidth(1);
//        sprite.setRegionHeight(1);
//        sprite.setScale(0.5f);
        sprite.setOrigin(0f, 0f);
//        sprite.setOrigin(0.5f, 0.5f);
        sprite.setSize(1, 1);
//        sprite.setScale(0.02f);
//        sprite.setOrigin(25f, 25f);
        sprite.setOrigin(0.5f, 0.5f);
        sprite.setRotation(30);
//        sprite.rotate90(false);
        sprite.setPosition(4, 3);
//        sprite.setPosition(0, 0);
//        sprite.setPosition(4, 3);
//        batch.draw(sprite, 4, 4);
        sprite.draw(batch);

        batch.end();
    }

    /**
     * Contains info about single grid square
     */
    class Square {
        int image = 0;
        int count = 0;
    }

    class Tile {
        final Vector2 pos = new Vector2(2.3f, 3);

        // helper fields
        final Vector2 target = new Vector2();
        final Vector2 target1 = new Vector2();
        final Vector2 dir = new Vector2();

        public void init() {
            target.set(MathUtils.round(pos.x), MathUtils.round(pos.y));
        }

        public void update() {
            if (Gdx.input.isTouched()) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                final int x = MathUtils.floor(touchPos.x);
                final int y = MathUtils.floor(touchPos.y);
                target.set(x, y);
                final Square square = grid.get(x, y);
                if (square != null) {
                    square.count++;
                }
            }

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
    }

}
