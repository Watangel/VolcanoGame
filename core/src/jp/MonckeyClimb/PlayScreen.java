package jp.MonckeyClimb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import menu.MiniGameCollection;
import menu.TestHomeScreen;

/**
 * Created by iuchi on 2017/06/28.
 */

public class PlayScreen extends ScreenAdapter {
    static final float CAMERA_WIDTH = 336;
    static final float CAMERA_HEIGHT = 504;

    static final float NOMOVECAMERA_WIDTH = 336;
    static final float NOMOVECAMERA_HEIGHT = 504;

    static final float BUTTONBACKGROUND_WIDTH = NOMOVECAMERA_WIDTH / 4 * 3;
    static final float BUTTONBACKGROUND_HEIGHT = NOMOVECAMERA_WIDTH / 4 * 2;

    static final float BACKGROUND_STATUS_HEIGHT = NOMOVECAMERA_HEIGHT / 10;

    static final int GAME_STATE_PLAYING = 1;
    static final int GAME_STATE_GAMEOVER = 2;
    static final int GAME_STATE_STOP = 3;
    static final int GAME_STATE_RETRY = 4;
    static final int GAME_STATE_BACKHOME = 5;

    //ボタンのID
    static final int ID_Retry = 1;
    static final int ID_Home = 2;

    static final int STOPBUTTON_NOPUSH = 1;
    static final int STOPBUTTON_PUSHED = 2;
    static final int STOPBUTTON_WAIT = 3;

    static final int STOPCANCELLBUTTON_NOPUSH = 1;
    static final int STOPCANCELLBUTTON_PUSHED = 2;
    static final int STOPCANCELLBUTTON_WAIT = 3;

    static final float STOPBUTTON_X = NOMOVECAMERA_WIDTH - StopButton.STOPBUTTON_WIDTH - BACKGROUND_STATUS_HEIGHT / 10;
    static final float STOPBUTTON_Y = NOMOVECAMERA_HEIGHT - BACKGROUND_STATUS_HEIGHT / 2 - StopButton.STOPBUTTON_HEIGHT / 2;
    static final float STOPBUTTON_SCALE = 0.5f;

    //static final float RETRYBUTTON_X = NOMOVECAMERA_WIDTH - StopButton.STOPBUTTON_WIDTH * 2 - BACKGROUND_STATUS_HEIGHT / 10 * 2;
    //リトライボタンのx座標, y座標
    static final float RETRYBUTTON_X = NOMOVECAMERA_WIDTH / 2 - BUTTONBACKGROUND_WIDTH / 2 + BUTTONBACKGROUND_WIDTH / 3 - StopButton.STOPBUTTON_WIDTH / 2;
    static final float RETRYBUTTON_Y = NOMOVECAMERA_HEIGHT / 2 - BUTTONBACKGROUND_HEIGHT / 2 + BUTTONBACKGROUND_HEIGHT / 2 - StopButton.STOPBUTTON_HEIGHT / 2;

    //ホームボタンのx座標, y座標
    static final float HOMEBUTTON_X = NOMOVECAMERA_WIDTH / 2 - BUTTONBACKGROUND_WIDTH / 2 + BUTTONBACKGROUND_WIDTH / 3 * 2 - StopButton.STOPBUTTON_WIDTH / 2;
    static final float HOMEBUTTON_Y = NOMOVECAMERA_HEIGHT / 2 - BUTTONBACKGROUND_HEIGHT / 2 + BUTTONBACKGROUND_HEIGHT / 2 - StopButton.STOPBUTTON_HEIGHT / 2;

    ButtonVerSize BVS;

    //font.pngより実測
    static final float FONT_HEIGHT = 20;
    static final float FONT_SCALE = 0.5f;

    public static float MONKEY_X;
    public static float MONKEY_Y;

    int GAME_STATE;
    int STOPBUTTON_STATE;
    int STOPCANCELLBUTTON_STATE;
    int RETRYBUTTON_STATE;
    int HOMEBUTTON_STATE;
    int HP;
    int monkeyCount;
    int stopbuttoncount;
    int stopbuttonsizecount;
    int homebuttoncount;
    int homebuttonsizecount;
    int retrybuttoncount;
    int retrybuttonsizecount;
    int fadeOutCount;
    int Score;
    float high;
    float firstMonkeyY;
    int inthigh;
    Preferences mPrefs;
    Preferences coinPrefs;
    public static int highScore;
    static int stopCount;
    boolean checkRetry;
    boolean checkHome;
    boolean checkretrybutton;
    boolean checkhomebutton;
    boolean choiceGameStop;
    boolean retryOn;
    boolean homeOn;

