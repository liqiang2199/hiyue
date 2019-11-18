package com.lib.network.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import kotlin.Unit;
import io.reactivex.functions.Function;

/**
 * Rx help class
 */
public class RxUtils {

    /**
     * 点击监听 防止用户重复点击
     * @param viewClick
     */
    public static void view_Click(View viewClick, final RxClickOnNext rxClickOnNext){

        RxView.clicks(viewClick).throttleFirst(1,TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object unit) {
                        if (rxClickOnNext != null){
                            rxClickOnNext.clickOnNext(unit);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void edit_CombineLatest(EditText text, final Button but){
        Observable<CharSequence> observable = RxTextView.textChanges(text).skip(1);
        /*
         * 步骤3：通过combineLatest（）合并事件 & 联合判断
         **/
        Observable.combineLatest(observable,observable,observable,
                new Function3<CharSequence, CharSequence, CharSequence,Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2, @NonNull CharSequence charSequence3) throws Exception {

                return true;
            }

        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean s) throws Exception {
                /*
                 * 步骤6：返回结果 & 设置按钮可点击样式
                 **/
            }
        });

    }
}


