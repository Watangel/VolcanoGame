package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/10.
 */

public class TreePattern extends ItemObject {

    public static final float TREE_WIDTH = 400 / 9.5f;
    public static final float TREE_HEIGHT = 400 / 9.5f;

    public TreePattern(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(TREE_WIDTH, TREE_HEIGHT);
    }
}
