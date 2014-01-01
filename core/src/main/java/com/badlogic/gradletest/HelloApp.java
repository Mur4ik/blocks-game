
package com.badlogic.gradletest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import cz.kotu.grids.GenericGrid;
import cz.kotu.grids.LinPos;
import cz.kotu.grids.LinearGrid;


public class HelloApp extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    final Array<TextureRegion> blockTextureRegion = new Array<TextureRegion>(16);
    Texture blocks0Texture;
    OrthographicCamera camera;

    Tile tile = new Tile();

    GenericGrid<Square> grid = new GenericGrid<Square>(new LinearGrid(12, 8));

    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        camera.translate(-2, -2);

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");


        blocks0Texture = new Texture(Gdx.files.internal("blocks0.png"));
        blockTextureRegion.add(new TextureRegion(blocks0Texture, 150, 100, 50, 50));
        blockTextureRegion.add(new TextureRegion(blocks0Texture, 100, 50, 50, 50));
        blockTextureRegion.add(new TextureRegion(blocks0Texture, 50, 50, 50, 50));


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
            batch.draw(blockTextureRegion.get(square.image), p.x, p.y, 1, 1);
        }

        batch.draw(blockTextureRegion.get(2), tile.pos.x, tile.pos.y, 1, 1);
        batch.end();
    }

    /**
     * Contains info about single grid square
     */
    class Square {
        int image = 0;
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
                target.set(MathUtils.floor(touchPos.x), MathUtils.floor(touchPos.y));
            }

            if (target.dst2(pos) < 0.0001) {
//                target.add(1, 0);
            }
            target1.set(target);
            if (Math.abs(target1.x - pos.x) < Math.abs(target1.y - pos.y)) {
                target1.x = pos.x;
            } else {
                target1.y = pos.y;
            }
            dir.set(target1).sub(pos).scl(0.1f);

            pos.add(dir);

//            pos.set(target);
        }
    }

}
