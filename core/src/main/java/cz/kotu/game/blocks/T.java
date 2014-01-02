package cz.kotu.game.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import cz.kotu.grids.LinPos;
import cz.kotu.grids.LinearGrid;

/**
 * Holder for game textures.
 */
public class T {

    static Texture img;
    static Texture blocks0Texture;

    static final Array<TextureRegion> blockTextureRegion = new Array<TextureRegion>();

    static Sprite[] blockSprites;

    public static void loadTextures() {

        img = new Texture("badlogic.jpg");

        blocks0Texture = new Texture(Gdx.files.internal("blocks0.png"));

        initBlockTextures(blocks0Texture);

        blockSprites = createBlockSprites();


    }

    private static void initBlockTextures(Texture blocks0Texture1) {

        // size of one block
        final int BS = 50;

        LinearGrid blockTexs = new LinearGrid(5, 3);

        for (LinPos p : blockTexs) {
            T.blockTextureRegion.add(new TextureRegion(blocks0Texture1, p.x * BS, p.y * BS, BS, BS));
        }

    }

    /**
     * Creates set of 16 Sprites depending on 4-neighbourhood of tile
     *
     * @see GridUtils.getNeighHash()
     */
    private static Sprite[] createBlockSprites() {

        final int E = 1 << 0;
        final int N = 1 << 1;
        final int W = 1 << 2;
        final int S = 1 << 3;

        // one is template for other templates
        Sprite sprite = new Sprite(blockTextureRegion.get(11));
        sprite.setSize(1, 1);
        sprite.setOrigin(0.5f, 0.5f);

        // capture 16 templates in proper state
        Sprite[] sprites = new Sprite[16];
        sprites[0] = new Sprite(sprite);

        // one edge on east
        sprite.setRegion(blockTextureRegion.get(10));
        sprites[E] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[N] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[W] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[S] = new Sprite(sprite);
        sprite.rotate90(false);

        // two opposite edges E and W
        sprite.setRegion(blockTextureRegion.get(9));
        sprites[E | W] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[N | S] = new Sprite(sprite);
        sprite.rotate90(true);

//        two edges E and N
        sprite.setRegion(blockTextureRegion.get(7));
        sprite.flip(true, true);
        sprites[E | N] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[N | W] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[W | S] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[S | E] = new Sprite(sprite);
        sprite.rotate90(false);
        sprite.flip(true, true);

//      three edges E and N and W
        sprite.setRegion(blockTextureRegion.get(12));
        sprite.flip(true, true);
        sprites[E | N | W] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[N | W | S] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[W | S | E] = new Sprite(sprite);
        sprite.rotate90(false);
        sprites[S | E | N] = new Sprite(sprite);
        sprite.rotate90(false);
        sprite.flip(true, true);

        // all edges
        sprite.setRegion(blockTextureRegion.get(8));
        sprites[E | N | W | S] = new Sprite(sprite);

        return sprites;

    }


}
