package com.example.Tab5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab3 extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tab3, container, false);
        return layout;
    }
}
/*
public class Tab3 extends Fragment implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    private int mMineCount = 0;
    private int mTick = -1;
    private TextView mMineCounter;
    private TextView mTimerView;
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;
    private View mRootView;
    private static int mCellSize=80;
    private float mMineRatio = 0.2f;

    private static Tab3 sTab3;

    private enum GAME_MODE {NOT_STARTED, GAMING, ENDED};
    private GAME_MODE mGameMode = GAME_MODE.NOT_STARTED;

    private static boolean mInitialized = false;
    public enum DIFFICULTY { EASY, MEDIUM, HARD };
    public enum CELL_SIZE { SMALL, MEDIUM, LARGE };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        sTab3 = this;

        Button btn;
        btn = rootView.findViewById(R.id.btn);
        btn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void OnCLick(View view) {

        Fragment fg;
        switch (view.getId()) {
            case R.id.btn:
                fg = ChildOneFragment.newInstance();
                setChildFragment(fg);
                break;
        }
    }

    private void setChildFragment(Fragment child) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.container, new PlaceholderFragment()).commit();
    }

    public void setMarkerMode(View v) {
        Drawable d;
        MineButton.BombMode bombMode = MineButton.toggleBombMode();
        if (bombMode == MineButton.BombMode.MARK)
            d = getApplication().getResources().getDrawable(R.drawable.marker);
        else
            d = getApplication().getResources().getDrawable(R.drawable.bomb);

        ((ImageButton)v).setImageDrawable(d);
    }

    public void openSetup(View v) {

        DIFFICULTY df = mMineRatio <= 0.15f ? DIFFICULTY.EASY : mMineRatio <= 0.20f ? DIFFICULTY.MEDIUM : DIFFICULTY.HARD;
        CELL_SIZE sz = mCellSize <= 70 ? CELL_SIZE.SMALL : mCellSize <= 80 ? CELL_SIZE.MEDIUM : CELL_SIZE.LARGE;
        SetupDialog dlg = new SetupDialog(this, df, sz);
        dlg.setTitle(v.getResources().getString(R.string.setup));
        dlg.setOwnerActivity(this);
        dlg.show();

    }
*/

    /** Called when the user clicks the Send button */
/*
    public void startNewGame(View view) {
        mGameMode = GAME_MODE.GAMING;

        // Do something in response to button
        if (mTimer != null) mTimer.cancel();
        if (mTimerTask != null) mTimerTask.cancel();

        MineButton.initAllMines(mMineCount);

        mTick = 0;

        startTimer();

        setMineCounter(mMineCount);
        setTimer();
    }

    private void startTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        mTick++;
                        mTimerView.setText(String.format("%03d", mTick));
                    }
                });
            }

        };
        mTimer.scheduleAtFixedRate (mTimerTask, 1000, 1000);
    }

    public void stopGame() {
        stopTimer();

        mGameMode = GAME_MODE.ENDED;

        if (MineButton.getFoundCount() == mMineCount) {  // found all
            AlertDialog.Builder ad = new AlertDialog.Builder(mRootView.getContext());
            ad.setTitle(mRootView.getContext().getResources().getString(R.string.result_title));
            ad.setPositiveButton("OK", null);
            ad.setCancelable(false);
            ad.setMessage(String.format("%d " + mRootView.getContext().getResources().getString(R.string.sec), mTick));
            ad.create().show();
        }

    }

    private void setTimer() {
        mTimerView.setText(String.format("%03d", mTick));
    }

    private void setMineCounter(int count) {
        mMineCounter.setText(String.format("%03d", count));
    }

    private void stopTimer() {
        if (mTimerTask != null) mTimerTask.cancel();
        mTimer.cancel();
        mTimer.purge();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopTimer();
        mInitialized = false;
    }


    @Override
    protected void onPause() {

        super.onPause();
        stopTimer();
    }


    @Override
    protected void onResume() {

        super.onResume();
        if (mTick >= 0)
            startTimer();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }


    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    public void onClick(View v) {

        if (mGameMode == GAME_MODE.NOT_STARTED)  // if not started, start new game
            startNewGame(v);

        if (mGameMode != GAME_MODE.GAMING) // if not gaming, just ignore
            return;

        MineButton btn = (MineButton)v;
        boolean bRet = btn.clickMine();  // MineButton class handles click

        setMineCounter(mMineCount - btn.getMarkedCount()); // display remaining mines count
        if (!bRet)   // if game ends with success or failure, stop the game
            stopGame();

    }

    public void setup(DIFFICULTY df, CELL_SIZE sz) {

        mCellSize = sz == CELL_SIZE.SMALL ? 70 : sz == CELL_SIZE.MEDIUM ? 80 : 90;
        mMineRatio = (df == DIFFICULTY.EASY ? 0.15f : df == DIFFICULTY.MEDIUM ? 0.20f : 0.25f);

        initGame();

    }
}
*/