package jp.MonckeyClimb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import menu.MiniGameCollection;

/**
 * Created by iuchi on 2017/06/30.
 */

public class StartScreen extends ScreenAdapter {

  //  boolean check = false;
    MonkeyClimbStartButton MCSB;
    private MiniGameCollection originGame;
    Sprite sprite;
    Vector2 TouchPoint;
    static final float CAMERA_WIDTH = 600;
    static final float CAMERA_HEIGHT = 900;
    OrthographicCamera mCamera;
    FitViewport mViewPort;
    BitmapFont mFont;
    Game game;

    public StartScreen(MiniGameCollection mOriginGame) {
        originGame = mOriginGame;

        Texture backGround = new Texture("backgroundstart.png");
        sprite = new Sprite(new TextureRegion(backGround, 0, 0, 600, 900));
        sprite.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        sprite.setPosition(0, 0);

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        TouchPoint = new Vector2();
        mFont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font_0.png"), false);
        mFont.setColor(Color.GRAY);
        mFont.getData().setScale(0.5f);

        createScreen();
    }


    @Override
    public void render (float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


/*        if(check){
            game.setScreen(new PlayScreen(originGame));
        }
*/
        mCamera.update();
        originGame.batch.setProjectionMatrix(mCamera.combined);

        originGame.batch.begin();

        sprite.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        sprite.draw(originGame.batch);
        mFont.draw(originGame.batch, "version 1.0.0", 15, CAMERA_HEIGHT - 15);//原点左下
        MCSB.draw(originGame.batch);

        originGame.batch.end();

        touchevent(delta);

    }

    private void touchevent(float delta){
        if(Gdx.input.justTouched()){
            mViewPort.unproject(TouchPoint.set(Gdx.input.getX(), Gdx.input.getY()));
            Rectangle monkeygame = new Rectangle(CAMERA_WIDTH / 2 - 92.5f, CAMERA_HEIGHT / 2 - 49, 185, 98);
            if(monkeygame.contains(TouchPoint.x, TouchPoint.y)){
                originGame.setScreen(new PlayScreen(originGame));
            }
        }
    }

    @Override
    public void resize(int width, int height){
        mViewPort.update(width, height);
    }

    private void createScreen() {
        Texture MonkeyStart = new Texture("monkeyclimbstart.png");
        TextureRegion monmon = new TextureRegion(MonkeyStart, 0, 0, 740, 392);
        MCSB = new MonkeyClimbStartButton(monmon, 0, 0, 740, 392);
        MCSB.setPosition(CAMERA_WIDTH / 2 - 185 / 2, CAMERA_HEIGHT / 2 - 49);
    }
}
