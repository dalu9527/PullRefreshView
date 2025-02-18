package com.ybao.simple.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.ybao.pullrefreshview.layout.BaseHeaderView;
import com.ybao.simple.R;
import com.ybao.simple.utils.AnimUtil;

/**
 * Created by Ybao on 2015/11/3 0003.
 */
public class NormalHeaderView extends BaseHeaderView {
    TextView textView;
    View tagImg;
    View progress;
    View stateImg;

    public NormalHeaderView(Context context) {
        this(context, null);
    }

    public NormalHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_header_normal, this, true);
        textView = (TextView) findViewById(R.id.text);
        tagImg = findViewById(R.id.tag);
        progress = findViewById(R.id.progress);
        stateImg = findViewById(R.id.state);
    }

    @Override
    protected void onStateChange(int state) {
        if (textView == null || tagImg == null || progress == null || stateImg == null) {
            return;
        }
        stateImg.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        tagImg.setVisibility(View.VISIBLE);
        ViewHelper.setAlpha(textView, 1);
        ViewHelper.setAlpha(tagImg, 1);
        ViewHelper.setTranslationY(stateImg, 0);
        ViewHelper.setTranslationY(progress, 0);
        switch (state) {
            case NONE:
                break;
            case PULLING:
                textView.setText("下拉刷新");
                AnimUtil.startRotation(tagImg, 0);
                break;
            case LOOSENT_O_REFRESH:
                textView.setText("松开刷新");
                AnimUtil.startRotation(tagImg, 180);
                break;
            case REFRESHING:
                textView.setText("正在刷新");
                AnimUtil.startShow(progress, 0.1f, 400, 200);
                AnimUtil.startHide(textView);
                AnimUtil.startHide(tagImg);
                break;
            case REFRESH_CLONE:
                AnimUtil.startFromY(stateImg, -2 * stateImg.getHeight());
                AnimUtil.startToY(progress, 2 * progress.getHeight());
                stateImg.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                tagImg.setVisibility(View.INVISIBLE);
                textView.setText("刷新完成");
                break;

        }

    }

    @Override
    public int getSpanHeight() {
        return getHeight();
    }
}
