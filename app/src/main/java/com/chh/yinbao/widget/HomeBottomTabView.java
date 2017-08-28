package com.chh.yinbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chh.yinbao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by potoyang on 2017/8/9.
 */

public class HomeBottomTabView extends LinearLayout {

    private static final String TAG = HomeBottomTabView.class.getSimpleName();
    public static final int DISCOUNT_INFO_TAB_INDEX = 0;
    public static final int PAYBACK_PLAN_TAB_INDEX = 1;
    public static final int PROFILE_TAB_INDEX = 2;

    private int select_tab_text_color;
    private int default_tab_text_color;


    OnTabItemClickListener mTabItemClickListener;

    private int[] defaultIconIds = new int[]{
            R.mipmap.tab_discount_info_icon_normal,
            R.mipmap.tab_payback_plan_icon_normal,
            R.mipmap.tab_profile_icon_normal
    };

    private int[] checkedIconIds = new int[]{
            R.mipmap.tab_discount_info_icon_pressed,
            R.mipmap.tab_payback_plan_icon_pressed,
            R.mipmap.tab_profile_icon_pressed
    };

    private int currentItem = -1;

    private List<ImageView> imageViewList;
    private List<TextView> textViewList;
    private List<LinearLayout> linearLayoutList;

    public HomeBottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeTabView);
        if (null != typedArray) {
            int n = typedArray.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = typedArray.getIndex(i);
                switch (attr) {
                    case R.styleable.HomeTabView_select_tab_text_color:
                        select_tab_text_color = typedArray.getColor(attr, Color.rgb(138, 138, 138));
                        break;
                    case R.styleable.HomeTabView_default_tab_text_color:
                        default_tab_text_color = typedArray.getColor(attr, Color.rgb(138, 138, 138));
                        break;
                }
            }
            typedArray.recycle();
        }
        init(context);
    }

    public HomeBottomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.home_bottom_tab, this);
        imageViewList = new ArrayList<>();
        textViewList = new ArrayList<>();
        linearLayoutList = new ArrayList<>();
        addTabItemClick(rootView);
    }

    private void addTabItemClick(View rootView) {
        LinearLayout linearLayout = (LinearLayout) ((LinearLayout) rootView).getChildAt(0);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            final int index = i;
            final LinearLayout cLinearLayout = (LinearLayout) linearLayout.getChildAt(i);

            linearLayoutList.add(cLinearLayout);
            imageViewList.add((ImageView) cLinearLayout.getChildAt(0));
            textViewList.add((TextView) cLinearLayout.getChildAt(1));
            cLinearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentItem == index) {
                        return;
                    }
                    currentItem = index;
                    setCheckedViewState(index);
                    itemClick(index);
                }
            });
        }
    }

    public void setCheckedViewState(int index) {
        for (int i = 0; i < imageViewList.size(); i++) {
            LinearLayout linearLayout = linearLayoutList.get(i);
            ImageView imageView = imageViewList.get(i);
            TextView textView = textViewList.get(i);
            linearLayout.setBackgroundResource(R.color.color_white);
            imageView.setImageResource(defaultIconIds[i]);
            textView.setTextColor(default_tab_text_color);
        }
        if (index == -1 || index >= textViewList.size()) {
            index = 0;
        }
        linearLayoutList.get(index).setBackgroundResource(R.color.toolbar_bg);
        imageViewList.get(index).setImageResource(checkedIconIds[index]);
        textViewList.get(index).setTextColor(select_tab_text_color);
        currentItem = index;
    }

    private void itemClick(final int index) {
        if (mTabItemClickListener != null) {
            mTabItemClickListener.onTabItemClick(index);
        }
    }

    public interface OnTabItemClickListener {
        void onTabItemClick(int index);
    }

    public void setmTabItemClickListener(OnTabItemClickListener mTabItemClickListener) {
        this.mTabItemClickListener = mTabItemClickListener;
    }
}
