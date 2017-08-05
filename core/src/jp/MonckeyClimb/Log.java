package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/03.
 */

public class Log extends ItemObject {
    public static final float LOG_WIDTH = 30 * 3;
    public static final float LOG_HEIGHT = 12 * 3;

    public static final float LOG_FALL = 5f;
    float accelaccel;

    public Log(Texture texture, int X, int Y, int Width, int Height) {
        super(texture, X, Y, Width, Height);
        setSize(LOG_WIDTH, LOG_HEIGHT);
    }

    public void acceltete(int accel){
        accelaccel = accel;
    }

    public void update(float delta){
        vector.set(accelaccel * delta, 0);

        setPosition(getX() + vector.x, getY());
    }
    public void updatefall(float delta){
        vector.add(0, LOG_FALL * delta);
        setPosition(getX(), getY() - vector.y);
    }
}
