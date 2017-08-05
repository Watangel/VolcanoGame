package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by iuchi on 2017/07/23.
 */

public class ButtonVerSize extends Sprite {
    final int BUTTON_NOPUSH = 1;
    final int BUTTON_PUSHED = 2;
    final int BUTTON_WAIT = 3;

    //コンストラクタ1
    int onState;
    int offState;

    //コンストラクタ2
    int buttonState;

    //共通
    int buttonCount;
    boolean buttonCheck;
    int sizeCount;

    /**
     *
     * @param x x座標
     * @param y y座標
     * @param width 幅
     * @param height 高さ
     * @param scale 設定した値を1fごとに縮小
     * @param on 押す前のsprite
     * @param off 押した後のsprite
     * @param onstate
     * @param offstate
     * @param buttoncount ボタンのアニメーション
     * @param sizecount サイズの大きさ設定
     * @param batch
     */

    public void BVS2(float x, float y, float width, float height, float scale, Sprite on, Sprite off, int onstate, int offstate, int buttoncount, int sizecount, SpriteBatch batch){

        on.setPosition(x, y);
        off.setPosition(x, y);
        if (onstate == BUTTON_NOPUSH){
            on.draw(batch);
            buttonCheck = false;
        } else if (onstate == BUTTON_PUSHED) {
            buttoncount++;
            if (buttoncount < 6) {
                sizecount--;
            } else if (buttoncount > 5 && buttoncount < 11) {
                sizecount++;
            } else if (buttoncount == 11) {
                buttoncount = 0;
                sizecount = 0;
                onstate = BUTTON_WAIT;
                offstate = BUTTON_NOPUSH;
            }
            on.setSize(width + sizecount * scale, height + sizecount * scale);
            on.setPosition(x - (sizecount * scale) / 2, y - (sizecount * scale) / 2);
            on.draw(batch);
            buttonCheck = true;
        } else if (offstate == BUTTON_NOPUSH){
            off.draw(batch);
            buttonCheck = true;
        } else if (offstate == BUTTON_PUSHED){
            buttoncount++;
            if (buttoncount < 6){
                sizecount--;
            } else if (buttoncount > 5 && buttoncount < 11){
                sizecount++;
            } else if (buttoncount == 11){
                buttoncount = 0;
                sizecount = 0;
                offstate = BUTTON_WAIT;
                onstate = BUTTON_NOPUSH;
            }
            off.setSize(width + sizecount * scale, height + sizecount * scale);
            off.setPosition(x - (sizecount * scale) / 2, y - (sizecount * scale) / 2);
            off.draw(batch);
            buttonCheck = true;
        }

        onState = onstate;
        offState = offstate;
        buttonCount = buttoncount;
        sizeCount = sizecount;
    }

    public void BVS1(float x, float y, float width, float height, float scale, Sprite button, int buttonstate, int buttoncount, int sizecount, SpriteBatch batch){

        button.setPosition(x, y);
        if (buttonstate == BUTTON_NOPUSH){
            button.draw(batch);
            buttonCheck = false;
        } else if (buttonstate == BUTTON_PUSHED){
            buttoncount++;
            if (buttoncount < 6){
                sizecount--;
                buttonCheck = false;
            } else if (buttoncount > 5 && buttoncount < 11){
                sizecount++;
                //System.out.println("ベアアアアアアアアアアアア");
                buttonCheck = false;
            } else if (buttoncount == 11){
                buttoncount = 0;
                sizecount = 0;
                //buttonstate = BUTTON_WAIT;
                //処理を書く用チェック
                buttonstate = BUTTON_NOPUSH;
                buttonCheck = true;
                //System.out.println("ボアアアアアアアアアアアアアアアアアアアアア");
            }

            //System.out.println(buttonCheck);
            button.setSize(width + sizecount * scale, height + sizecount * scale);
            button.setPosition(x - (sizecount * scale) / 2, y - (sizecount * scale) / 2);
            button.draw(batch);
        }

        buttonState = buttonstate;
        buttonCount = buttoncount;
        sizeCount = sizecount;
    }

    //第一コンストラクタ用
    public int getOnButtonState(){
        return onState;
    }

    public int getOffButtonState(){
        return offState;
    }

    //第二コンストラクタ用
    public int getButtonState(){
        return buttonState;
    }

    public boolean getButtonCheck(){
        return buttonCheck;
    }

    //共同
    public int getButtonCount(){
        return buttonCount;
    }

    public int getSizeCount(){
        return sizeCount;
    }
}
//元
/*stopButton.setPosition(STOPBUTTON_X, STOPBUTTON_Y);
        stopCancellButton.setPosition(STOPBUTTON_X, STOPBUTTON_Y);
        if (STOPBUTTON_STATE == STOPBUTTON_NOPUSH){
            stopButton.draw(minioriginGame.batch);
        } else if (STOPBUTTON_STATE == STOPBUTTON_PUSHED){
            stopbuttoncount++;
            if (stopbuttoncount < 6){
                stopbuttonsizecount--;
            } else if (stopbuttoncount > 5 && stopbuttoncount < 11){
                stopbuttonsizecount++;
            } else if (stopbuttoncount == 11){
                stopbuttoncount = 0;
                stopbuttonsizecount = 0;
                STOPBUTTON_STATE = STOPBUTTON_WAIT;
                STOPCANCELLBUTTON_STATE = STOPCANCELLBUTTON_NOPUSH;
            }
            stopButton.setSize(StopButton.STOPBUTTON_WIDTH + stopbuttonsizecount * STOPBUTTON_SCALE, StopButton.STOPBUTTON_HEIGHT + stopbuttonsizecount * STOPBUTTON_SCALE);
            stopButton.setPosition(STOPBUTTON_X - (stopbuttonsizecount * STOPBUTTON_SCALE) / 2, STOPBUTTON_Y - (stopbuttonsizecount * STOPBUTTON_SCALE) / 2);
            stopButton.draw(minioriginGame.batch);
        } else if (STOPCANCELLBUTTON_STATE == STOPCANCELLBUTTON_NOPUSH){
            stopCancellButton.draw(minioriginGame.batch);
        } else if (STOPCANCELLBUTTON_STATE == STOPCANCELLBUTTON_PUSHED){
            stopbuttoncount++;
            if (stopbuttoncount < 6){
                stopbuttonsizecount--;
            } else if (stopbuttoncount > 5 && stopbuttoncount < 11){
                stopbuttonsizecount++;
            } else if (stopbuttoncount == 11){
                stopbuttoncount = 0;
                stopbuttonsizecount = 0;
                STOPCANCELLBUTTON_STATE = STOPCANCELLBUTTON_WAIT;
                STOPBUTTON_STATE = STOPBUTTON_NOPUSH;
            }
            stopCancellButton.setSize(StopButton.STOPBUTTON_WIDTH + stopbuttonsizecount * STOPBUTTON_SCALE, StopButton.STOPBUTTON_HEIGHT + stopbuttonsizecount * STOPBUTTON_SCALE);
            stopCancellButton.setPosition(STOPBUTTON_X - (stopbuttonsizecount * STOPBUTTON_SCALE) / 2, STOPBUTTON_Y - (stopbuttonsizecount * STOPBUTTON_SCALE) / 2);
            stopCancellButton.draw(minioriginGame.batch);
        }*/

