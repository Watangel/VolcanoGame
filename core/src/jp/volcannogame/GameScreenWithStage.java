package jp.volcannogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import menu.MiniGameCollection;

/**
 * Created by iuchi on 2017/07/14.
 */

public class GameScreenWithStage extends ScreenAdapter {
    private MiniGameCollection mGame;

    static final float CAMERA_WIDTH = 450;
    static final float CAMERA_HEIGHT = 300;

    static final int GAME_STATE_READY = 0;
    static final int GAME_STATE_PLAYING = 1;
    static final int GAME_STATE_GAMEOVER = 2;
    static final int GAME_STATE_STOP = 3;

    static final float GRAVITY = -510;

    static final int VOLCANO_STATE_STATIC = 0;
    static final int VOLCANO_STATE_ERUPTION = 1;
    static final int VOLCANO_STATE_COOL = 2;

    int mGameState;
    int volcanoState;

    Sprite mBg;
    Sprite bgWhite;
    Sprite mVoltexture;

    //一時停止ボタン
    Button stopButton;

    //火山弾
    Texture bombTexture;
    VolcanoBomb mBomb;
    List<VolcanoBomb> Bombs;
    int bombNumber = 7;
    int Bombcooltime = 60;
    Random bomb;
    Random Reruption;

    //コイン
    Texture coinTexture;
    Coin coin;
    List<Coin> coins;
    int coinNumber = 3;
    Random coinPos;
    Random coinReruption;
    int coinCount;

    //溶岩
    Texture lavaTexture;
    Lava lava;
    int lavaCooltime = 4;
    float lavacount;
    LavaFire lavaFire;

    //爆発
    Texture bombFireTexture;
    BombFire mbombFire;
    List<BombFire> bombFires;

    //HPゲージ
    Sprite mbgHPBar, mHPBar;
    float hpfirstWidth;
    int downHP = 7;

    //プレイヤー
    Player mPlayer;
    float accel;

    //タッチ
    Vector3 mTouchPoint;

    //スコア
    BitmapFont mFont;
    int mScore;
    int mHighScore;
    Preferences mPrefs;
    Preferences coinPrefs;

    //ステージ
    Stage stage;
    Stage stopStage;

    public GameScreenWithStage(MiniGameCollection game){
        mGame = game;

        //背景
        Texture bgTexture = new Texture("BackGroundSky.png");
        Texture white = new Texture("white.png");

        //火山
        Texture volcanoTexture = new Texture("Volcano.png");
        bombTexture = new Texture("VolcanoBomb.png");
        bombFireTexture = new Texture("BombFire.png");

        //コイン
        coinTexture = new Texture("Coin.png");

        //HPバー
        Texture hpBarTexture = new Texture("HPゲージ.png");
        Texture hpTexture = new Texture("HP.png");
        mbgHPBar = new Sprite(hpBarTexture, 0, 0, 476, 37);
        mbgHPBar.setSize(CAMERA_WIDTH / 10 * 7, CAMERA_HEIGHT / 10);
        mbgHPBar.setPosition(CAMERA_WIDTH / 20 * 1, CAMERA_HEIGHT / 6 * 5);
        mHPBar = new Sprite(hpTexture, 0, 0, 476, 37);
        hpfirstWidth = mHPBar.getRegionWidth();
        mHPBar.setSize(CAMERA_WIDTH / 10 * 7, CAMERA_HEIGHT / 10);
        mHPBar.setPosition(CAMERA_WIDTH / 20 * 1, CAMERA_HEIGHT / 6 * 5);

        //火山
        mVoltexture = new Sprite(new TextureRegion(volcanoTexture, 0, 0, 450, 300));
        mVoltexture.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mVoltexture.setPosition(0, 0);
        mBg = new Sprite(new TextureRegion(bgTexture, 0, 0, 450, 300));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);
        bgWhite = new Sprite(new TextureRegion(white, 0, 0, 450, 300));
        bgWhite.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        bgWhite.setPosition(0, 0);
        bgWhite.setAlpha(0.2f);

        mGameState = GAME_STATE_READY;
        mTouchPoint = new Vector3();

        volcanoState = VOLCANO_STATE_STATIC;
        Bombs = new ArrayList<VolcanoBomb>();
        Reruption = new Random();
        bomb = new Random();
        bombFires = new ArrayList<BombFire>();

        coins = new ArrayList<Coin>();
        coinReruption = new Random();
        coinPos = new Random();

