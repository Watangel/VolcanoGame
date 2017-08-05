package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by iuchi on 2017/06/28.
 */

public class ItemObject extends Sprite {
    public final Vector2 vector;

    public ItemObject(Texture texture, int X, int Y, int Width, int Height){
        super(texture, X, Y, Width, Height);
        vector = new Vector2();
    }
}
