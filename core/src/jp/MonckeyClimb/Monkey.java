package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/06/28.
 */

public class Monkey extends ItemObject {

    public static final float MONKEY_WIDTH = 81;
    public static final float MONKEY_HEIGHT = 74;

    public static final int MONKEY_STATE_STOP = 0;
    public static final int MONKEY_STATE_CLIMB = 1;
    static final int MONKEY_STATE_LEFT_MOVE = 2;
    static final int MONKEY_STATE_RIGHT_MOVE = 3;
    static final int MONKEY_STATE_LEFT_NOW = 4;
    static final int MONKEY_STATE_RIGHT_NOW = 5;

    public static final float MONKEY_JUMP_VELOCITY = 600;
    public static final float MONKEY_CLIMB_VELOCITY = 230;
    public static final float MONKEY_STOP_VELOCITY = 0;

    public static int State;

    public Monkey(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        //setSize(MONKEY_WIDTH, MONKEY_HEIGHT);
        State = MONKEY_STATE_STOP;
    }

    public void update (float delta){

        if(State == MONKEY_STATE_CLIMB){
            vector.set(0, MONKEY_CLIMB_VELOCITY * delta);
            //vector.add(0, 0.001f);
        }

        if(State == MONKEY_STATE_STOP || State == MONKEY_STATE_LEFT_NOW || State == MONKEY_STATE_RIGHT_NOW){
            vector.set(0, MONKEY_STOP_VELOCITY);
        }

        if (State == MONKEY_STATE_RIGHT_MOVE){
            vector.set(MONKEY_JUMP_VELOCITY * delta, 0);
        }
        if (State == MONKEY_STATE_LEFT_MOVE){
            vector.set(-MONKEY_JUMP_VELOCITY * delta, 0);
        }


        setPosition(PlayScreen.MONKEY_X += vector.x, PlayScreen.MONKEY_Y += vector.y);
    }
}
