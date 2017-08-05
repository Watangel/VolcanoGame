package menu;

import com.badlogic.gdx.Gdx;
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

import jp.MonckeyClimb.MonkeyShopScreen;
import jp.volcannogame.GameScreenWithStage;
import jp.volcannogame.ShopScreen;

/**
 * Created by iuchi on 2017/08/01.
 */

public class SelectShop extends ScreenAdapter {
    static final float CAMERA_WIDTH = 300;
    static final float CAMERA_HEIGHT = 450;

    MiniGameCollection mGame;

    //背景
    Sprite bgColor;
    Sprite bg;

    //ステージ
    Stage stage;

    //フォント
    BitmapFont mFont;

    //ボタン
    Button volcanoShop;
    Button monkeyShop;
    Button backButton;

    public SelectShop(MiniGameCollection game){
        mGame = game;

        //フォント
        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        mFont.setColor(Color.GOLD);

        //背景
        bgColor = new Sprite(new TextureRegion(new Texture("shop_bg_color.png"), 600, 900));
        bgColor.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        bg = new Sprite(new TextureRegion(new Texture("shop_bg.png"), 600, 900));
        bg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);

        //ボタン
        TextureRegion volcanoRegion = new TextureRegion(new Texture("volcano_select.png"), 600, 100);
        TextureRegion monkeyRegion = new TextureRegion(new Texture("monkey_select.png"), 600, 100);
        Button.ButtonStyle volcanoButtonStyle = new Button.ButtonStyle();
        volcanoButtonStyle.up = new TextureRegionDrawable(volcanoRegion);
        volcanoButtonStyle.down = new TextureRegionDrawable(volcanoRegion);
        Button.ButtonStyle monkeyButtonStyle = new Button.ButtonStyle();
        monkeyButtonStyle.up = new TextureRegionDrawable(monkeyRegion);
        monkeyButtonStyle.down = new TextureRegionDrawable(monkeyRegion);
        //火山
        volcanoShop = new Button(volcanoButtonStyle);
        volcanoShop.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
        volcanoShop.setPosition(CAMERA_WIDTH / 10, CAMERA_HEIGHT / 2 + 30);
        volcanoShop.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle volcano = new Rectangle(0, 0, volcanoShop.getWidth(), volcanoShop.getHeight());
                if(volcano.contains(x, y)){
                    volcanoShop.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                    volcanoShop.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 + 30 + CAMERA_HEIGHT / 10 * 0.02f);
                }else {
                    volcanoShop.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                    volcanoShop.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 + 30);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                volcanoShop.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                volcanoShop.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 + 30 + CAMERA_HEIGHT / 10 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle volcano = new Rectangle(0, 0, volcanoShop.getWidth(), volcanoShop.getHeight());
                volcanoShop.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                volcanoShop.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 + 30);
                if(volcano.contains(x, y)) {
                    stage.dispose();
                    mGame.setScreen(new ShopScreen(mGame));
                }
            }
        });
        //モンキー
        monkeyShop = new Button(monkeyButtonStyle);
        monkeyShop.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
        monkeyShop.setPosition(CAMERA_WIDTH / 10, CAMERA_HEIGHT / 2 - 50);
        monkeyShop.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle monkey = new Rectangle(0, 0, monkeyShop.getWidth(), monkeyShop.getHeight());
                if(monkey.contains(x, y)){
                    monkeyShop.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                    monkeyShop.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 - 50 + CAMERA_HEIGHT / 10 * 0.02f);
                }else {
                    monkeyShop.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                    monkeyShop.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 - 50);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                monkeyShop.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                monkeyShop.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 - 50 + CAMERA_HEIGHT / 10 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle monkey = new Rectangle(0, 0, monkeyShop.getWidth(), monkeyShop.getHeight());
                monkeyShop.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                monkeyShop.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 - 50);
                if(monkey.contains(x, y)) {
                    stage.dispose();
                    mGame.setScreen(new MonkeyShopScreen(mGame));
                }
            }
        });

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
                    mGame.setScreen(new TestHomeScreen(mGame));
                }
            }
        });

        //ステージ
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        stage.addActor(volcanoShop);
        stage.addActor(monkeyShop);
        stage.addActor(backButton);
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
        bgColor.draw(mGame.batch);
        bg.draw(mGame.batch);
        mGame.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
    }
}
