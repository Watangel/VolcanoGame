package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/15.
 */

public class Coin extends GameObject {
    public static final float COIN_WIDTH = 24f;
    public static final float COIN_HEIGHT = 24f;

    public static final int COIN_GET_FALSE = 0;
    public static final int COIN_GET_TRUE = 1;

    int hitState;

    public Coin(Texture texture, int srcX, int srcY, int srcW, int srcH){
        super(texture, srcX, srcY, srcW, srcH);
        setSize(COIN_WIDTH, COIN_HEIGHT);
        hitState = COIN_GET_FALSE;
    }

    public void update(float delta){
        velocity.add(0, GameScreenWithStage.GRAVITY * delta * 0.5f);
        setPosition(getX(), getY() + velocity.y * delta);
    }

    public void get(){
        setAlpha(0);
    }
}