        lavaTexture = new Texture("Lava.png");
        lava = new Lava(lavaTexture, 0, 0, 450, 70);
        lava.setSize(CAMERA_WIDTH, CAMERA_HEIGHT / 5);
        lava.setPosition(0, 0);
        Texture lavaFireTexture = new Texture("LavaFire.png");
        lavaFire = new LavaFire(lavaFireTexture, 0, 0, 50, 100);

        mScore  = 0;
        mHighScore = 0;
        mFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3_0.png" ), false);
        mFont.getData().setScale(0.7f);

        mPrefs = Gdx.app.getPreferences("jp.volcannogame");
        coinPrefs = Gdx.app.getPreferences("jp.MiniGameCollection");
        mHighScore = mPrefs.getInteger("HIGHSCORE", 0);
        coinCount = coinPrefs.getInteger("COIN", 0);

        createStage();

        //一時停止ステージ
        Sprite stopMenuBg = new Sprite(new Texture("Menu.png"), 300, 250);
        Image stopMenuBgImage = new Image(stopMenuBg);
        stopMenuBgImage.setPosition(75,0);

            //スタート
        TextureRegion startUpRegion = new TextureRegion(new Texture("monkeystopcancellbutton.png"), 420, 420);
        TextureRegion startDownRegion = new TextureRegion(new Texture("monkeystopcancellbutton.png"), 420, 420);
        Button.ButtonStyle startButtonStyle = new Button.ButtonStyle();
        startButtonStyle.up = new TextureRegionDrawable(startUpRegion);
        startButtonStyle.down = new TextureRegionDrawable(startDownRegion);
        startButtonStyle.down.setBottomHeight(-3);
        final Button startButton = new Button(startButtonStyle);
        startButton.setSize(50, 50);
        startButton.setPosition(125, 60);
        startButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle startRect = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                if(startRect.contains(x, y)){
                    startButton.setSize(50 * 0.96f, 50 * 0.96f);
                    startButton.setPosition(125 + 50 * 0.02f, 60 + 50 * 0.02f);
                }else {
                    startButton.setSize(50, 50);
                    startButton.setPosition(125, 60);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startButton.setSize(50 * 0.96f, 50 * 0.96f);
                startButton.setPosition(125 + 50 * 0.02f, 60 + 50 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                startButton.setSize(50, 50);
                startButton.setPosition(125, 60);
                Rectangle startRect = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                if(startRect.contains(x, y)){
                    mGameState = GAME_STATE_PLAYING;
                }
            }
        });

            //リスタートボタン
        TextureRegion restartUpRegion = new TextureRegion(new Texture("monkeyretrybutton.png"), 420, 420);
        TextureRegion restartDownRegion = new TextureRegion(new Texture("monkeyretrybutton.png"), 420, 420);
        Button.ButtonStyle restartButtonStyle = new Button.ButtonStyle();
        restartButtonStyle.up = new TextureRegionDrawable(restartUpRegion);
        restartButtonStyle.down = new TextureRegionDrawable(restartDownRegion);
        restartButtonStyle.down.setBottomHeight(-3);
        final Button restartButton = new Button(restartButtonStyle);
        restartButton.setSize(50, 50);
        restartButton.setPosition(275, 60);
        restartButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle startRect = new Rectangle(0, 0, restartButton.getWidth(), restartButton.getHeight());
                if(startRect.contains(x, y)){
                    restartButton.setSize(50 * 0.96f, 50 * 0.96f);
                    restartButton.setPosition(275 + 50 * 0.02f, 60 + 50 * 0.02f);
                }else {
                    restartButton.setSize(50, 50);
                    restartButton.setPosition(275, 60);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                restartButton.setSize(50 * 0.96f, 50 * 0.96f);
                restartButton.setPosition(275 + 50 * 0.02f, 60 + 50 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                restartButton.setSize(50, 50);
                restartButton.setPosition(275, 60);
                Rectangle startRect = new Rectangle(0, 0, restartButton.getWidth(), restartButton.getHeight());
                if(startRect.contains(x, y)){
                    stage.dispose();
                    stopStage.dispose();
                    mGame.setScreen(new GameScreenWithStage(mGame));
                }
            }
        });

            //ホームボタン
        TextureRegion homeUpRegion = new TextureRegion(new Texture("monkeyhomebutton.png"), 420, 420);
        TextureRegion homeDownRegion = new TextureRegion(new Texture("monkeyhomebutton.png"), 420, 420);
        Button.ButtonStyle homeButtonStyle = new Button.ButtonStyle();
        homeButtonStyle.up = new TextureRegionDrawable(homeUpRegion);
        homeButtonStyle.down = new TextureRegionDrawable(homeDownRegion);
        homeButtonStyle.down.setBottomHeight(-3);
        final Button homeButton = new Button(homeButtonStyle);
        homeButton.setSize(50, 50);
        homeButton.setPosition(200, 60);
        homeButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle startRect = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                if(startRect.contains(x, y)){
                    homeButton.setSize(50 * 0.96f, 50 * 0.96f);
                    homeButton.setPosition(200 + 50 * 0.02f, 60 + 50 * 0.02f);
                }else {
                    homeButton.setSize(50, 50);
                    homeButton.setPosition(200, 60);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                homeButton.setSize(50 * 0.96f, 50 * 0.96f);
                homeButton.setPosition(200 + 50 * 0.02f, 60 + 50 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                Rectangle startRect = new Rectangle(0, 0, startButton.getWidth(), startButton.getHeight());
                homeButton.setSize(50, 50);
                homeButton.setPosition(200, 60);
                if(startRect.contains(x, y)){
                    stage.dispose();
                    stopStage.dispose();
                    mGame.setScreen(new menu.TestHomeScreen(mGame));
                }
            }
        });

            //ステージ
        stopStage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        stopStage.addActor(stopMenuBgImage);
        stopStage.addActor(startButton);
        stopStage.addActor(restartButton);
        stopStage.addActor(homeButton);


        //一時停止ボタン
        TextureRegion upRegion = new TextureRegion(new Texture("monkeystopbutton.png"), 420, 420);
        TextureRegion downRegion = new TextureRegion(new Texture("monkeystopbutton.png"), 420, 420);
        Button.ButtonStyle stopButtonStyle = new Button.ButtonStyle();
        stopButtonStyle.up = new TextureRegionDrawable(upRegion);
        stopButtonStyle.down = new TextureRegionDrawable(downRegion);
        stopButtonStyle.down.setBottomHeight(-3);
        stopButton = new Button(stopButtonStyle);
        stopButton.setSize(50, 50);
        stopButton.setPosition(CAMERA_WIDTH - 55, CAMERA_HEIGHT - 55);
        stopButton.addListener(new InputListener(){
            public void touchDragged (InputEvent event, float x, float y, int pointer){
                Rectangle start = new Rectangle(0, 0, stopButton.getWidth(), stopButton.getHeight());
                if(start.contains(x, y)){
                    stopButton.setSize(50 * 0.96f, 50 * 0.96f);
                    stopButton.setPosition(CAMERA_WIDTH - 55 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                }else {
                    stopButton.setSize(50, 50);
                    stopButton.setPosition(CAMERA_WIDTH - 55, CAMERA_HEIGHT - 55);
                }
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                stopButton.setSize(50 * 0.96f, 50 * 0.96f);
                stopButton.setPosition(CAMERA_WIDTH - 55 + 50 * 0.02f, CAMERA_HEIGHT - 55 + 50 * 0.02f);
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stopButton.setSize(50, 50);
                stopButton.setPosition(CAMERA_WIDTH - 55, CAMERA_HEIGHT - 55);
                Rectangle start = new Rectangle(0, 0, stopButton.getWidth(), stopButton.getHeight());
                System.out.println("お呼び出し");
                if(start.contains(x, y)){
                    mGameState = GAME_STATE_STOP;
                }
            }
        });

        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        stage.addListener(new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle left = new Rectangle(0, 0, stage.getWidth() / 2, stage.getHeight());
                Rectangle right = new Rectangle(stage.getWidth() / 2, 0, stage.getWidth() / 2, stage.getHeight());
                if(right.contains(x, y)){
                    System.out.println("右");
                    accel = -5.0f;
                }
                if(left.contains(x, y)){
                    System.out.println("左");
                    accel = 5.0f;
                }
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                accel = 0;
            }

            public void fling(InputEvent event, float velocityX, float velocityY, int button){
                System.out.println(velocityY);
                if(velocityY > 600){
                    mPlayer.jump();
                }
            }
        });

        stage.addActor(stopButton);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!(mGameState == GAME_STATE_STOP)){
            Gdx.input.setInputProcessor(stage);
            Matrix4 cameraMatrix = stage.getViewport().getCamera().combined;
            mGame.batch.setProjectionMatrix(cameraMatrix);
            stage.act();
            stage.getViewport().getCamera().update();
        }

        if (mGameState == GAME_STATE_STOP) {
            Gdx.input.setInputProcessor(stopStage);
            Matrix4 cameraMatrix = stopStage.getViewport().getCamera().combined;
            mGame.batch.setProjectionMatrix(cameraMatrix);
            stopStage.act();
            stopStage.getViewport().getCamera().update();
        }
        update(delta);


        mGame.batch.begin();
        mBg.draw(mGame.batch);
        mVoltexture.draw(mGame.batch);
        bgWhite.draw(mGame.batch);
        mbgHPBar.draw(mGame.batch);
        mHPBar.draw(mGame.batch);
        if(lava.State == lava.LAVA_STATE_COOL){
            lava.draw(mGame.batch);
        }
        lavaFire.draw(mGame.batch);
        mPlayer.draw(mGame.batch);
        if(volcanoState == VOLCANO_STATE_COOL){
            for(int i = 0; i < bombFires.size(); i++){
                bombFires.get(i).draw(mGame.batch);
            }
            for(int i = 0; i < Bombs.size(); i++){
                Bombs.get(i).draw(mGame.batch);
            }
            for(int i = 0; i < coins.size(); i++){
                coins.get(i).draw(mGame.batch);
            }
        }
        mFont.draw(mGame.batch, "HIGHSCORE:" + mHighScore, CAMERA_WIDTH / 20 , CAMERA_HEIGHT / 10 * 8);
        mFont.draw(mGame.batch, "SCORE:" + mScore, CAMERA_WIDTH / 20 , CAMERA_HEIGHT / 10 * 7);
        mFont.draw(mGame.batch, "COIN:" + coinCount, CAMERA_WIDTH / 20, CAMERA_HEIGHT / 10 * 6);
        mGame.batch.end();

        //ステージ描画
        if(!(mGameState == GAME_STATE_STOP)){
            stage.draw();
        }
        if(mGameState == GAME_STATE_STOP){
            stopStage.draw();
        }
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
        stopStage.getViewport().update(width, height);
    }

    private void update(float delta){
        //呼び出し
        switch (mGameState){
            case GAME_STATE_READY:
                updateReady();
                break;
            case GAME_STATE_PLAYING:
                mScore ++;
                System.out.println(coinCount);
                updatePlaying(delta);
                if(mScore > mHighScore){
                    mHighScore = mScore;
                    mPrefs.putInteger("HIGHSCORE", mHighScore);
                    mPrefs.flush();
                }
                break;
            case GAME_STATE_GAMEOVER:
                updateGameOver();
                break;
        }
    }

    private void createStage(){
        Texture ossanTexture = new Texture("player1.png");
        Texture akumaTexture = new Texture("player2.png");
        Texture mahoutsukaiTexture = new Texture("player3.png");

        if(mPrefs.getInteger("OSSAN", 2) == 2){
            mPlayer = new Player(ossanTexture, 0, 0, 19, 36);
            mPlayer.setPosition(CAMERA_WIDTH / 2 - mPlayer.getWidth() / 2, 0);
        }
        if(mPrefs.getInteger("AKUMA", 0) == 2){
            mPlayer = new Player(akumaTexture, 0, 0, 19, 36);
            mPlayer.setPosition(CAMERA_WIDTH / 2 - mPlayer.getWidth() / 2, 0);
            mPlayer.PLAYER_MAX_HP = 150;
            mPlayer.PLAYER_HP = 150;
        }
        if(mPrefs.getInteger("MAHOUTSUKAI", 0) == 2){
            mPlayer = new Player(mahoutsukaiTexture, 0, 0, 19, 36);
            mPlayer.setPosition(CAMERA_WIDTH / 2 - mPlayer.getWidth() / 2, 0);
            downHP = 4;
        }
    }

    private void updateReady(){
        if(Gdx.input.justTouched()){
            mGameState = GAME_STATE_PLAYING;
        }
    }

    private void updatePlaying(float delta){
        if(volcanoState == VOLCANO_STATE_STATIC){
            if(Reruption.nextInt(Bombcooltime) == 1){
                volcanoState = VOLCANO_STATE_ERUPTION;
                if(lava.State == lava.LAVA_STATE_READY){
                    if(Reruption.nextInt(lavaCooltime) == 1){
                        lava.State = lava.LAVA_STATE_RUN;
                    }
                }
            }
        }
        if(lava.State == lava.LAVA_STATE_RUN){
            lavacount = 0;
            lava.setAlpha(0);
            lava.State = lava.LAVA_STATE_COOL;
        }
        if(lava.State == lava.LAVA_STATE_COOL){
            lavacount ++;
            if(lavacount % 2 == 0){
                lava.setAlpha(lavacount / 2 * 0.01f);
            }
            if(lavacount > 180){
                checkHitLava();
            }
            if(lavacount > 190){
                lava.setAlpha(1 - lavacount * 0.02f);
            }
            if(lavacount == 200){
                lava.State = lava.LAVA_STATE_READY;
            }
        }

        if(volcanoState == VOLCANO_STATE_ERUPTION){
            createBomb();
            createCoin();
            volcanoState = VOLCANO_STATE_COOL;
        }
        if(volcanoState == VOLCANO_STATE_COOL){
            //火山弾
            for(int i = 0; i < Bombs.size(); i++){
                Bombs.get(i).update(delta);
            }
            checkHitBomb();

            //コイン
            for(int i = 0; i < coins.size(); i++){
                coins.get(i).update(delta);
            }
            checkHitCoin();


            if(Bombs.get(0).getY() < -1 * CAMERA_HEIGHT && coins.get(0).getY() < -1 * CAMERA_HEIGHT){
                Bombs.clear();
                coins.clear();
                volcanoState = VOLCANO_STATE_STATIC;
            }
        }

        for(int i = 0; i < bombFires.size(); i++){
            bombFires.get(i).setAlpha(1 - 0.1f * bombFires.get(i).count);
            bombFires.get(i).count++;
            if(bombFires.get(i).count > 10){
                bombFires.remove(bombFires.get(i));
            }
        }

        mPlayer.update(delta, accel);

        if(mPlayer.PLAYER_HP < 0){
            mGameState = GAME_STATE_GAMEOVER;
        }

        mHPBar.setRegionWidth((int)(hpfirstWidth / mPlayer.PLAYER_MAX_HP * mPlayer.PLAYER_HP));
        mHPBar.setSize(CAMERA_WIDTH / 10 * 7 / mPlayer.PLAYER_MAX_HP * mPlayer.PLAYER_HP, CAMERA_HEIGHT / 10);
    }

    private void updateGameOver(){
        stage.dispose();
        mGame.setScreen(new ResultScreen(mGame, mScore));
    }

    private void checkHitLava(){
        if(mPlayer.getBoundingRectangle().overlaps(lava.getBoundingRectangle())){
            lava.State = lava.LAVA_STATE_READY;
            mPlayer.PLAYER_HP -= downHP * 2;
            lavaFire.setPosition(mPlayer.getX(), mPlayer.getY());
            lavaFire.mState = lavaFire.LAVAFIRE_STATE_DRAWABLE;

        }
    }

    private void checkHitBomb(){
        for(int i = 0; i < Bombs.size(); i++){
            if(mPlayer.getBoundingRectangle().overlaps(Bombs.get(i).getBoundingRectangle())){
                if(Bombs.get(i).hit_State == Bombs.get(i).BOMB_HIT_FALSE){
                    Bombs.get(i).hit_State = Bombs.get(i).BOMB_HIT_TRUE;
                    mPlayer.PLAYER_HP -= downHP;

                    Bombs.get(i).Hit();
                    mbombFire = new BombFire(bombFireTexture, 0, 0, 32, 32);
                    mbombFire.setPosition(Bombs.get(i).getX() - Bombs.get(i).getWidth(), Bombs.get(i).getY());
                    mbombFire.setSize(CAMERA_WIDTH / 8 , CAMERA_HEIGHT / 6);
                    bombFires.add(mbombFire);
                }
            }
        }
    }

    private void checkHitCoin(){
        for(int i = 0; i < coins.size(); i++){
            if(mPlayer.getBoundingRectangle().overlaps(coins.get(i).getBoundingRectangle())){
                if(coins.get(i).hitState == coins.get(i).COIN_GET_FALSE){
                    coins.get(i).hitState = coins.get(i).COIN_GET_TRUE;
                    mScore += 100;
                    coinCount ++;
                    coinPrefs.putInteger("COIN", coinCount);
                    coinPrefs.flush();
                    coins.get(i).get();
                }
            }
        }
    }

    public void createBomb(){
        //火山弾
        for(int i = 0; i < bombNumber; i++){
            mBomb = new VolcanoBomb(bombTexture, 0, 0, 32, 32);
            mBomb.setPosition(bomb.nextFloat() * CAMERA_WIDTH - mBomb.getWidth() / 2, (float)(1 + (bomb.nextInt(10) + 1) * 0.1) * CAMERA_HEIGHT);
            Bombs.add(mBomb);
        }
    }

    public void createCoin(){
        for(int i = 0; i < coinNumber; i++){
            coin = new Coin(coinTexture, 0, 0, 32, 32);
            coin.setPosition(coinPos.nextFloat() * CAMERA_WIDTH - coin.getWidth() / 2, (float)(1 + (coinPos.nextInt(10) + 1) * 0.1) * CAMERA_HEIGHT);
            coins.add(coin);
        }
    }
}
