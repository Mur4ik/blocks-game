
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


public class HelloApp extends ApplicationAdapter {
    /**
     * pixel per meter
     */
    final float PPM = 50f;
    SpriteBatch batch;
    Texture img;
    final Array<TextureRegion> blockTextureRegion = new Array<TextureRegion>(16);
    Texture blocks0Texture;
    OrthographicCamera camera;

    Tile tile = new Tile();

    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);

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

        tile.init();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        tile.update();

        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(blockTextureRegion.get(0), 100, 100);
        batch.draw(blockTextureRegion.get(1), 200, 100);
        batch.draw(blockTextureRegion.get(2), 150, 150);


        batch.draw(blockTextureRegion.get(2), tile.pos.x * PPM, tile.pos.y * PPM);
        batch.end();
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
                target.set(MathUtils.floor(touchPos.x / PPM), MathUtils.floor(touchPos.y / PPM));
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
