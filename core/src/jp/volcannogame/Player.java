package jp.volcannogame;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/06/30.
 */

public class Player extends GameObject{
    //幅、高さ
    public static final float PLAYER_WIDTH = 28.5f;
    public static final float PLAYER_HEIGHT = 54f;

    //状態(上昇、降下)
    public static final int PLAYER_STATE_STATIC = 0;
    public static final int PLAYER_STATE_JUMP = 1;
    public static final int PLAYER_STATE_FALL = 2;

    //速度(x,y)
    public static final float PLAYER_JUMP_VELOCITY = 450f;
    public static final float PLAYER_MOVE_VELOCITY = 600.0f;

    //HP
    public float PLAYER_MAX_HP = 100;
    public float PLAYER_HP;

    int mState;

    public Player(Texture texture, int srcX, int srcY, int srcW, int srcH){
        super(texture, srcX, srcY, srcW, srcH);
        setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        PLAYER_HP = PLAYER_MAX_HP;
        mState = PLAYER_STATE_FALL;
    }

    public void update(float delta, float accelX){
        velocity.add(0, GameScreenWithStage.GRAVITY * delta);
        velocity.x = -accelX / 10 * PLAYER_MOVE_VELOCITY;
        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);

        if(velocity.y > 0){
            if(mState != PLAYER_STATE_JUMP){
                mState = PLAYER_STATE_JUMP;
            }
        }

        if(velocity.y < 0){
            if(mState != PLAYER_STATE_FALL){
                mState = PLAYER_STATE_FALL;
            }
        }

        if(getY() < 0){
            setY(0);
            velocity.y = 0;
        }

        if(velocity.y == 0){
            if(mState != PLAYER_STATE_STATIC){
                mState = PLAYER_STATE_STATIC;
            }
        }

        if(getX() < 0){
            setX(0);
        }else if(getX() + PLAYER_WIDTH > GameScreenWithStage.CAMERA_WIDTH){
            setX(GameScreenWithStage.CAMERA_WIDTH - PLAYER_WIDTH);
        }
    }

    public void jump(){
        if(mState == PLAYER_STATE_STATIC){
            velocity.y = PLAYER_JUMP_VELOCITY;
            mState = PLAYER_STATE_JUMP;
        }
    }


}
