package jp.MonckeyClimb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import menu.MiniGameCollection;

/**
 * Created by iuchi on 2017/06/28.
 */

public class GameOverScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 336;
    static final float CAMERA_HEIGHT = 504;

    private MiniGameCollection minioriginGame;
    Sprite Bg, testes;
    OrthographicCamera mCamera;
    FitViewport mViewPort;
    BitmapFont scoreFont, retryFont, highScoreFont;

    //Texture test = new Texture("backgroundstart.png");

    int mScore;

    public GameOverScreen(MiniGameCollection mOriginGame, int score){
        minioriginGame = mOriginGame;
        mScore = score;

        Texture backGround = new Texture("background.png");
        Bg = new Sprite(new TextureRegion(backGround, 0, 0, 336, 504));
        Bg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        Bg.setPosition(0, 0);

        //カメラとビューポート
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        //フォント
        scoreFont = new BitmapFont(Gdx.files.internal("fontscore.fnt"), Gdx.files.internal("fontscore_0.png"), false);
        scoreFont.setColor(Color.GRAY);
        scoreFont.getData().setScale(1);

        /*
        //test
        testes = new Sprite(new TextureRegion(test, 0, 0, 50, 50));
        testes.setSize(64, 20);
        */

        retryFont = new BitmapFont(Gdx.files.internal("fontretry.fnt"), Gdx.files.internal("fontretry_0.png"), false);
        retryFont.setColor(Color.GRAY);
        retryFont.getData().setScale(1);

        //ハイスコア
        highScoreFont = new BitmapFont(Gdx.files.internal("fonthighscore.fnt"), Gdx.files.internal("fonthighscore_0.png"), false);
        highScoreFont.setColor(Color.GRAY);
        highScoreFont.getData().setScale(1);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mCamera.update();
        minioriginGame.batch.setProjectionMatrix(mCamera.combined);

        minioriginGame.batch.begin();
        Bg.draw(minioriginGame.batch);
        scoreFont.draw(minioriginGame.batch, "COIN:" + mScore, 0 , CAMERA_HEIGHT / 2 + 80, CAMERA_WIDTH, Align.center, false);
        /*testes.setPosition(CAMERA_WIDTH / 2 + 3, CAMERA_HEIGHT / 2 + 60);
        testes.draw(minioriginGame.batch);*/
        retryFont.draw(minioriginGame.batch, "Retry?", 0, CAMERA_HEIGHT / 2 - 80, CAMERA_WIDTH, Align.center, false);
        minioriginGame.batch.end();

        if (Gdx.input.justTouched()){
            minioriginGame.setScreen(new PlayScreen(minioriginGame));
        }
    }
}
