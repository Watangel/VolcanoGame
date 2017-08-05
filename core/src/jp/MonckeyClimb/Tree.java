package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/03.
 */

public class Tree extends ItemObject {

    public static final float TREE_WIDTH = 400 / 9.5f;
    public static final float TREE_HEIGHT = 1600 / 9.5f;

    public Tree(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(TREE_WIDTH, TREE_HEIGHT);
    }
}
