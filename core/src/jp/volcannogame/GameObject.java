package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by iuchi on 2017/06/30.
 */

public class GameObject extends Sprite {
    public final Vector2 velocity;

    public GameObject(Texture texture, int srcX, int srcY, int srcW, int srcH){
        super(texture, srcX, srcY, srcW, srcH);

        velocity = new Vector2();
    }
}
