
package cz.kotu.game.blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class HelloApp extends ApplicationAdapter {
    SpriteBatch batch;

    OrthographicCamera camera;

    Stage stage;

    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        camera.translate(-2, -2);

        batch = new SpriteBatch();

        T.loadTextures();

        stage = new Stage();

        stage.init();


//		try {
//			new FreeTypeFontGenerator(Gdx.files.internal("test.fnt"));
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		Bullet.init();



    }



    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        processInputs();

        stage.update();

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(T.img, 0, 0);

        stage.draw(batch);

        batch.end();
    }


    void processInputs() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            stage.pointerDown(touchPos.x, touchPos.y);
        }
    }

}
