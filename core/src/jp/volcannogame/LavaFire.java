package jp.volcannogame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by iuchi on 2017/07/12.
 */

public class LavaFire extends Sprite {
    public static int LAVAFIRE_STATE_STATIC = 0;
    public static int LAVAFIRE_STATE_DRAWABLE = 1;
    int check;

    private float mAlpha;

    public int mState;

    public LavaFire(Texture texture, int srcX, int srxY, int srcW, int srcH){
        super(texture, srcX, srxY, srcW, srcH);
        mState = LAVAFIRE_STATE_STATIC;
        mAlpha = 1;
    }

    @Override
    public void draw(Batch batch){
        if(mState == LAVAFIRE_STATE_STATIC){
            return;
        }else if(mState == LAVAFIRE_STATE_DRAWABLE){
            System.out.println("start" + mAlpha);
            if(checkDrawable()){
                super.draw(batch);
                System.out.println("描画");
                System.out.println(mAlpha + "end");
            }
        }
    }

    @Override
    public void setAlpha(float alpha){
        mAlpha = alpha;
        super.setAlpha(alpha);
    }

    private boolean checkDrawable(){
        if(mAlpha > 0){
            setAlpha(mAlpha - 0.01f);
            if(mAlpha < 0){
                setAlpha(0);
            }
            return true;
        }else{
            setAlpha(1);
            System.out.println("セットアルファ");
            mState = LAVAFIRE_STATE_STATIC;
            return false;
        }
    }
}
