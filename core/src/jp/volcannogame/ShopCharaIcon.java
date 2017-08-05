package jp.volcannogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by iuchi on 2017/07/17.
 */

public class ShopCharaIcon extends Sprite {
    public static final float ICON_WIDTH = 57f;
    public static final float ICON_HEIGHT = 108f;

    public boolean bought;

    public ShopCharaIcon(Texture texture, int x, int y, int w, int h){
        super(texture, x, y, w, h);
        setSize(ICON_WIDTH , ICON_HEIGHT);
        bought = false;
    }
}
