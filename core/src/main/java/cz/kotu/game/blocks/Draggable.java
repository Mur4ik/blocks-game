package cz.kotu.game.blocks;

/**
 * @author tkotula
 */
public interface Draggable {
    boolean onPressed(float x, float y);

    boolean onReleased(float x, float y);
}
