package jp.MonckeyClimb;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static jp.MonckeyClimb.PlayScreen.CAMERA_HEIGHT;
import static jp.MonckeyClimb.PlayScreen.CAMERA_WIDTH;
import static jp.MonckeyClimb.PlayScreen.MONKEY_Y;
import static jp.MonckeyClimb.PlayScreen.mCamera;
import static jp.MonckeyClimb.PlayScreen.stopCount;

/**
 * Created by iuchi on 2017/07/17.
 */

public class CreateStageUpdate {

    public static final float space = CAMERA_HEIGHT / 10;

    List<TreeMaterial> mTreeMaterial = new ArrayList<TreeMaterial>();
    List<TreePattern> mTreePattern = new ArrayList<TreePattern>();
    List<Tree> mTrees = new ArrayList<Tree>();
    //List<Tree> mTreesShadow = new ArrayList<Tree>();;
    List<Tree> mTreesLine = new ArrayList<Tree>();
    List<Coin> mCoin = new ArrayList<Coin>();
    List<Log> mLogsLeft = new ArrayList<Log>();
    List<Log> mLogsRight = new ArrayList<Log>();
    List<Bikkuri> mBikkurisLeft = new ArrayList<Bikkuri>();
    List<Bikkuri> mBikkurisRight = new ArrayList<Bikkuri>();

    int coinCount = 0;
    int treecount = 0;
    int treecount2 = 0;
    //int treecountShadow = 0;
    //int treecountShadow2 = 0;
    int treePatternCount = 0;
    int treePatternCount2 = 0;
    int treeMaterialCount = 0;
    int treeMaterialCount2 = 0;
    int treecountLine = 0;
    int treecountLine2 = 0;
    int logCount = 0;

    float coinRange = CAMERA_WIDTH / 10 * 7 - CAMERA_WIDTH / 10 * 3 + Tree.TREE_WIDTH / 2;

    Random random = new Random();

    Texture treeMaterial1LeftTexture = new Texture("treeMaterial4.png");
    Texture treeMaterial1RightTexture = new Texture("treeMaterial4-2.png");
    //Texture treeMaterial1LeftTexture = new Texture("treeMaterial1.png");
    //Texture treeMaterial1RightTexture = new Texture("treeMaterial1-2.png");
    Texture treeMaterial2LeftTexture = new Texture("treeMaterial2-2.png");
    Texture treeMaterial2RightTexture = new Texture("treeMaterial2.png");
    Texture treeMaterial3LeftTexture = new Texture("treeleafMaterial1.png");
    Texture treeMaterial3RightTexture = new Texture("treeleafMaterial2.png");
    Texture treeMaterial4LeftTexture = new Texture("treeleafMaterial4.png");
    Texture treeMaterial4RightTexture = new Texture("treeleafMaterial3.png");
    Texture treePatternTexture = new Texture("treePattern.png");
    Texture treeTextureline = new Texture("treeMaterial0line.png");
    Texture treeTexture = new Texture("treeMaterial0.png");
    //Texture treeTextureShadow = new Texture("treeMaterial0shadow.png");
    Texture coinTexture = new Texture("coin2.png");
    Texture logTextureLeft = new Texture("log2.png");
    Texture logTextureRight = new Texture("log.png");
    Texture bikkuriRTexture = new Texture("bikkuriRight.png");
    Texture bikkuriLTexture = new Texture("bikkuriLeft.png");

