package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/08.
 */

public class Bikkuri extends ItemObject {

    public static final float BIKKURI_WIDTH = 62 / 2;
    public static final float BIKKURI_HEIGHT = 49 / 2;

    public Bikkuri(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(BIKKURI_WIDTH, BIKKURI_HEIGHT);
    }
}
