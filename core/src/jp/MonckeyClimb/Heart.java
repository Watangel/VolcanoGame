package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/04.
 */

public class Heart extends ItemObject {

    public static final float HEART_WIDTH = 18.5f * 2;
    public static final float HEART_HEIGHT = 14.5f * 2;

    public Heart(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(HEART_WIDTH, HEART_HEIGHT);
    }

}