    public void Create(){
        logCount++;

        //登るのをやめた場合1秒でコインの湧き停止
        stopCount++;

        //木
        if(mCamera.position.y + CAMERA_HEIGHT / 2 + space > treecount * Tree.TREE_HEIGHT){
            treecount = tree(CAMERA_WIDTH / 10 * 3, treecount);
        }
        if(mCamera.position.y + CAMERA_HEIGHT / 2 + space > treecount2 * Tree.TREE_HEIGHT){
            treecount2 = tree(CAMERA_WIDTH / 10 * 7, treecount2);
        }

        //木の陰
        /*if (mCamera.position.y + CAMERA_HEIGHT >= treecountShadow * Tree.TREE_HEIGHT){
            treecountShadow = treeShadow(CAMERA_WIDTH / 10 * 3 + CAMERA_WIDTH / 100, treecountShadow);
        }

        if (mCamera.position.y + CAMERA_HEIGHT >= treecountShadow2 * Tree.TREE_HEIGHT){
            treecountShadow2 = treeShadow(CAMERA_WIDTH / 10 * 7 + CAMERA_WIDTH / 100, treecountShadow2);
        }*/

        //木の模様
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treePatternCount * TreePattern.TREE_HEIGHT){
            treePatternCount = treePatterndraw(CAMERA_WIDTH / 10 * 3, treePatternCount);
        }
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treePatternCount2 * TreePattern.TREE_HEIGHT){
            treePatternCount2 = treePatterndraw(CAMERA_WIDTH / 10 * 7, treePatternCount2);
        }

        //木の糸
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treecountLine * Tree.TREE_HEIGHT){
            treecountLine = treeLine(CAMERA_WIDTH / 10 * 3, treecountLine);
        }
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treecountLine2 * Tree.TREE_HEIGHT){
            treecountLine2 = treeLine(CAMERA_WIDTH / 10 * 7, treecountLine2);
        }

        //木の部品
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treeMaterialCount * 100){
            treeMaterialCount = treeMaterialdraw(CAMERA_WIDTH / 10 * 3, treeMaterialCount);
        }
        if (mCamera.position.y + CAMERA_HEIGHT / 2 + space > treeMaterialCount2 * 100){
            treeMaterialCount2 = treeMaterialdraw(CAMERA_WIDTH / 10 * 7, treeMaterialCount2);
        }

        //コイン
        if (stopCount < 60){
            coinCount++;
            coinCreate();
        }

        logdraw();
    }

     public void Createfirst(){
        //木の陰
        /*for (int i = 0; i < Math.round(CAMERA_HEIGHT / Tree.TREE_HEIGHT) + 1; i++){
            treecountShadow = treeShadow(CAMERA_WIDTH / 10 * 3 + CAMERA_WIDTH / 100, treecountShadow);
        }
        for (int i = 0; i < Math.round(CAMERA_HEIGHT / Tree.TREE_HEIGHT) + 1; i++){
            treecountShadow2 = treeShadow(CAMERA_WIDTH / 10 * 7 + CAMERA_WIDTH / 100, treecountShadow2);
        }*/

        for (int i = 0; i < Math.round(CAMERA_HEIGHT / Tree.TREE_HEIGHT) + 1; i++){
            treecount = tree(CAMERA_WIDTH / 10 * 3, treecount);
        }

        for (int i = 0; i < Math.round(CAMERA_HEIGHT / Tree.TREE_HEIGHT) + 1; i++){
            treecount2 = tree(CAMERA_WIDTH / 10 * 7, treecount2);
        }

        for (int i = 0; i < Math.round(CAMERA_HEIGHT / TreePattern.TREE_HEIGHT) + 1; i++) {
            treePatternCount =treePatterndraw(CAMERA_WIDTH / 10 * 3, treePatternCount);
        }
        for (int i = 0; i < Math.round(CAMERA_HEIGHT / TreePattern.TREE_HEIGHT) + 1; i++) {
            treePatternCount2 = treePatterndraw(CAMERA_WIDTH / 10 * 7, treePatternCount2);
        }

        for (int i = 0; i < Math.round(CAMERA_HEIGHT / 100) + 1; i++){
            treeMaterialCount = treeMaterialdraw(CAMERA_WIDTH / 10 * 3, treeMaterialCount);
        }
        for (int i = 0; i < Math.round(CAMERA_HEIGHT / 100) + 1; i++){
            treeMaterialCount2 = treeMaterialdraw(CAMERA_WIDTH / 10 * 7, treeMaterialCount2);
        }
    }

    private int treeMaterialdraw(float position, int count) {
        int ntm = random.nextInt(8);
        switch (ntm){
            case 0:
                //TreeMaterial treeMaterial0 = new TreeMaterial(treeMaterial1LeftTexture, 0, 0, 433, 500);
                //treeMaterial0.setSize(433 / 16, 500 / 16);
                TreeMaterial treeMaterial0 = new TreeMaterial(treeMaterial1LeftTexture, 0, 0, 461, 616);
                treeMaterial0.setSize(461 / 14, 616/ 14);
                treeMaterial0.setPosition(position - Tree.TREE_WIDTH / 2 - treeMaterial0.getWidth(), count * 100);
                mTreeMaterial.add(treeMaterial0);
                break;
            case 1:
                //TreeMaterial treeMaterial1 = new TreeMaterial(treeMaterial1RightTexture, 0, 0, 433, 500);
                //treeMaterial1.setSize(433 / 16, 500 / 16);
                TreeMaterial treeMaterial1 = new TreeMaterial(treeMaterial1RightTexture, 0, 0, 461, 616);
                treeMaterial1.setSize(461 / 14, 616 / 14);
                treeMaterial1.setPosition(position + Tree.TREE_WIDTH / 2, count * 100);
                mTreeMaterial.add(treeMaterial1);
                break;
            case 2:
                TreeMaterial treeMaterial2 = new TreeMaterial(treeMaterial2LeftTexture, 0, 0, 321, 500);
                treeMaterial2.setSize(321 / 9.5f, 500 / 9.5f);
                treeMaterial2.setPosition(position - Tree.TREE_WIDTH / 2 - treeMaterial2.getWidth(), count * 100);
                mTreeMaterial.add(treeMaterial2);
                break;
            case 3:
                TreeMaterial treeMaterial3 = new TreeMaterial(treeMaterial2RightTexture, 0, 0, 321, 500);
                treeMaterial3.setSize(321 / 9.5f, 500 / 9.5f);
                treeMaterial3.setPosition(position + Tree.TREE_WIDTH / 2, count * 100);
                mTreeMaterial.add(treeMaterial3);
                break;
            case 4:
                TreeMaterial treeMaterial4 = new TreeMaterial(treeMaterial3LeftTexture, 0, 0, 498, 355);
                treeMaterial4.setSize(498 / 6, 355 / 6);
                treeMaterial4.setPosition(position - Tree.TREE_WIDTH / 2 - treeMaterial4.getWidth() / 2, count * 100);
                mTreeMaterial.add(treeMaterial4);
                break;
            case 5:
                TreeMaterial treeMaterial5 = new TreeMaterial(treeMaterial3RightTexture, 0, 0, 498, 355);
                treeMaterial5.setSize(498 / 6, 355 / 6);
                treeMaterial5.setPosition(position + Tree.TREE_WIDTH / 2 - treeMaterial5.getWidth() / 2, count * 100);
                mTreeMaterial.add(treeMaterial5);
                break;
            case 6:
                TreeMaterial treeMaterial6 = new TreeMaterial(treeMaterial4LeftTexture, 0, 0, 500, 427);
                treeMaterial6.setSize(500 / 6, 427 / 6);
                treeMaterial6.setPosition(position - Tree.TREE_WIDTH / 2 - treeMaterial6.getWidth() / 2, count * 100);
                mTreeMaterial.add(treeMaterial6);
                break;
            case 7:
                TreeMaterial treeMaterial7 = new TreeMaterial(treeMaterial4RightTexture, 0, 0, 500, 427);
                treeMaterial7.setSize(500 / 6, 427 / 6);
                treeMaterial7.setPosition(position + Tree.TREE_WIDTH / 2 - treeMaterial7.getWidth() / 2, count * 100);
                mTreeMaterial.add(treeMaterial7);
                break;
        }
        count++;
        return count;
    }

    private int treePatterndraw(float position, int count){
        int ntp = random.nextInt(4);
        switch (ntp){
            case 0:
                TreePattern treePattern0 = new TreePattern(treePatternTexture, 0, 0, 400, 400);
                treePattern0.setPosition(position - TreePattern.TREE_WIDTH / 2, count * TreePattern.TREE_HEIGHT);
                mTreePattern.add(treePattern0);
                break;
            case 1:
                TreePattern treePattern1 = new TreePattern(treePatternTexture, 400, 0, 400, 400);
                treePattern1.setPosition(position - TreePattern.TREE_WIDTH / 2, count * TreePattern.TREE_HEIGHT);
                mTreePattern.add(treePattern1);
                break;
            case 2:
                TreePattern treePattern2 = new TreePattern(treePatternTexture, 0, 400, 400, 400);
                treePattern2.setPosition(position - TreePattern.TREE_WIDTH / 2, count * TreePattern.TREE_HEIGHT);
                mTreePattern.add(treePattern2);
                break;
            case 3:
                TreePattern treePattern3 = new TreePattern(treePatternTexture, 400, 400, 400, 400);
                treePattern3.setPosition(position - TreePattern.TREE_WIDTH / 2, count * TreePattern.TREE_HEIGHT);
                mTreePattern.add(treePattern3);
                break;
        }
        count++;
        return count;
    }

    private int tree(float position, int count){
        Tree tree = new Tree(treeTexture, 0, 0, 400, 1600);
        tree.setPosition(position - Tree.TREE_WIDTH / 2, count * Tree.TREE_HEIGHT);
        mTrees.add(tree);
        count++;
        return count;
    }

    /*
    private int treeShadow(float position, int count){
        Tree treeshadow = new Tree(treeTextureShadow, 0, 0, 400, 1600);
        treeshadow.setPosition(position - Tree.TREE_WIDTH / 2, count * Tree.TREE_HEIGHT);
        mTreesShadow.add(treeshadow);
        count++;
        return count;
    }*/

    private int treeLine(float position, int count){
        Tree treeLine = new Tree(treeTextureline, 0, 0, 400, 1600);
        treeLine.setPosition(position - Tree.TREE_WIDTH / 2, count * Tree.TREE_HEIGHT);
        mTreesLine.add(treeLine);
        count++;
        return count;
    }

    private void coinCreate(){
        if (coinCount == 60){
            float nc = random.nextInt((int) coinRange);
            Coin coin = new Coin(coinTexture, 0, 0, 25, 25);
            coin.setPosition(CAMERA_WIDTH / 10 * 3 - Tree.TREE_WIDTH / 2 + nc, mCamera.position.y + CAMERA_HEIGHT / 2 + space);
            mCoin.add(coin);
            coinCount = 0;
        }
    }

    private void logdraw(){
        if(logCount == 60){
            int n = random.nextInt(2);
            int n2 = random.nextInt(383 + 200) + 1;
            float n3 = random.nextInt(200) + 100;
            if (n == 0){
                Log log = new Log(logTextureLeft, 0, 0, 30, 12);
                log.setPosition(0 - Log.LOG_WIDTH - CAMERA_WIDTH / 2, MONKEY_Y + Monkey.MONKEY_HEIGHT + n2);
                Bikkuri bikkuri = new Bikkuri(bikkuriLTexture, 0, 0, 62, 49);
                bikkuri.setPosition(0, log.getY() + Log.LOG_HEIGHT / 2 - Bikkuri.BIKKURI_HEIGHT / 2);
                log.accelaccel = n3;
                mLogsLeft.add(log);
                mBikkurisLeft.add(bikkuri);
            } else if (n == 1){
                Log log = new Log(logTextureRight , 0, 0, 30, 12);
                log.setPosition(CAMERA_WIDTH + Log.LOG_WIDTH + CAMERA_WIDTH / 2, MONKEY_Y + Monkey.MONKEY_HEIGHT + n2);
                Bikkuri bikkuri = new Bikkuri(bikkuriRTexture, 0, 0, 62, 49);
                bikkuri.setPosition(CAMERA_WIDTH - Bikkuri.BIKKURI_WIDTH, log.getY() + Log.LOG_HEIGHT / 2 - Bikkuri.BIKKURI_HEIGHT / 2);
                log.accelaccel = -n3;
                mLogsRight.add(log);
                mBikkurisRight.add(bikkuri);
            }
            logCount = 0;
        }
    }
}