    Vector2 mTouchPoint;
    private MiniGameCollection minioriginGame;
    Sprite mBg, mBgStatus, mFadeOut, mBgButton;
    public static OrthographicCamera mCamera, mNoMoveCamera;

    FitViewport mViewPort, mNoMoveViewPort;

    Random random;

    CreateStageUpdate csu;

    List<Heart> mHearts;
    List<Log> mLogsFall;
    Monkey monkey;
    Monkey monkeyRight;
    Monkey monkeyLeft;
    Rectangle recMonkey;
    Rectangle recMonkeyRight;
    Rectangle recMonkeyLeft;
    Rectangle recCoin;
    BitmapFont highFont;
    BitmapFont scoreFont;
    BitmapFont highScoreFont;

    Texture stopButtonTexture = new Texture("monkeystopbutton.png");
    Texture pussedButtonTexture = new Texture("monkeystopcancellbutton.png");
    Texture retryButtonTexture = new Texture("monkeyretrybutton.png");
    Texture homeButtonTexture = new Texture("monkeyhomebutton.png");
    Texture heartTexture = new Texture("heart3.png");
    Texture backGround = new Texture("background.png");
    Texture backGroundStatus = new Texture("backgroundstatus.png");
    Texture fadeOut = new Texture("bgblack.png");
    Texture buttonBackGround = new Texture("buttonbackground.png");

    StopButton stopButton = new StopButton(stopButtonTexture, 0, 0, 420, 420);
    StopButton stopCancellButton = new StopButton(pussedButtonTexture, 0, 0, 420, 420);
    StopButton retryButton = new StopButton(retryButtonTexture, 0, 0, 420, 420);
    StopButton homeButton = new StopButton(homeButtonTexture, 0, 0, 420, 420);

    //ステージ
    Stage controlStage;

    public PlayScreen(MiniGameCollection mOriginGame){
        minioriginGame = mOriginGame;

        //背景
        mBg = new Sprite(new TextureRegion(backGround, 0, 0, 336, 504));
        mBg.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        mBg.setPosition(0, 0);

        //フェードアウト
        mFadeOut = new Sprite(new TextureRegion(fadeOut, 0, 0, 200, 300));
        mFadeOut.setSize(NOMOVECAMERA_WIDTH, NOMOVECAMERA_HEIGHT);
        mFadeOut.setPosition(0, 0);

        //ボタンを押したときの背景
        mBgButton = new Sprite(new TextureRegion(buttonBackGround, 0, 0, 600, 400));
        mBgButton.setSize(BUTTONBACKGROUND_WIDTH, BUTTONBACKGROUND_HEIGHT);
        mBgButton.setPosition(NOMOVECAMERA_WIDTH / 2 - BUTTONBACKGROUND_WIDTH / 2, NOMOVECAMERA_HEIGHT / 2 - BUTTONBACKGROUND_HEIGHT / 2);

        //ステータスの背景
        mBgStatus = new Sprite(new TextureRegion(backGroundStatus, 0, 0, 1000, 150));
        mBgStatus.setSize(NOMOVECAMERA_WIDTH, BACKGROUND_STATUS_HEIGHT);
        mBgStatus.setPosition(0, NOMOVECAMERA_HEIGHT - BACKGROUND_STATUS_HEIGHT);

        //カメラとビューポート
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mViewPort = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, mCamera);

        //動かないオブジェクト用カメラ
        mNoMoveCamera = new OrthographicCamera();
        mNoMoveCamera.setToOrtho(false, NOMOVECAMERA_WIDTH, NOMOVECAMERA_HEIGHT);
        mNoMoveViewPort = new FitViewport(NOMOVECAMERA_WIDTH, NOMOVECAMERA_HEIGHT, mNoMoveCamera);

        //状態
        GAME_STATE = GAME_STATE_PLAYING;
        STOPBUTTON_STATE = STOPBUTTON_NOPUSH;
        STOPCANCELLBUTTON_STATE = STOPCANCELLBUTTON_WAIT;
        RETRYBUTTON_STATE = STOPBUTTON_NOPUSH;
        HOMEBUTTON_STATE = STOPBUTTON_NOPUSH;

        //高さフォント
        highFont = new BitmapFont(Gdx.files.internal("fontmeter.fnt"), Gdx.files.internal("fontmeter_0.png"), false);
        highFont.setColor(Color.GRAY);
        highFont.getData().setScale(FONT_SCALE);

        //スコアフォント
        scoreFont = new BitmapFont(Gdx.files.internal("fontscore.fnt"), Gdx.files.internal("fontscore_0.png"), false);
        scoreFont.setColor(Color.GRAY);
        scoreFont.getData().setScale(FONT_SCALE);

