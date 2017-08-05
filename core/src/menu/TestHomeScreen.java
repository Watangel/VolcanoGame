package menu;

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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import jp.MonckeyClimb.PlayScreen;
import jp.MonckeyClimb.StartScreen;
import jp.volcannogame.GameScreenWithStage;
import jp.volcannogame.ShopScreen;

/**
 * Created by iuchi on 2017/07/11.
 */

public class TestHomeScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 300;
    static final float CAMERA_HEIGHT = 450;

    Stage stage;
    Sprite mBg;
    BitmapFont mFont;
    Skin skin;

    Button startButton;
    Button shopButton;
    Button.ButtonStyle buttonStyle;

    TextureAtlas buttonAtlas;

    MiniGameCollection mGame;

    public TestHomeScreen(MiniGameCollection game){
        mGame = game;

        Texture bgTexture = new Texture("HomeScreenBg.png");

        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 300, 450));
        Image BgImage = new Image(mBg);
        BgImage.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        BgImage.setPosition(0, 0);

        mFont = new BitmapFont();
        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png"), false);

  /*      buttonAtlas = new TextureAtlas("Button.txt");
        TextureRegion upRegion = buttonAtlas.findRegion("ButtonUp");
        TextureRegion downRegion = buttonAtlas.findRegion("ButtonDown");

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = mFont;
        buttonStyle.up = new TextureRegionDrawable(upRegion);
        buttonStyle.down = new TextureRegionDrawable(downRegion);
  //      buttonStyle.down.setBottomHeight(-3);*/

        TextureRegion startRegion = new TextureRegion(new Texture("GameStartButton.png"), 600, 100);
        TextureRegion shopRegion = new TextureRegion(new Texture("ShopButton.png"), 600, 100);

        Button.ButtonStyle startButtonStyle = new Button.ButtonStyle();
        startButtonStyle.up = new TextureRegionDrawable(startRegion);
        startButtonStyle.down = new TextureRegionDrawable(startRegion);

        Button.ButtonStyle shopButtonStyle = new Button.ButtonStyle();
        shopButtonStyle.up = new TextureRegionDrawable(shopRegion);
        shopButtonStyle.down = new TextureRegionDrawable(shopRegion);

        //スタートボタン
        startButton = new Button(startButtonStyle);
        startButton.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
        startButton.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 - 35);
        startButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle start = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                if(start.contains(x, y)){
                    startButton.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                    startButton.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 - 35 + CAMERA_HEIGHT / 10 * 0.02f);
                }else {
                    startButton.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                    startButton.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 - 35);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                startButton.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                startButton.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 - 35 + CAMERA_HEIGHT / 10 * 0.02f);
                System.out.println("スタートよびだし");
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle start = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                startButton.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                startButton.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 - 35);
                if(start.contains(x, y)) {
                     System.out.println("ゲームスタート");
                    stage.dispose();
                    mGame.setScreen(new SelectGame(mGame));
                }
            }
        });

        //ショップボタン
        shopButton = new Button(shopButtonStyle);
        shopButton.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
        shopButton.setPosition(CAMERA_WIDTH / 10, CAMERA_HEIGHT / 2 - CAMERA_HEIGHT / 10 - 50);
        shopButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle shop = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                if(shop.contains(x, y)){
                    shopButton.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                    shopButton.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 - CAMERA_HEIGHT / 10 - 50 + CAMERA_HEIGHT / 10 * 0.02f);
                }else {
                    shopButton.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                    shopButton.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 - CAMERA_HEIGHT / 10 - 50);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                shopButton.setSize(CAMERA_WIDTH / 5 * 4  * 0.96f, CAMERA_HEIGHT / 10 * 0.96f);
                shopButton.setPosition(CAMERA_WIDTH / 10 + CAMERA_WIDTH / 5 * 4  * 0.02f, CAMERA_HEIGHT / 2 - CAMERA_HEIGHT / 10 - 50 + CAMERA_HEIGHT / 10 * 0.02f);
                System.out.println("ショップよびだし");
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                Rectangle shop = new Rectangle(0, 0, shopButton.getWidth(), shopButton.getHeight());
                shopButton.setSize(CAMERA_WIDTH / 5 * 4, CAMERA_HEIGHT / 10);
                shopButton.setPosition(CAMERA_WIDTH / 10 , CAMERA_HEIGHT / 2 - CAMERA_HEIGHT / 10 - 50);
                if(shop.contains(x, y)){
                    System.out.println("ショップ");
                    stage.dispose();
                    mGame.setScreen(new SelectShop(mGame));
    //                stage.dispose();
                }
            }
        });
/*
        MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        moveAction.setPosition(CAMERA_WIDTH / 6 , CAMERA_HEIGHT / 2 - 35);
        moveAction.setDuration(.5f);
        startButton.addAction(moveAction);
*/
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(BgImage);
        stage.addActor(startButton);
        stage.addActor(shopButton);

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
