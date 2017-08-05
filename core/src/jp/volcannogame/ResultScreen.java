package jp.volcannogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import menu.MiniGameCollection;
import menu.TestHomeScreen;

/**
 * Created by iuchi on 2017/07/06.
 */

public class ResultScreen extends ScreenAdapter {
    static final float GUI_WIDTH = 450;
    static final float GUI_HEIGHT = 300;

    private MiniGameCollection mGame;
    Sprite mBg;
    BitmapFont mFont;

    int mScore;
    Stage stage;

    public ResultScreen(MiniGameCollection game, int score){
        mGame = game;
        mScore = score;

        Texture bgTexture = new Texture("ResultBack.png");
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 450, 300));
        mBg.setSize(GUI_WIDTH, GUI_HEIGHT);
        mBg.setPosition(0, 0);

        //ホームボタン
        TextureRegion homeUpRegion = new TextureRegion(new Texture("monkeyhomebutton.png"), 420, 420);
        TextureRegion homeDownRegion = new TextureRegion(new Texture("monkeyhomebutton.png"), 420, 420);
        Button.ButtonStyle homeButtonStyle = new Button.ButtonStyle();
        homeButtonStyle.up = new TextureRegionDrawable(homeUpRegion);
        homeButtonStyle.down = new TextureRegionDrawable(homeDownRegion);
        homeButtonStyle.down.setBottomHeight(-3);
        final Button homeButton = new Button(homeButtonStyle);
        homeButton.setSize(80, 80);
        homeButton.setPosition(120, 60);
        homeButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle startRect = new Rectangle(0, 0, homeButton.getWidth(), homeButton.getHeight());
                if(startRect.contains(x, y)){
                    homeButton.setSize(80 * 0.96f, 80 * 0.96f);
                    homeButton.setPosition(120 + 80 * 0.02f, 60 + 80 * 0.02f);
                }else {
                    homeButton.setSize(80, 80);
                    homeButton.setPosition(120, 60);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                homeButton.setSize(80 * 0.96f, 80 * 0.96f);
                homeButton.setPosition(120 + 80 * 0.02f, 60 + 80 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                Rectangle startRect = new Rectangle(0, 0, homeButton.getWidth(), homeButton.getHeight());
                homeButton.setSize(80, 80);
                homeButton.setPosition(120, 60);
                if(startRect.contains(x, y)){
                    stage.dispose();
                    mGame.setScreen(new menu.TestHomeScreen(mGame));
                }
            }
        });

        //リトライボタン
        TextureRegion restartUpRegion = new TextureRegion(new Texture("monkeyretrybutton.png"), 420, 420);
        TextureRegion restartDownRegion = new TextureRegion(new Texture("monkeyretrybutton.png"), 420, 420);
        Button.ButtonStyle restartButtonStyle = new Button.ButtonStyle();
        restartButtonStyle.up = new TextureRegionDrawable(restartUpRegion);
        restartButtonStyle.down = new TextureRegionDrawable(restartDownRegion);
        final Button retryButton = new Button(restartButtonStyle);
        retryButton.setSize(80, 80);
        retryButton.setPosition(250, 60);
        retryButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle startRect = new Rectangle(0, 0, retryButton.getWidth(), retryButton.getHeight());
                if(startRect.contains(x, y)){
                    retryButton.setSize(80 * 0.96f, 80 * 0.96f);
                    retryButton.setPosition(250 + 80 * 0.02f, 60 + 80 * 0.02f);
                }else {
                    retryButton.setSize(80, 80);
                    retryButton.setPosition(250, 60);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                retryButton.setSize(80 * 0.96f, 80 * 0.96f);
                retryButton.setPosition(250 + 80 * 0.02f, 60 + 80 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                Rectangle retryRect = new Rectangle(0, 0, retryButton.getWidth(), retryButton.getHeight());
                retryButton.setSize(80, 80);
                retryButton.setPosition(250, 60);
                if(retryRect.contains(x, y)){
                    stage.dispose();
                    mGame.setScreen(new GameScreenWithStage(mGame));
                }
            }
        });

        stage = new Stage(new FitViewport(GUI_WIDTH, GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(homeButton);
        stage.addActor(retryButton);
        Matrix4 cameraMatrix = stage.getViewport().getCamera().combined;
        mGame.batch.setProjectionMatrix(cameraMatrix);

        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);
        mFont.getData().setScale(1.5f);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        mGame.batch.begin();
        mBg.draw(mGame.batch);
        mFont.draw(mGame.batch, "SCORE:" + mScore, 0, GUI_HEIGHT / 2 + 40, GUI_WIDTH, Align.center, false);

        mGame.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
    }

}