        //ハイスコア
        highScoreFont = new BitmapFont(Gdx.files.internal("fonthighscore.fnt"), Gdx.files.internal("fonthighscore_0.png"), false);
        highScoreFont.setColor(Color.GRAY);
        highScoreFont.getData().setScale(FONT_SCALE);

        //メンバ変数の初期化
        MONKEY_X = CAMERA_WIDTH / 10 * 3 - Monkey.MONKEY_WIDTH / 2;
        MONKEY_Y = CAMERA_HEIGHT / 6;

        firstMonkeyY = MONKEY_Y;

        high = 0;
        inthigh = 0;

        //ハイスコア
        mPrefs = Gdx.app.getPreferences("MonkeyClimb");
        highScore = mPrefs.getInteger("HIGHSCORE", 0);
        coinPrefs = Gdx.app.getPreferences("jp.MiniGameCollection");

        //ボタンのカウントとボタンのサイズのカウント
        retrybuttoncount = 0;
        retrybuttonsizecount = 0;
        stopbuttoncount = 0;
        stopbuttonsizecount = 0;

        choiceGameStop = false;
        fadeOutCount = 0;
        HP = 3;
        monkeyCount = 0;
        Score = coinPrefs.getInteger("COIN", 0);
        checkRetry = false;
        checkhomebutton = false;
        checkretrybutton = false;
        retryOn = false;
        homeOn = false;


        mHearts = new ArrayList<Heart>();
        mLogsFall = new ArrayList<Log>();
        random = new Random();
        mTouchPoint = new Vector2();

        csu = new CreateStageUpdate();
        BVS = new ButtonVerSize();
        createStage();

