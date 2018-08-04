package com.http.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.http.rx.RxSchedulersHelper;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class DefaultHttpUiShow {

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity,final String msg) {


        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        final HttpDialogUtils httpDialogUtils = new HttpDialogUtils();


        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .compose(RxSchedulersHelper.<T>inMainCommand())
                        .doOnSubscribe(new Consumer<Disposable>() { // 在订阅时执行
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        httpDialogUtils.showProgress(activityWeakReference.get(),msg);
                    }
                }).doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Activity context;
                        if ((context = activityWeakReference.get()) != null
                                && !context.isFinishing()) {
                            httpDialogUtils.dismissProgress();
                        }
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity) {
        return applyProgressBar(activity, "");
    }
}
