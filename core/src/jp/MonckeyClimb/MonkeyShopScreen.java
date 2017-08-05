package jp.MonckeyClimb;

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
 * Created by iuchi on 2017/08/02.
 */

public class MonkeyShopScreen extends ScreenAdapter {
    public static final float CAMERA_WIDTH = 300;
    public static final float CAMERA_HEIGHT = 450;

    float nomalMonkeyPos = 280;
    float yellowMonkeyPos = 150;
    float pinkMonkeyPos = 20;

    MiniGameCollection mGame;

    //背景
    Sprite bg;

    //ステージ
    Stage stage;

    //フォント
    BitmapFont mFont;
    BitmapFont monkeyName;

    //ボタン
    Button backButton;

    Button nomalMonkey;
    Button yellowMonkey;
    Button pinkMonkey;
    Button blueMonkey;

    //セレクト
    static final int SELECT_NOMAL_MONKEY = 0;
    static final int SELECT_YELLOW_MONKEY = 1;
    static final int SELECT_PINK_MONKEY = 2;
    static final int SELECT_BLUE_MONKEY = 3;

    Preferences coinPrefs;
    Preferences mPrefs;

    String sep;

    Sprite selectFrame;

    public MonkeyShopScreen(MiniGameCollection game){
        sep = System.getProperty("line.separator");
        mGame = game;
        coinPrefs = Gdx.app.getPreferences("jp.MiniGameCollection");

        mPrefs = Gdx.app.getPreferences("MonkeyClimb");
        mPrefs.putInteger("YELLOW_MONKEY", 1);
        mPrefs.putInteger("PINK_MONKEY", 1);
        mPrefs.flush();

        //選択枠
        Texture selectTexture = new Texture("Select.png");
        selectFrame = new Sprite(selectTexture, 260, 120);
        whatSelect();

        //フォント
        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        mFont.setColor(Color.GOLD);
        monkeyName = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        monkeyName.getData().setScale(0.7f);
        monkeyName.getData().setLineHeight(30);

        //背景
        bg = new Sprite(new TextureRegion(new Texture("ShopBg.png"), 300, 450));
        bg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);

        //バックボタン
        TextureRegion backRegion = new TextureRegion(new Texture("backbutton.png"), 420, 420);
        Button.ButtonStyle backButtonStyle = new Button.ButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(backRegion);
        backButtonStyle.down = new TextureRegionDrawable(backRegion);

