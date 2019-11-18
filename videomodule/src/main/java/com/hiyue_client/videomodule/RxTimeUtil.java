package com.hiyue_client.videomodule;

import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

public class RxTimeUtil {

    public static void time(final TextView tvCountDown){
        Flowable.intervalRange(0, 11, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "倒计时");

                        tvCountDown.setText("倒计时 " + String.valueOf(10 - aLong) + " 秒");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "倒计时完毕");

                    }
                })
                .subscribe();
    }
}
