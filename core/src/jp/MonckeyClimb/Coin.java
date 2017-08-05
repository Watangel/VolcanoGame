package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/08.
 */

public class Coin extends ItemObject {

    public static final float COIN_WIDTH = 25;
    public static final float COIN_HEIGHT = 25;

    public static final float COIN_FALL = 3f;

    public Coin(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(COIN_WIDTH, COIN_HEIGHT);
    }

    public void updatefall(float delta){
        vector.add(0, COIN_FALL * delta);
        setPosition(getX(), getY() - vector.y);
    }

}
