package com.http.rx;


import android.app.Activity;

import com.http.common.bean.BaseResultEntity;
import com.http.dialog.DefaultHttpUiShow;
import com.http.exception.ExceptionCont;
import com.http.exception.ResponseThrowable;
import com.http.exception.SessionException;
import com.util.rxcommand.RxUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class RxResultHelper {

    private static <T> ObservableTransformer<BaseResultEntity<T>, T> handleResult() {
        return new ObservableTransformer<BaseResultEntity<T>, T>() {
            @Override
            public Observable<T> apply(Observable<BaseResultEntity<T>> tObservable) {
                return tObservable.flatMap(new Function<BaseResultEntity<T>, ObservableSource<T>>() {

                    @Override
                    public ObservableSource<T> apply(BaseResultEntity<T> resultEntity) {

                        if (resultEntity.isOk()) {
                            //请求成功
                            return Observable.just(resultEntity.resultMap);

                        } else if (ExceptionCont.SIGN_OUT.equals(resultEntity.code)) {
                            //添加重登录的处理，需要和服务器协商
                            return Observable.error(new SessionException(resultEntity.description,resultEntity.code));

                        } else {

                            ResponseThrowable mResponseThrowable = new ResponseThrowable(resultEntity.description,resultEntity.code);
                            return Observable.error(mResponseThrowable);
                        }

                    }
                });
            }
        };
    }

    /**
     *
     * @param activity
     * @param observable
     * @param listener
     * @param <R>
     *  处理请求进行转化,返回http结果
     */
    public static <R> void getHttpRepose(final Activity activity, final Observable<BaseResultEntity<R>> observable,
                                         final DataCallBackListener<R> listener) {
        observable.compose(RxSchedulersHelper.<BaseResultEntity<R>>inMainCommand())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        DefaultHttpUiShow.applyProgressBar(activity);
                    }
                })
                .compose(RxUtils.<BaseResultEntity<R>>bindToLifecycle(activity))
                .compose(RxSchedulersHelper.<BaseResultEntity<R>>inMainCommand())
                .compose(RxResultHelper.<R>handleResult())
                .subscribe(new RxResultSubscriber<R>() {
                    @Override
                    public void _onNext(R testResponse) {
                        if (null != listener){
                            listener.onSuccess(testResponse);
                        }
                    }

                    @Override
                    public void _onError(String code,String msg) {
                        if (null != listener){
                            listener.onError(code,msg);
                        }
                    }
                });
    }


}
