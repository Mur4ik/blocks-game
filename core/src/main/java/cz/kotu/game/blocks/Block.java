package cz.kotu.game.blocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

class Block {

    final Vector2 pos = new Vector2();
    TextureRegion textureRegion = T.blockTextureRegion.get(5);

    Block() {

    }

    Block setPos(float x, float y) {
        pos.set(x, y);
        return this;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void update() {
    }

    void draw(SpriteBatch batch) {
        batch.draw(getTextureRegion(), pos.x, pos.y, 1, 1);
    }
}
