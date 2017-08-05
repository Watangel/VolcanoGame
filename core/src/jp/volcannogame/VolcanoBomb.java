package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/02.
 */

public class VolcanoBomb extends GameObject{
    public static final float BOMB_WIDTH = 24f;
    public static final float BOMB_HEIGHT = 24f;

    public static final int BOMB_HIT_FALSE = 0;
    public static final int BOMB_HIT_TRUE = 1;

    int hit_State;

    public VolcanoBomb(Texture texture, int srcX, int srcY, int srcW, int srcH){
        super(texture, srcX, srcY, srcW, srcH);
        setSize(BOMB_WIDTH, BOMB_HEIGHT);
        hit_State = BOMB_HIT_FALSE;
    }

    public void update(float delta){
        velocity.add(0, GameScreenWithStage.GRAVITY * delta * 0.5f);
        setPosition(getX(), getY() + velocity.y * delta);
    }

    public void Hit(){
        setAlpha(0);
    }
}
