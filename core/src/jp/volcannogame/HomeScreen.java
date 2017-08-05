package jp.volcannogame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import menu.MiniGameCollection;

/**
 * Created by iuchi on 2017/07/11.
 */

public class HomeScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 450;
    static final float CAMERA_HEIGHT = 300;

    Stage stage;
    Sprite mBg;
    BitmapFont mFont;
    Skin skin;

    TextButton startButton;
    TextButton.TextButtonStyle buttonStyle;

    TextureAtlas buttonAtlas;

    MiniGameCollection mGame;

    public HomeScreen(MiniGameCollection game){
        mGame = game;

        Texture bgTexture = new Texture("Shop.png");

        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 450, 300));
        Image BgImage = new Image(mBg);
        BgImage.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        BgImage.setPosition(0, 0);

        mFont = new BitmapFont();
        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);

        buttonAtlas = new TextureAtlas("Button.txt");
        TextureRegion upRegion = buttonAtlas.findRegion("ButtonUp");
        TextureRegion downRegion = buttonAtlas.findRegion("ButtonDown");

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = mFont;
        buttonStyle.up = new TextureRegionDrawable(upRegion);
        buttonStyle.down = new TextureRegionDrawable(downRegion);
        buttonStyle.down.setBottomHeight(-3);

        startButton = new TextButton("GAME START!!", buttonStyle);
        startButton.setSize(CAMERA_WIDTH / 3 * 2, CAMERA_HEIGHT / 5);
        startButton.setPosition(CAMERA_WIDTH / 6 , CAMERA_HEIGHT / 2 - 35);
        startButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle start = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                if(start.contains(x, y)) {
                    System.out.println("ゲームスタート");
                    mGame.setScreen(new GameScreenWithStage(mGame));
                    stage.dispose();
                }
            }
        });
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(CAMERA_WIDTH / 6 , CAMERA_HEIGHT / 2 - 35);
        moveAction.setDuration(.5f);
        startButton.addAction(moveAction);

        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(BgImage);
        stage.addActor(startButton);

        Matrix4 cameraMatrix = stage.getViewport().getCamera().combined;
        mGame.batch.setProjectionMatrix(cameraMatrix);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
    }
}
