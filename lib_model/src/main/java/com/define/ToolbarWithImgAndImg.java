package com.define;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duiafudao.lib_model.R;


/**
 * 左右均为图片
 * Created by Administrator on 2016/10/13
 */
public class ToolbarWithImgAndImg extends RelativeLayout {

    private TextView mView_tv_item_center;
    private ImageButton view_item_goback;
    private ImageButton view_tv_wallet_detail;
    private int rightLeftPadding;
    private int rightTopPadding;
    private int rightRightPadding;
    private int rightBottomPadding;


    public ToolbarWithImgAndImg(Context context) {
        this(context, null);
    }

    public ToolbarWithImgAndImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    /*初始化布局*/
    private void initView(final Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.toolbar_layout_image, this);

        view_item_goback = (ImageButton) view.findViewById(R.id.view_item_goback);
        view_tv_wallet_detail = (ImageButton) view.findViewById(R.id.view_tv_wallet_detail);
        mView_tv_item_center = (TextView) view.findViewById(R.id.view_tv_item_center);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToolbarWithImgAndImg);
        String titleText = a.getString(R.styleable.ToolbarWithImgAndImg_ToolbarWithImgAndImg_titleText);
       final Drawable rightDrawable = a.getDrawable(R.styleable.ToolbarWithImgAndImg_ToolbarWithImgAndImg_rightDrawable);
        Drawable leftDrawable = a.getDrawable(R.styleable.ToolbarWithImgAndImg_ToolbarWithImgAndImg_leftDrawable);

        rightLeftPadding = (int) a.getDimension(R.styleable.ToolbarWithImgAndImg_ToolbarWithImgAndImg_right_left, dp2px(context, 10));
        rightTopPadding = (int) a.getDimension(R.styleable.ToolbarWithImgAndImg_ToolbarWithImgAndImg_right_top, dp2px(context, 10));
        rightRightPadding = (int) a.getDimension(R.styleable.ToolbarWithImgAndImg_ToolbarWithImgAndImg_right_right, dp2px(context, 10));
        rightBottomPadding = (int) a.getDimension(R.styleable.ToolbarWithImgAndImg_ToolbarWithImgAndImg_right_bottom, dp2px(context, 10));

        a.recycle();
        if (!TextUtils.isEmpty(titleText))
            mView_tv_item_center.setText(titleText);
        if (rightDrawable != null) {
            view_tv_wallet_detail.setVisibility(VISIBLE);
            view_tv_wallet_detail.setPadding(rightLeftPadding, rightTopPadding, rightRightPadding, rightBottomPadding);
            view_tv_wallet_detail.setImageDrawable(rightDrawable);
        }

        if (leftDrawable != null) {
//            view_item_goback.setImageDrawable(leftDrawable);
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
        view_tv_wallet_detail.setOnClickListener(listener);
    }


    public void setText(String str) {
        if (TextUtils.isEmpty(str))
            return;
        mView_tv_item_center.setText(str);
    }


    public String getText() {
        return mView_tv_item_center.getText().toString().trim();
    }

    public TextView getTextView() {
        return mView_tv_item_center;
    }


    /*设置控件是否可见*/
    public void setRightVisibility(int visibility) {
        view_tv_wallet_detail.setVisibility(visibility);
    }

    /*设置控件是否可见*/
    public void setLeftVisibility(int visibility) {
        view_item_goback.setVisibility(visibility);
    }

    public void setTextColor() {
        mView_tv_item_center.setTextColor(Color.parseColor("#1A2126"));
    }

    private int dp2px(Context context,int def){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,def,context.getResources().getDisplayMetrics());
    }

}
