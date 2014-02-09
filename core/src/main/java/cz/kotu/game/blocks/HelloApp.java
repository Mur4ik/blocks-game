
package cz.kotu.game.blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import cz.kotu.game.blocks.hex.HexStage;


public class HelloApp extends ApplicationAdapter {
    SpriteBatch batch;

    OrthographicCamera camera;

    BaseStage stage;

    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        camera.translate(-2, -2);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        T.loadTextures();

//        stage = new BaseStage();
//        stage = new GridStage();
        stage = new HexStage();

        stage.init();

//		try {
//			new FreeTypeFontGenerator(Gdx.files.internal("test.fnt"));
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		Bullet.init();

        Gdx.input.setInputProcessor(new LocalInputProcessor());

    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        processInputs();

        batch.setProjectionMatrix(camera.combined);

        stage.update();

        camera.update();

        batch.begin();

        batch.draw(T.img, 0, 0);
        batch.end();

        stage.draw(camera.combined);


//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(1, 1, 0, 1);
//
////        shapeRenderer.line(x, y, x2, y2);
//        shapeRenderer.rect(1, 1, 200, 200);
////        shapeRenderer.circle(x, y, radius);
//
//        shapeRenderer.end();

    }


    void processInputs() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            stage.pointerDown(touchPos.x, touchPos.y);
        }
        float CAM_SPEED = 1;
        float dx = 0;
        float dy = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx -= Gdx.graphics.getDeltaTime() * CAM_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx += Gdx.graphics.getDeltaTime() * CAM_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy += Gdx.graphics.getDeltaTime() * CAM_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy -= Gdx.graphics.getDeltaTime() * CAM_SPEED;
        }
        camera.translate(dx, dy);
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom *= Math.pow(2, Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom /= Math.pow(2, Gdx.graphics.getDeltaTime());
        }
    }

    class LocalInputProcessor extends InputAdapter {

        final Vector3 touchPos = new Vector3();

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            unproject(screenX, screenY);

            return stage.touchDown(touchPos.x, touchPos.y, pointer, button);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {

            unproject(screenX, screenY);

            return stage.touchDragged(touchPos.x, touchPos.y, pointer);
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {

            unproject(screenX, screenY);

            return stage.touchUp(touchPos.x, touchPos.y, pointer, button);
        }

        private void unproject(int screenX, int screenY) {
            touchPos.set(screenX, screenY, 0);
            camera.unproject(touchPos);
        }

        @Override
        public boolean keyTyped(char character) {
            return stage.keyTyped(character);
        }
    }

}
