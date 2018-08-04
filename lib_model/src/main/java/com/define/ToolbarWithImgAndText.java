package com.define;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duiafudao.lib_model.R;


/**
 * 左为图片,右为文本
 */
public class ToolbarWithImgAndText extends RelativeLayout {

    private TextView mView_tv_item_center;
    private ImageButton view_item_goback;
    private TextView tv_item_right;

    public ToolbarWithImgAndText(Context context) {
        this(context, null);
    }

    public ToolbarWithImgAndText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolbarWithImgAndText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    /*初始化布局*/
    private void initView(final Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.toolbar_layout_other, this);

        view_item_goback = (ImageButton) view.findViewById(R.id.view_item_goback);
        mView_tv_item_center = (TextView) view.findViewById(R.id.view_tv_item_center);
        tv_item_right = (TextView) view.findViewById(R.id.tv_item_right);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToolbarWithImgAndText);
        String titleText = a.getString(R.styleable.ToolbarWithImgAndText_ToolbarWithImgAndText_centerText);
        String rightText = a.getString(R.styleable.ToolbarWithImgAndText_ToolbarWithImgAndText_rightText);
        Drawable leftDrawable = a.getDrawable(R.styleable.ToolbarWithImgAndText_ToolbarWithImgAndText_leftDrawable);

        Log.i("TitleStyleView", "initView: " + titleText);
        a.recycle();
        if (!TextUtils.isEmpty(titleText))
            mView_tv_item_center.setText(titleText);
        if (!TextUtils.isEmpty(rightText))
            tv_item_right.setText(rightText);

        if (leftDrawable != null) {
            setDrawable(context, leftDrawable, view_item_goback);
        }

    }


    public void setDrawable(Context context, Drawable drawable, ImageButton view) {
        view.setPadding(dp2px(context, 10), dp2px(context, 12),
                dp2px(context, 10), dp2px(context, 12));
        view.setImageDrawable(drawable);
    }


    public void setOnLeftListener(OnClickListener listener) {
        view_item_goback.setOnClickListener(listener);
    }


    public void setOnRightListener(OnClickListener listener) {
        tv_item_right.setOnClickListener(listener);
    }


    public void setText(String str) {
        if (TextUtils.isEmpty(str))
            return;
        mView_tv_item_center.setText(str);
    }


    public String getText() {
        return mView_tv_item_center.getText().toString().trim();
    }

    /*设置控件是否可见*/
    public void setRightVisibility(int visibility) {
        tv_item_right.setVisibility(visibility);
    }

    public TextView getTv_item_right() {
        return tv_item_right;
    }

    /*设置控件是否可见*/
    public void setLeftVisibility(int visibility) {
        view_item_goback.setVisibility(visibility);
    }
    private int dp2px(Context context,int def){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,def,context.getResources().getDisplayMetrics());
    }
}
