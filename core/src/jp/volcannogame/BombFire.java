package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by iuchi on 2017/07/05.
 */

public class BombFire extends GameObject {
    public int count = 0;
    public BombFire(Texture texture, int srcX, int srcY, int srcW, int srcH){
        super(texture, srcX, srcY, srcW, srcH);
    }
}
