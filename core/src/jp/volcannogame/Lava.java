package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/07.
 */

public class Lava extends GameObject {
    public static final int LAVA_STATE_READY = 0;
    public static final int LAVA_STATE_RUN = 1;
    public static final int LAVA_STATE_COOL = 2;

    public static int State;
    public Lava(Texture texture, int srcX, int srcY, int srcW, int srcH){
        super(texture, srcX, srcY, srcW, srcH);
        State = LAVA_STATE_READY;
    }
}
