package jp.volcannogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import menu.MiniGameCollection;
import menu.SelectShop;
import menu.TestHomeScreen;

/**
 * Created by iuchi on 2017/07/16.
 */

public class ShopScreen extends ScreenAdapter{
    static final float CAMERA_WIDTH = 300;
    static final float CAMERA_HEIGHT = 450;

    Stage stage;
    Stage stageDrawable;
    Sprite mBg;
    BitmapFont mFont;
    BitmapFont setsumei;
    BitmapFont purchase;
    String sep;

    MiniGameCollection mGame;

    //BackButton
    Button backButton;

    //キャラクター
    ShopCharaIcon OSSAN_ICON;
    Button OSSAN_BUTTON;
    ShopCharaIcon AKUMA_ICON;
    Button AKUMA_BUTTON;
    ShopCharaIcon MAHOUTSUKAI_ICON;
    Button MAHOUTSUKAI_BUTTON;

    //Select
    Sprite selectFrame;

    int coinCount;
    Preferences mPrefs;
    Preferences coinPrefs;

    public ShopScreen(MiniGameCollection game){
        mGame = game;
        mPrefs = Gdx.app.getPreferences("jp.volcannogame");
        coinPrefs = Gdx.app.getPreferences("jp.MiniGameCollection");
        //reset
 /*       mPrefs.remove("MAHOUTSUKAI");
        mPrefs.remove("AKUMA");
        mPrefs.remove("COIN");
        mPrefs.flush();*/
        coinCount = coinPrefs.getInteger("COIN", 0);

        //選択枠
        Texture selectTexture = new Texture("Select.png");
        selectFrame = new Sprite(selectTexture, 260, 120);
        whatSelect();

        //背景
        Texture bgTexture = new Texture("ShopBg.png");
        mBg = new Sprite(bgTexture, 0, 0, 300, 450);

        //フォント
        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        mFont.setColor(Color.GOLD);
        setsumei = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        setsumei.getData().setScale(0.5f);
        setsumei.getData().setLineHeight(30);
        setsumei.setColor(Color.BROWN);
        purchase = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        purchase.setColor(Color.ORANGE);
        purchase.getData().setScale(0.5f);

        //BackButton
        TextureRegion backRegion = new TextureRegion(new Texture("backbutton.png"), 420, 420);
        Button.ButtonStyle backButtonStyle = new Button.ButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(backRegion);
        backButtonStyle.down = new TextureRegionDrawable(backRegion);

        backButton = new Button(backButtonStyle);
        backButton.setSize(50, 50);
        backButton.setPosition(5, CAMERA_HEIGHT - 55);
        backButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle back = new Rectangle(0, 0, backButton.getWidth(), backButton.getHeight());
                if(back.contains(x, y)){
                    backButton.setSize(50 * 0.96f, 50 * 0.96f);
                    backButton.setPosition(5 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                }else {
                    backButton.setSize(50, 50);
                    backButton.setPosition(5, CAMERA_HEIGHT - 55);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                backButton.setSize(50 * 0.96f, 50 * 0.96f);
                backButton.setPosition(5 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setSize(50, 50);
                backButton.setPosition(5, CAMERA_HEIGHT - 55);
                Rectangle back = new Rectangle(0, 0, backButton.getWidth(), backButton.getHeight());
                if(back.contains(x, y)) {
                    stage.dispose();
                    mGame.setScreen(new SelectShop(mGame));
                }
            }
        });

        //キャラ説明
        sep = System.getProperty("line.separator");

        //キャラ
        Button.ButtonStyle charaButtonStyle = new Button.ButtonStyle();
        charaButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("null.png"), 64, 64));
        charaButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("null.png"), 64, 64));

        Texture ossanTexture = new Texture("player1.png");
        OSSAN_ICON = new ShopCharaIcon(ossanTexture, 0, 0, 19, 36);
        OSSAN_ICON.setPosition(50, CAMERA_HEIGHT -70 - 108);
        OSSAN_ICON.bought = true;
        OSSAN_BUTTON = new Button(charaButtonStyle);
        OSSAN_BUTTON.setSize(OSSAN_ICON.getWidth(), OSSAN_ICON.getHeight());
        OSSAN_BUTTON.setPosition(OSSAN_ICON.getX(), OSSAN_ICON.getY());
        OSSAN_BUTTON.addListener(new InputListener(){
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               return true;
           }
           public void touchUp(InputEvent event, float x, float y, int pointer, int button){
               Rectangle ossanRect = new Rectangle(0, 0, OSSAN_BUTTON.getWidth(), OSSAN_BUTTON.getHeight());
               if(ossanRect.contains(x, y)){
                   mPrefs.putInteger("OSSAN", 2);
                   if(!(mPrefs.getInteger("AKUMA", 0) == 0)){
                       mPrefs.putInteger("AKUMA", 1);
                   }
                   if(!(mPrefs.getInteger("MAHOUTSUKAI", 0) == 0)){
                       mPrefs.putInteger("MAHOUTSUKAI", 1);
                   }
                   mPrefs.flush();
                   selectFrame.setPosition(35, CAMERA_HEIGHT -183);
               }
           }

        });

        Texture akumaTexture = new Texture("player2.png");
        AKUMA_ICON = new ShopCharaIcon(akumaTexture, 0, 0, 19, 36);
        AKUMA_ICON.setPosition(50, CAMERA_HEIGHT - 200 - 108);
        AKUMA_BUTTON = new Button(charaButtonStyle);
        AKUMA_BUTTON.setSize(AKUMA_ICON.getWidth(), AKUMA_ICON.getHeight());
        AKUMA_BUTTON.setPosition(AKUMA_ICON.getX(), AKUMA_ICON.getY());
        AKUMA_BUTTON.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                Rectangle akumaRect = new Rectangle(0, 0, AKUMA_BUTTON.getWidth(), AKUMA_BUTTON.getHeight());
                if(akumaRect.contains(x, y)){
                    if(mPrefs.getInteger("AKUMA", 0) == 0){
                        if(coinPrefs.getInteger("COIN", 0) >= 700){
                            coinPrefs.putInteger("COIN", coinPrefs.getInteger("COIN", 0) - 700);
                            mPrefs.putInteger("AKUMA", 1);
                            mPrefs.flush();
                            coinPrefs.flush();
                        }
                    }else{
                        mPrefs.putInteger("AKUMA", 2);
                        if(!(mPrefs.getInteger("OSSAN", 2) == 1)){
                            mPrefs.putInteger("OSSAN", 1);
                        }
                        if(!(mPrefs.getInteger("MAHOUTSUKAI", 0) == 0)){
                            mPrefs.putInteger("MAHOUTSUKAI", 1);
                        }
                        mPrefs.flush();
                        selectFrame.setPosition(35, CAMERA_HEIGHT - 313);
                    }
                }
            }
        });
        Texture mahoutsukaiTexture = new Texture("player3.png");
        MAHOUTSUKAI_ICON = new ShopCharaIcon(mahoutsukaiTexture, 0, 0, 19, 36);
        MAHOUTSUKAI_ICON.setPosition(50, CAMERA_HEIGHT - 330 - 108);
        MAHOUTSUKAI_BUTTON = new Button(charaButtonStyle);
        MAHOUTSUKAI_BUTTON.setSize(MAHOUTSUKAI_ICON.getWidth(), MAHOUTSUKAI_ICON.getHeight());
        MAHOUTSUKAI_BUTTON.setPosition(MAHOUTSUKAI_ICON.getX(), MAHOUTSUKAI_ICON.getY());
        MAHOUTSUKAI_BUTTON.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                Rectangle mahoutsukaiRect = new Rectangle(0, 0, MAHOUTSUKAI_BUTTON.getWidth(), MAHOUTSUKAI_BUTTON.getHeight());
                if(mahoutsukaiRect.contains(x, y)){
                    if(mPrefs.getInteger("MAHOUTSUKAI", 0) == 0){
                        if(coinPrefs.getInteger("COIN", 0) >= 1000){
                            coinPrefs.putInteger("COIN", coinPrefs.getInteger("COIN", 0) - 1000);
                            mPrefs.putInteger("MAHOUTSUKAI", 1);
                            mPrefs.flush();
                            coinPrefs.flush();
                        }
                    }else{
                        mPrefs.putInteger("MAHOUTSUKAI", 2);
                        if(!(mPrefs.getInteger("AKUMA", 0) == 0)){
                            mPrefs.putInteger("AKUMA", 1);
                        }
                        if(!(mPrefs.getInteger("OSSAN", 2) == 1)){
                            mPrefs.putInteger("OSSAN", 1);
                        }
                        mPrefs.flush();
                        selectFrame.setPosition(35, CAMERA_HEIGHT - 443);
                    }
                }
            }
        });

        //ステージ
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(OSSAN_BUTTON);
        stage.addActor(AKUMA_BUTTON);
        stage.addActor(MAHOUTSUKAI_BUTTON);
        stage.addActor(backButton);
        Matrix4 cameraMatrix = stage.getViewport().getCamera().combined;
        mGame.batch.setProjectionMatrix(cameraMatrix);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        coinCount = coinPrefs.getInteger("COIN", 0);

        stage.act();

        mGame.batch.begin();
        mBg.draw(mGame.batch);
        OSSAN_ICON.draw(mGame.batch);
        setsumei.draw(mGame.batch,"HP:100" + sep + sep + "This is not a lamp." + sep + "His hair is" + sep + "bar cord", 120, CAMERA_HEIGHT -70);
        setsumei.draw(mGame.batch, "HP:150" + sep + sep + "He is not " + sep + "cavity bacteria", 120, CAMERA_HEIGHT - 200);
        setsumei.draw(mGame.batch, "HP:100" + sep + sep + "He has a good" + sep + "magic armour", 120, CAMERA_HEIGHT - 330);
        AKUMA_ICON.draw(mGame.batch);
        MAHOUTSUKAI_ICON.draw(mGame.batch);
        checkPurchase();
        mFont.getData().setScale(1);
        mFont.draw(mGame.batch, "COIN:" + coinCount, 60, 430);
        mFont.getData().setScale(0.5f);
        mFont.draw(mGame.batch, "$700", 120, 170);
        mFont.draw(mGame.batch, "$1000", 120, 40);
        selectFrame.draw(mGame.batch);
        mGame.batch.end();

        stage.draw();
    }

    public void checkPurchase(){
        if(mPrefs.getInteger("AKUMA", 0) == 0){
            purchase.setColor(Color.ORANGE);
            purchase.draw(mGame.batch, "non purchase", 170, 170);
        }else{
            purchase.setColor(Color.GREEN);
            purchase.draw(mGame.batch, "purchased", 170, 170);
        }

        if(mPrefs.getInteger("MAHOUTSUKAI", 0) == 0){
            purchase.setColor(Color.ORANGE);
            purchase.draw(mGame.batch, "non purchase", 170, 40);
        }else{
            purchase.setColor(Color.GREEN);
            purchase.draw(mGame.batch, "purchased", 170, 40);
        }
    }

    public void whatSelect(){
        if(mPrefs.getInteger("OSSAN", 2) == 2){
            selectFrame.setPosition(35, CAMERA_HEIGHT -183);
        }
        if(mPrefs.getInteger("AKUMA", 0) == 2){
            selectFrame.setPosition(35, CAMERA_HEIGHT -313);
        }
        if(mPrefs.getInteger("MAHOUTSUKAI") == 2){
            selectFrame.setPosition(35, CAMERA_HEIGHT - 443);
        }
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
 //       stageDrawable.getViewport().update(width, height);
    }
}