        backButton = new Button(backButtonStyle);
        backButton.setSize(50, 50);
        backButton.setPosition(5, CAMERA_HEIGHT - 55);
        backButton.addListener(new InputListener() {
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Rectangle back = new Rectangle(0, 0, backButton.getWidth(), backButton.getHeight());
                if (back.contains(x, y)) {
                    backButton.setSize(50 * 0.96f, 50 * 0.96f);
                    backButton.setPosition(5 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                } else {
                    backButton.setSize(50, 50);
                    backButton.setPosition(5, CAMERA_HEIGHT - 55);
                }
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setSize(50 * 0.96f, 50 * 0.96f);
                backButton.setPosition(5 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backButton.setSize(50, 50);
                backButton.setPosition(5, CAMERA_HEIGHT - 55);
                Rectangle back = new Rectangle(0, 0, backButton.getWidth(), backButton.getHeight());
                if (back.contains(x, y)) {
                    stage.dispose();
                    mGame.setScreen(new SelectShop(mGame));
                }
            }
        });

        TextureRegion nomalMonkeyRegion = new TextureRegion(new Texture("monkey2.png"), 81, 81);
        TextureRegion yellowMonkeyRegion = new TextureRegion(new Texture("monkey_yellow.png"), 81, 81);
        TextureRegion pinkMonkeyRegion = new TextureRegion(new Texture("monkey_pink.png"), 81, 81);

        //ノーマルモンキー
        Button.ButtonStyle nomalMonkeyStyle = new Button.ButtonStyle();
        nomalMonkeyStyle.up = new TextureRegionDrawable(nomalMonkeyRegion);
        nomalMonkeyStyle.down = new TextureRegionDrawable(nomalMonkeyRegion);
        nomalMonkey = new Button(nomalMonkeyStyle);
        nomalMonkey.setSize(100, 100);
        nomalMonkey.setPosition(40, nomalMonkeyPos);
        nomalMonkey.addListener(new InputListener(){
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Rectangle nomal = new Rectangle(0, 0, nomalMonkey.getWidth(), nomalMonkey.getHeight());
                if (nomal.contains(x, y)) {
                    nomalMonkey.setSize(100 * 0.96f, 100 * 0.96f);
                    nomalMonkey.setPosition(40 + 100 * 0.02f, nomalMonkeyPos + 100 * 0.02f);
                } else {
                    nomalMonkey.setSize(100, 100);
                    nomalMonkey.setPosition(40, nomalMonkeyPos);
                }
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                nomalMonkey.setSize(100 * 0.96f, 100 * 0.96f);
                nomalMonkey.setPosition(40 + 100 * 0.02f, nomalMonkeyPos + 100 * 0.02f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                nomalMonkey.setSize(100, 100);
                nomalMonkey.setPosition(40, nomalMonkeyPos);
                Rectangle nomal = new Rectangle(0, 0, nomalMonkey.getWidth(), nomalMonkey.getHeight());
                if (nomal.contains(x, y)) {
                    if(mPrefs.getInteger("NOMAL_MONKEY", 2) == 1){
                        mPrefs.putInteger("NOMAL_MONKEY", 2);
                        selected(SELECT_NOMAL_MONKEY);
                        whatSelect();
                    }
                }
            }
        });

        //イエローモンキー
        Button.ButtonStyle yellowMonkeyStyle = new Button.ButtonStyle();
        yellowMonkeyStyle.up = new TextureRegionDrawable(yellowMonkeyRegion);
        yellowMonkeyStyle.down = new TextureRegionDrawable(yellowMonkeyRegion);
        yellowMonkey = new Button(yellowMonkeyStyle);
        yellowMonkey.setSize(100, 100);
        yellowMonkey.setPosition(40, yellowMonkeyPos);
        yellowMonkey.addListener(new InputListener(){
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Rectangle yellow = new Rectangle(0, 0, yellowMonkey.getWidth(), yellowMonkey.getHeight());
                if (yellow.contains(x, y)) {
                    yellowMonkey.setSize(100 * 0.96f, 100 * 0.96f);
                    yellowMonkey.setPosition(40 + 100 * 0.02f, yellowMonkeyPos + 100 * 0.02f);
                } else {
                    yellowMonkey.setSize(100, 100);
                    yellowMonkey.setPosition(40, yellowMonkeyPos);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                yellowMonkey.setSize(100 * 0.96f, 100 * 0.96f);
                yellowMonkey.setPosition(40 + 100 * 0.02f, yellowMonkeyPos + 100 * 0.02f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                yellowMonkey.setSize(100, 100);
                yellowMonkey.setPosition(40, yellowMonkeyPos);
                Rectangle yellow = new Rectangle(0, 0, yellowMonkey.getWidth(), yellowMonkey.getHeight());
                if (yellow.contains(x, y)) {
                    //購入されていない場合
                    if(mPrefs.getInteger("YELLOW_MONKEY", 0) == 0){
                        if(coinPrefs.getInteger("COIN", 0) >= 600){
                            coinPrefs.putInteger("COIN", coinPrefs.getInteger("COIN", 0) - 600);
                            mPrefs.putInteger("YELLOW_MONKEY", 1);
                            coinPrefs.flush();
                            mPrefs.flush();
                        }
                    }
                    //購入されている場合
                    if(mPrefs.getInteger("YELLOW_MONKEY", 0) == 1){
                        mPrefs.putInteger("YELLOW_MONKEY", 2);
                        selected(SELECT_YELLOW_MONKEY);
                        whatSelect();
                    }
                }
            }
        });

        //ピンクモンキー
        Button.ButtonStyle pinkMonkeyStyle = new Button.ButtonStyle();
        pinkMonkeyStyle.up = new TextureRegionDrawable(pinkMonkeyRegion);
        pinkMonkeyStyle.down = new TextureRegionDrawable(pinkMonkeyRegion);
        pinkMonkey = new Button(pinkMonkeyStyle);
        pinkMonkey.setSize(100, 100);
        pinkMonkey.setPosition(40, pinkMonkeyPos);
        pinkMonkey.addListener(new InputListener(){
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Rectangle pink = new Rectangle(0, 0, pinkMonkey.getWidth(), pinkMonkey.getHeight());
                if (pink.contains(x, y)) {
                    pinkMonkey.setSize(100 * 0.96f, 100 * 0.96f);
                    pinkMonkey.setPosition(40 + 100 * 0.02f, pinkMonkeyPos + 100 * 0.02f);
                } else {
                    pinkMonkey.setSize(100, 100);
                    pinkMonkey.setPosition(40, pinkMonkeyPos);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pinkMonkey.setSize(100 * 0.96f, 100 * 0.96f);
                pinkMonkey.setPosition(40 + 100 * 0.02f, pinkMonkeyPos + 100 * 0.02f);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pinkMonkey.setSize(100, 100);
                pinkMonkey.setPosition(40, pinkMonkeyPos);
                Rectangle pink = new Rectangle(0, 0, pinkMonkey.getWidth(), pinkMonkey.getHeight());
                if (pink.contains(x, y)) {
                    //購入されていない場合
                    if(mPrefs.getInteger("PINK_MONKEY", 0) == 0){
                        if(coinPrefs.getInteger("COIN", 0) >= 600){
                            coinPrefs.putInteger("COIN", coinPrefs.getInteger("COIN", 0) - 600);
                            mPrefs.putInteger("PINK_MONKEY", 1);
                            coinPrefs.flush();
                            mPrefs.flush();
                        }
                    }
                    //購入されている場合
                    if(mPrefs.getInteger("PINK_MONKEY", 0) == 1){
                        mPrefs.putInteger("PINK_MONKEY", 2);
                        selected(SELECT_PINK_MONKEY);
                        whatSelect();
                    }
                }
            }
        });

        //ステージ
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        stage.addActor(backButton);
        stage.addActor(nomalMonkey);
        stage.addActor(yellowMonkey);
        stage.addActor(pinkMonkey);
        Gdx.input.setInputProcessor(stage);
        Matrix4 cameraMatrix = stage.getViewport().getCamera().combined;
        mGame.batch.setProjectionMatrix(cameraMatrix);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        mGame.batch.begin();
        bg.draw(mGame.batch);
        mFont.getData().setScale(1);
        mFont.setColor(Color.GOLD);
        mFont.draw(mGame.batch, "COIN:" + coinPrefs.getInteger("COIN", 0), 60, 430);
        checkPurchased();
        monkeyName.setColor(Color.BROWN);
        monkeyName.draw(mGame.batch, "Nomal" + sep + "  Monkey", 153, nomalMonkeyPos + 70);
        monkeyName.setColor(Color.YELLOW);
        monkeyName.draw(mGame.batch, "Yellow" + sep + "  Monkey", 153, yellowMonkeyPos + 70);
        monkeyName.setColor(Color.PINK);
        monkeyName.draw(mGame.batch, "Pink" + sep + "  Monkey", 153, pinkMonkeyPos + 70);
        selectFrame.draw(mGame.batch);
        mGame.batch.end();

        stage.draw();
    }

    public void whatSelect(){
        if(mPrefs.getInteger("NOMAL_MONKEY", 2) == 2){
            selectFrame.setPosition(20, nomalMonkeyPos - 10);
        }
        if(mPrefs.getInteger("YELLOW_MONKEY", 0) == 2){
            selectFrame.setPosition(20, yellowMonkeyPos -10);
        }
        if(mPrefs.getInteger("PINK_MONKEY") == 2){
            selectFrame.setPosition(20, pinkMonkeyPos -10);
        }
    }

    public void selected(int name){
        switch(name){
            case SELECT_NOMAL_MONKEY:
                if(mPrefs.getInteger("YELLOW_MONKEY", 0) == 2){
                    mPrefs.putInteger("YELLOW_MONKEY", 1);
                }
                if(mPrefs.getInteger("PINK_MONKEY", 0) == 2){
                    mPrefs.putInteger("PINK_MONKEY", 1);
                }
                if(mPrefs.getInteger("BLUE_MONKEY", 0) == 2){
                    mPrefs.putInteger("BLUE_MONKEY", 1);
                }
                break;
            case SELECT_YELLOW_MONKEY:
                if(mPrefs.getInteger("NOMAL_MONKEY", 2) == 2){
                    mPrefs.putInteger("NOMAL_MONKEY", 1);
                }
                if(mPrefs.getInteger("PINK_MONKEY", 0) == 2){
                    mPrefs.putInteger("PINK_MONKEY", 1);
                }
                if(mPrefs.getInteger("BLUE_MONKEY", 0) == 2){
                    mPrefs.putInteger("BLUE_MONKEY", 1);
                }
                break;
            case SELECT_PINK_MONKEY:
                if(mPrefs.getInteger("NOMAL_MONKEY", 2) == 2){
                    mPrefs.putInteger("NOMAL_MONKEY", 1);
                }
                if(mPrefs.getInteger("YELLOW_MONKEY", 0) == 2){
                    mPrefs.putInteger("YELLOW_MONKEY", 1);
                }
                if(mPrefs.getInteger("BLUE_MONKEY", 0) == 2){
                    mPrefs.putInteger("BLUE_MONKEY", 1);
                }
                break;
            case SELECT_BLUE_MONKEY:
                if(mPrefs.getInteger("NOMAL_MONKEY", 2) == 2){
                    mPrefs.putInteger("NOMAL_MONKEY", 1);
                }
                if(mPrefs.getInteger("YELLOW_MONKEY", 0) == 2){
                    mPrefs.putInteger("YELLOW_MONKEY", 1);
                }
                if(mPrefs.getInteger("PINK_MONKEY", 0) == 2){
                    mPrefs.putInteger("PINK_MONKEY", 1);
                }
                break;
        }
        mPrefs.flush();
    }

    public void checkPurchased(){
        if(!(mPrefs.getInteger("YELLOW_MONKEY", 0) == 0)){
            mFont.setColor(Color.GREEN);
            mFont.getData().setScale(0.55f);
            mFont.draw(mGame.batch, "purchased", 165, yellowMonkeyPos + 20);
        }else {
            mFont.setColor(Color.RED);
            mFont.getData().setScale(0.7f);
            mFont.draw(mGame.batch, "$500", 165, yellowMonkeyPos + 20);
        }
        if(!(mPrefs.getInteger("PINK_MONKEY", 0) == 0)){
            mFont.getData().setScale(0.55f);
            mFont.setColor(Color.GREEN);
            mFont.draw(mGame.batch, "purchased", 165, pinkMonkeyPos + 20);
        }else {
            mFont.setColor(Color.RED);
            mFont.getData().setScale(0.7f);
            mFont.draw(mGame.batch, "$500", 165, pinkMonkeyPos + 20);
        }
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
    }
}