        controlStage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
        Gdx.input.setInputProcessor(controlStage);
        controlStage.addListener(new ActorGestureListener(20, 0.4f, 0, 0.15f){
            Rectangle recStopCancellButton = new Rectangle(stopCancellButton.getBoundingRectangle());
            public boolean longPress (Actor actor, float x, float y){
                if (GAME_STATE == GAME_STATE_PLAYING) {
                    if (Monkey.State != Monkey.MONKEY_STATE_LEFT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_MOVE) {
                        Monkey.State = Monkey.MONKEY_STATE_CLIMB;
                        stopCount = 0;
                    }
                }
                return false;
            }
            public void fling(InputEvent event, float velocityX, float velocityY, int button){
                if(GAME_STATE == GAME_STATE_PLAYING){
                    if(velocityX > 400){
                        if (Monkey.State != Monkey.MONKEY_STATE_LEFT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_NOW) {
                            Monkey.State = Monkey.MONKEY_STATE_RIGHT_MOVE;
                            if (recStopCancellButton.contains(mTouchPoint.x, mTouchPoint.y)){
                                Monkey.State = Monkey.MONKEY_STATE_LEFT_NOW;
                            }
                        }
                    }
                    if(velocityX < -400){
                        if (Monkey.State != Monkey.MONKEY_STATE_LEFT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_MOVE && Monkey.State != Monkey.MONKEY_STATE_LEFT_NOW) {
                            Monkey.State = Monkey.MONKEY_STATE_LEFT_MOVE;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void render (float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //ステージ
        controlStage.act();

        //フォントの高さ?
        //System.out.println(highFont.getCapHeight());
        //状態の更新
        update(delta);

        if (MONKEY_Y > mCamera.position.y - CAMERA_HEIGHT / 6 * 2) {
            mCamera.position.y = MONKEY_Y + CAMERA_HEIGHT / 6 * 2;
        }

        mCamera.update();
        minioriginGame.batch.setProjectionMatrix(mCamera.combined);

        minioriginGame.batch.begin();


        //背景
        mBg.setPosition(mCamera.position.x - CAMERA_WIDTH / 2, mCamera.position.y - CAMERA_HEIGHT / 2);
        mBg.draw(minioriginGame.batch);

        //木
        /*for (int i = 0; i < csu.mTreesShadow.size(); i++){
            if (csu.mTreesShadow.get(i).getY() + Tree.TREE_HEIGHT < mCamera.position.y - CAMERA_HEIGHT * 2){
                csu.mTreesShadow.remove(i);
                i--;
            } else {
                csu.mTreesShadow.get(i).draw(minioriginGame.batch);
            }
        }*/
        for (int i = 0; i < csu.mTrees.size(); i++){
            if (csu.mTrees.get(i).getY() + Tree.TREE_HEIGHT < mCamera.position.y - CAMERA_HEIGHT / 2){
                csu.mTrees.remove(i);
                i--;
            } else {
                csu.mTrees.get(i).draw(minioriginGame.batch);
            }
        }


        //木の模様
        for (int i = 0; i < csu.mTreePattern.size(); i++){

            if (csu.mTreePattern.get(i).getY() + TreePattern.TREE_HEIGHT < mCamera.position.y - CAMERA_HEIGHT / 2){
                csu.mTreePattern.remove(i);
                i--;
            } else {
                csu.mTreePattern.get(i).draw(minioriginGame.batch);
            }
        }

        //木の糸
        for (int i = 0; i < csu.mTreesLine.size(); i++){
            if (csu.mTreesLine.get(i).getY() + Tree.TREE_HEIGHT < mCamera.position.y - CAMERA_HEIGHT / 2){
                csu.mTreesLine.remove(i);
                i--;
            } else {
                csu.mTreesLine.get(i).draw(minioriginGame.batch);
            }
        }

        //木の部品
       for (int i = 0; i < csu.mTreeMaterial.size(); i++){
            //getY() + OOOOO はCSUのtreeMaterialdrawの中のsetSizeの最大値
            if (csu.mTreeMaterial.get(i).getY() + 427 / 6 < mCamera.position.y - CAMERA_HEIGHT / 2){
                csu.mTreeMaterial.remove(i);
                i--;
            } else {
                csu.mTreeMaterial.get(i).draw(minioriginGame.batch);
            }
       }

        //Monkey
       if(Monkey.State == Monkey.MONKEY_STATE_CLIMB){
            if(monkeyCount < 10 ){
                monkeyCount++;
                monkeyRight.draw(minioriginGame.batch);
            } else if(monkeyCount >= 10 && monkeyCount < 20){
                monkeyCount++;
                monkeyLeft.draw(minioriginGame.batch);
                if (monkeyCount == 19){
                    monkeyCount = 0;
                }
            }
       }else if(Monkey.State == Monkey.MONKEY_STATE_STOP
               || Monkey.State == Monkey.MONKEY_STATE_LEFT_NOW
               || Monkey.State == Monkey.MONKEY_STATE_RIGHT_NOW
               || Monkey.State == Monkey.MONKEY_STATE_LEFT_MOVE
               || Monkey.State == Monkey.MONKEY_STATE_RIGHT_MOVE){
            monkey.draw(minioriginGame.batch);
       }

       //丸太
        for(int i = 0; i < csu.mLogsLeft.size(); i++){
            if (csu.mLogsLeft.get(i).getX() > CAMERA_WIDTH){
                csu.mLogsLeft.remove(i);
                csu.mBikkurisLeft.remove(i);
                i--;
            } else {
                csu.mLogsLeft.get(i).draw(minioriginGame.batch);
            }
        }

        for (int i = 0; i < csu.mLogsRight.size(); i++){
            if (csu.mLogsRight.get(i).getX() + Log.LOG_WIDTH < 0){
                csu.mLogsRight.remove(i);
                csu.mBikkurisRight.remove(i);
                i--;
            } else {
                csu.mLogsRight.get(i).draw(minioriginGame.batch);
            }
        }

        for (int i = 0; i < mLogsFall.size(); i++){
            if (mLogsFall.get(i).getY() + Log.LOG_HEIGHT < mCamera.position.y - CAMERA_HEIGHT / 2){
                mLogsFall.remove(i);
                i--;
            } else {
                mLogsFall.get(i).draw(minioriginGame.batch);
            }
        }

        //ビックリマーク
        for (int i = 0; i < csu.mBikkurisLeft.size(); i++){
            if (csu.mLogsLeft.get(i).getX() + Log.LOG_WIDTH > 0){
                continue;
            } else {
                csu.mBikkurisLeft.get(i).draw(minioriginGame.batch);
            }
        }

        for (int i = 0; i < csu.mBikkurisRight.size(); i++){
            if (csu.mLogsRight.get(i).getX() < CAMERA_WIDTH){
                continue;
            } else {
                csu.mBikkurisRight.get(i).draw(minioriginGame.batch);
            }
        }

        //コイン
        for (int i = 0; i < csu.mCoin.size(); i++){
            if(csu.mCoin.get(i).getY() + Coin.COIN_HEIGHT < mCamera.position.y - CAMERA_HEIGHT / 2){
                csu.mCoin.remove(i);
                i--;
            } else {
                csu.mCoin.get(i).draw(minioriginGame.batch);
            }
        }

        minioriginGame.batch.end();


        mNoMoveCamera.update();
        minioriginGame.batch.setProjectionMatrix(mNoMoveCamera.combined);
        minioriginGame.batch.begin();

        mBgStatus.draw(minioriginGame.batch);

        //ハート
        for (int i = 0; i < HP; i ++){
            Heart heart = new Heart(heartTexture, 0, 0, 370, 290);
            mHearts.add(heart);
            mHearts.get(i).setPosition(NOMOVECAMERA_WIDTH - StopButton.STOPBUTTON_WIDTH - BACKGROUND_STATUS_HEIGHT / 10 * 2 - Heart.HEART_WIDTH * i - Heart.HEART_WIDTH - 1 * i, NOMOVECAMERA_HEIGHT - BACKGROUND_STATUS_HEIGHT / 2 - Heart.HEART_HEIGHT / 2);
            mHearts.get(i).draw(minioriginGame.batch);
        }

        //一時停止
        BVS.BVS2(STOPBUTTON_X, STOPBUTTON_Y, StopButton.STOPBUTTON_WIDTH, StopButton.STOPBUTTON_HEIGHT, STOPBUTTON_SCALE, stopButton, stopCancellButton, STOPBUTTON_STATE, STOPCANCELLBUTTON_STATE, stopbuttoncount, stopbuttonsizecount, minioriginGame.batch);
        STOPBUTTON_STATE = BVS.getOnButtonState();
        STOPCANCELLBUTTON_STATE = BVS.getOffButtonState();
        stopbuttoncount = BVS.getButtonCount();
        stopbuttonsizecount = BVS.getSizeCount();
        choiceGameStop = BVS.getButtonCheck();


        //設定した座標が左上
        scoreFont.draw(minioriginGame.batch, "COIN:" + Score, 5, NOMOVECAMERA_HEIGHT - BACKGROUND_STATUS_HEIGHT + BACKGROUND_STATUS_HEIGHT / 4 * 3 + FONT_HEIGHT * FONT_SCALE / 2);
        highFont.draw(minioriginGame.batch, "HIGH:" + inthigh + "m", 5, NOMOVECAMERA_HEIGHT - BACKGROUND_STATUS_HEIGHT + BACKGROUND_STATUS_HEIGHT / 4 * 2 + FONT_HEIGHT * FONT_SCALE / 2);
        highScoreFont.draw(minioriginGame.batch, "HIGHSCORE:" + highScore, 5, NOMOVECAMERA_HEIGHT - BACKGROUND_STATUS_HEIGHT + BACKGROUND_STATUS_HEIGHT / 4 + FONT_HEIGHT * FONT_SCALE / 2);

        if (choiceGameStop){

            mBgButton.draw(minioriginGame.batch);

            //ホームボタン
            BVS.BVS1(HOMEBUTTON_X, HOMEBUTTON_Y, StopButton.STOPBUTTON_WIDTH, StopButton.STOPBUTTON_HEIGHT, STOPBUTTON_SCALE, homeButton, HOMEBUTTON_STATE, homebuttoncount, homebuttonsizecount, minioriginGame.batch);
            HOMEBUTTON_STATE = BVS.getButtonState();
            homebuttoncount = BVS.getButtonCount();
            homebuttonsizecount = BVS.getSizeCount();
            checkhomebutton = BVS.getButtonCheck();
            //リトライボタン
            BVS.BVS1(RETRYBUTTON_X, RETRYBUTTON_Y, StopButton.STOPBUTTON_WIDTH, StopButton.STOPBUTTON_HEIGHT, STOPBUTTON_SCALE, retryButton, RETRYBUTTON_STATE, retrybuttoncount, retrybuttonsizecount, minioriginGame.batch);
            RETRYBUTTON_STATE = BVS.getButtonState();
            retrybuttoncount = BVS.getButtonCount();
            retrybuttonsizecount = BVS.getSizeCount();
            checkretrybutton = BVS.getButtonCheck();
            //System.out.println("checkretrybutton" + checkretrybutton);

            //フェードアウト
            if ((checkhomebutton || homeOn) && retryOn == false){
                homeOn = true;
                fadeout(delta, checkHome, homeOn, ID_Home);
            }
            if ((checkretrybutton || retryOn) && homeOn == false){
                retryOn = true;
                fadeout(delta, checkRetry, retryOn, ID_Retry);
                //System.out.println("checkretrybutton2" + checkretrybutton);
                //System.out.println("retryOn" + retryOn);
                //System.out.println(fadeOutCount);
            }
        }

        minioriginGame.batch.end();

        if (GAME_STATE == GAME_STATE_RETRY || GAME_STATE == GAME_STATE_BACKHOME){
            update(delta);
        }

        //System.out.println("COIN" + csu.mCoin.size());
/*      System.out.println("LogRight" + csu.mLogsRight.size());
        System.out.println("LogLeft" + csu.mLogsLeft.size());
        System.out.println("Tree" + csu.mTrees.size());
        System.out.println("BikkuriRight" + csu.mBikkurisRight.size());
        System.out.println("BikkuriLeft" + csu.mBikkurisLeft.size());
        System.out.println("TreeMaterial" + csu.mTreeMaterial.size());*/
        //System.out.println("TreeShadow" + csu.mTreesShadow.size());
        //System.out.println("LogFall" + mLogsFall.size());
        //System.out.println("MONKEY_STATE" + Monkey.State);
    }

    private void fadeout(float delta, boolean check, boolean on, int ID){
        fadeOutCount++;
        mFadeOut.setPosition(0, 0);
        if (fadeOutCount < 50){
            mFadeOut.setAlpha(0.02f * fadeOutCount);
            mFadeOut.draw(minioriginGame.batch);
        }
        if (fadeOutCount > 49 && fadeOutCount < 61){
            mFadeOut.setAlpha(1);
            mFadeOut.draw(minioriginGame.batch);
            if (fadeOutCount == 60){
                check = true;
                on = false;
                fadeOutCount = 0;
                //System.out.println("ペペペペペペペペペペエエエエェェェェェ");
                //System.out.println("ゾババババババババババ" + retryOn);
                //update(delta);
            }
        }
        //ボタンのIDによって代入する
        if (ID == ID_Retry){
            checkRetry = check;
            retryOn = on;
        } else if (ID == ID_Home){
            checkHome = check;
            homeOn = on;
        }
    }

    private void createStage() {

        //Monkeyの配置
        if(mPrefs.getInteger("NOMAL_MONKEY", 2) == 2){
            //テクスチャの読み込み
            Texture monkeyTexture = new Texture("monkey2.png");
            Texture monkeyRightTexture = new Texture("monkey1.png");
            Texture monkeyLeftTexture = new Texture("monkey3.png");
            monkey = new Monkey(monkeyTexture, 0, 0, 81, 74);
            monkeyRight = new Monkey(monkeyRightTexture, 0, 0, 81, 74);
            monkeyLeft = new Monkey(monkeyLeftTexture, 0, 0, 81, 74);
        }else if(mPrefs.getInteger("YELLOW_MONKEY", 0) == 2){
            //テクスチャの読み込み
            Texture monkeyTexture = new Texture("monkey_yellow.png");
            Texture monkeyRightTexture = new Texture("monkey_yellow2.png");
            Texture monkeyLeftTexture = new Texture("monkey_yellow3.png");
            monkey = new Monkey(monkeyTexture, 0, 0, 81, 74);
            monkeyRight = new Monkey(monkeyRightTexture, 0, 0, 81, 74);
            monkeyLeft = new Monkey(monkeyLeftTexture, 0, 0, 81, 74);
        }else if(mPrefs.getInteger("PINK_MONKEY", 0) == 2) {
            //テクスチャの読み込み
            Texture monkeyTexture = new Texture("monkey_pink.png");
            Texture monkeyRightTexture = new Texture("monkey_pink2.png");
            Texture monkeyLeftTexture = new Texture("monkey_pink3.png");
            monkey = new Monkey(monkeyTexture, 0, 0, 81, 74);
            monkeyRight = new Monkey(monkeyRightTexture, 0, 0, 81, 74);
            monkeyLeft = new Monkey(monkeyLeftTexture, 0, 0, 81, 74);
        }else if(mPrefs.getInteger("BLUE_MONKEY", 0) == 2) {
            //テクスチャの読み込み
            Texture monkeyTexture = new Texture("monkey_blue.png");
            Texture monkeyRightTexture = new Texture("monkey_blue2.png");
            Texture monkeyLeftTexture = new Texture("monkey_blue3.png");
            monkey = new Monkey(monkeyTexture, 0, 0, 81, 74);
            monkeyRight = new Monkey(monkeyRightTexture, 0, 0, 81, 74);
            monkeyLeft = new Monkey(monkeyLeftTexture, 0, 0, 81, 74);
        }

        csu.Createfirst();


        monkey.setPosition(MONKEY_X, MONKEY_Y);

    }

    //状態
    private void update(float delta) {
        switch (GAME_STATE){
            case GAME_STATE_PLAYING:
                updatePlaying(delta);
                csu.Create();
                break;
            case GAME_STATE_STOP:
                touchEvent();
                break;
            case GAME_STATE_GAMEOVER:
                updateGameOver();
                break;
            case GAME_STATE_RETRY:
                updateGameRetry();
                break;
            case GAME_STATE_BACKHOME:
                updateGameBackHome();
                break;
        }
    }

    private void updateGameBackHome() {
        if (checkHome){
            minioriginGame.setScreen(new TestHomeScreen(minioriginGame));
        }
    }

    private void updateGameRetry() {
        if (checkRetry){
            minioriginGame.setScreen(new PlayScreen(minioriginGame));
        }
    }

    //タッチイベント
    private void touchEvent(){
        if(Gdx.input.isTouched()) {
            mNoMoveViewPort.unproject(mTouchPoint.set(Gdx.input.getX(), Gdx.input.getY()));
            Rectangle left = new Rectangle(0, 0, NOMOVECAMERA_WIDTH / 3, NOMOVECAMERA_HEIGHT);
            Rectangle right = new Rectangle(NOMOVECAMERA_WIDTH / 3 * 2, 0, NOMOVECAMERA_WIDTH / 3, NOMOVECAMERA_HEIGHT);
            Rectangle climb = new Rectangle(NOMOVECAMERA_WIDTH / 3, 0, NOMOVECAMERA_WIDTH / 3, NOMOVECAMERA_HEIGHT);
            Rectangle recRetryButton = new Rectangle(retryButton.getBoundingRectangle());
            Rectangle recStopButton = new Rectangle(stopButton.getBoundingRectangle());
            Rectangle recHomeButton = new Rectangle(homeButton.getBoundingRectangle());
            Rectangle recStopCancellButton = new Rectangle(stopCancellButton.getBoundingRectangle());
            if (STOPBUTTON_STATE == STOPBUTTON_NOPUSH){
                if (recStopButton.contains(mTouchPoint.x, mTouchPoint.y)){
                    STOPBUTTON_STATE = STOPBUTTON_PUSHED;
                    GAME_STATE =  GAME_STATE_STOP;
                }
            }
            if (STOPCANCELLBUTTON_STATE == STOPCANCELLBUTTON_NOPUSH){
                if (recStopCancellButton.contains(mTouchPoint.x, mTouchPoint.y)){
                    STOPCANCELLBUTTON_STATE = STOPCANCELLBUTTON_PUSHED;
                    GAME_STATE = GAME_STATE_PLAYING;
                }
            }
            if (choiceGameStop){
                if (RETRYBUTTON_STATE == STOPBUTTON_NOPUSH){
                    if (recRetryButton.contains(mTouchPoint.x, mTouchPoint.y)){
                        RETRYBUTTON_STATE = STOPBUTTON_PUSHED;
                        GAME_STATE = GAME_STATE_RETRY;
                    }
                }
                if (HOMEBUTTON_STATE == STOPBUTTON_NOPUSH){
                    if (recHomeButton.contains(mTouchPoint.x, mTouchPoint.y)){
                        HOMEBUTTON_STATE = STOPBUTTON_PUSHED;
                        GAME_STATE = GAME_STATE_BACKHOME;
                    }
                }
            }
     /*       if (GAME_STATE == GAME_STATE_PLAYING){
                if (left.contains(mTouchPoint.x, mTouchPoint.y)) {
                    if (Monkey.State != Monkey.MONKEY_STATE_LEFT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_MOVE && Monkey.State != Monkey.MONKEY_STATE_LEFT_NOW) {
                        Monkey.State = Monkey.MONKEY_STATE_LEFT_MOVE;
                    }
                }
                if (right.contains(mTouchPoint.x, mTouchPoint.y)) {
                    if (Monkey.State != Monkey.MONKEY_STATE_LEFT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_NOW) {
                        Monkey.State = Monkey.MONKEY_STATE_RIGHT_MOVE;
                        if (recStopCancellButton.contains(mTouchPoint.x, mTouchPoint.y)){
                            Monkey.State = Monkey.MONKEY_STATE_LEFT_NOW;
                        }
                    }
                }
                if (climb.contains(mTouchPoint.x, mTouchPoint.y)) {
                    if (Monkey.State != Monkey.MONKEY_STATE_LEFT_MOVE && Monkey.State != Monkey.MONKEY_STATE_RIGHT_MOVE) {
                        Monkey.State = Monkey.MONKEY_STATE_CLIMB;
                        stopCount = 0;
                    }
                }
            }*/

        }
    }

    //GAME_STATE_PLAYINGの場合のアップデート
    private void updatePlaying(float delta){
        touchEvent();

        if (high > highScore){
            highScore = inthigh;
            mPrefs.putInteger("HIGHSCORE", highScore);
            mPrefs.flush();
        }

        //monkeyRight.update(delta);
        //monkeyLeft.update(delta);
        //monkey.update(delta);
        monkey.update(delta);

        if (MONKEY_X < CAMERA_WIDTH / 10 * 3 - Monkey.MONKEY_WIDTH / 2) {
            MONKEY_X = CAMERA_WIDTH / 10 * 3 - Monkey.MONKEY_WIDTH / 2;
            Monkey.State = Monkey.MONKEY_STATE_LEFT_NOW;
        }
        if (MONKEY_X > CAMERA_WIDTH / 10 * 7 - Monkey.MONKEY_WIDTH / 2) {
            MONKEY_X = CAMERA_WIDTH / 10 * 7 - Monkey.MONKEY_WIDTH / 2;
            Monkey.State = Monkey.MONKEY_STATE_RIGHT_NOW;
        }

        if (Gdx.input.isTouched() != true
                && Monkey.State != Monkey.MONKEY_STATE_LEFT_MOVE
                && Monkey.State != Monkey.MONKEY_STATE_RIGHT_MOVE
                && Monkey.State == Monkey.MONKEY_STATE_CLIMB
                /*&& (Monkey.State == Monkey.MONKEY_STATE_LEFT_NOW
                    || Monkey.State == Monkey.MONKEY_STATE_RIGHT_NOW)*/) {
            Monkey.State = Monkey.MONKEY_STATE_STOP;
            //monkey.update(delta);
        }

        for (int i = 0; i < csu.mLogsLeft.size(); i++){
            csu.mLogsLeft.get(i).update(delta);
        }

        for (int i = 0; i < csu.mLogsRight.size(); i++){
            csu.mLogsRight.get(i).update(delta);
        }

        for (int i = 0; i < mLogsFall.size(); i++){
            mLogsFall.get(i).updatefall(delta);
        }

        for (int i = 0; i < csu.mCoin.size(); i++){
            csu.mCoin.get(i).updatefall(delta);
        }

        if (HP == 0){
            updateGameOver();
        }

        high = MONKEY_Y / Monkey.MONKEY_HEIGHT - firstMonkeyY / Monkey.MONKEY_HEIGHT;
        inthigh = Math.round(high);

        checkCollision();

        monkey.setPosition(MONKEY_X, MONKEY_Y);
        monkeyRight.setPosition(MONKEY_X, MONKEY_Y);
        monkeyLeft.setPosition(MONKEY_X, MONKEY_Y);
    }

    private void updateGameOver() {
        if (high > highScore){
            highScore = inthigh;
            mPrefs.putInteger("HIGHSCORE", highScore);
            mPrefs.flush();
        }
        minioriginGame.setScreen(new GameOverScreen(minioriginGame, Score));
    }

    private void checkCollision() {
        recMonkey = monkey.getBoundingRectangle().setSize(monkey.getWidth() * 0.4f, monkey.getHeight() * 0.9f);
        recMonkey.setX(MONKEY_X + Monkey.MONKEY_WIDTH * 0.5f / 2);
        recMonkeyRight = monkeyRight.getBoundingRectangle().setSize(monkeyRight.getWidth() * 0.45f, monkeyRight.getHeight());
        recMonkeyRight.setX(MONKEY_X + Monkey.MONKEY_WIDTH * 0.5f / 2);
        recMonkeyLeft = monkeyLeft.getBoundingRectangle().setSize(monkeyLeft.getWidth() * 0.45f, monkeyLeft.getHeight());
        recMonkeyLeft.setX(MONKEY_X + Monkey.MONKEY_WIDTH * 0.5f/ 2);
        for (int i = 0; i < csu.mLogsLeft.size(); i++){
            //あたり判定の精度
            if(recMonkey.overlaps(csu.mLogsLeft.get(i).getBoundingRectangle())
                    || recMonkeyRight.overlaps(csu.mLogsLeft.get(i).getBoundingRectangle())
                    || recMonkeyLeft.overlaps(csu.mLogsLeft.get(i).getBoundingRectangle())){
                mLogsFall.add(csu.mLogsLeft.get(i));
                csu.mLogsLeft.remove(i);
                csu.mBikkurisLeft.remove(i);
                HP -= 1;
                i--;
            }
        }

        for (int i = 0; i < csu.mLogsRight.size(); i++){
            //あたり判定の精度
            if(recMonkey.overlaps(csu.mLogsRight.get(i).getBoundingRectangle())
                    || recMonkeyRight.overlaps(csu.mLogsRight.get(i).getBoundingRectangle())
                    || recMonkeyLeft.overlaps(csu.mLogsRight.get(i).getBoundingRectangle())){
                mLogsFall.add(csu.mLogsRight.get(i));
                csu.mLogsRight.remove(i);
                csu.mBikkurisRight.remove(i);
                HP -= 1;
                i--;
            }
        }

        for (int i = 0; i < csu.mCoin.size(); i++){
            recCoin = csu.mCoin.get(i).getBoundingRectangle().setSize(csu.mCoin.get(i).getWidth() * 0.8f, csu.mCoin.get(i).getHeight() * 0.8f);
            if (recMonkey.overlaps(recCoin)
                    || recMonkeyRight.overlaps(recCoin)
                    || recMonkeyLeft.overlaps(recCoin)){
                csu.mCoin.remove(i);
                i--;
                Score += 1;
                coinPrefs.putInteger("COIN", Score);
                coinPrefs.flush();
            }
        }
    }

    @Override
    public void resize(int width, int height){
        mViewPort.update(width, height);
        mNoMoveViewPort.update(width, height);
    }
}
