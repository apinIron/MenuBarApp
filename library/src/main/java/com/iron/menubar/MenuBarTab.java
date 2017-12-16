package com.iron.menubar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author iron
 *         created at 2017/11/9
 */
public class MenuBarTab extends FrameLayout {

    private ImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvUnreadCount;
    private LinearLayout mlLayoutContainer;

    private Context mContext;
    private int mSelectedColor;
    private int mNotSelectedColor;
    private int mMenuIcon;
    private String mMenuTitle;
    private float mTextSize;
    private boolean mSelected = false;
    private int mPosition = -1;
    private int mUnreadCount = 0;

    private MenuBarTab(Context context){
        super(context);
    }

    public MenuBarTab(Context context, @DrawableRes int icon, String title, int selectedColor, int
            notSelectedColor, float textSize) {
        super(context);
        this.mMenuIcon = icon;
        this.mMenuTitle = title;
        this.mSelectedColor = selectedColor;
        this.mNotSelectedColor = notSelectedColor;
        this.mTextSize = textSize;

        init(context);
    }

    private void init(Context context) {
        mContext = context;

        //初始化内容布局
        initContainerLayout();

        //添加菜单图片
        addMenuIconView();

        //设置标题控件
        addMenuTitleView();

        //添加内容
        addView(mlLayoutContainer);

        //添加未读信息
        addUnreadView();
    }

    /**
     * 初始化内容布局
     */
    private void initContainerLayout() {
        mlLayoutContainer = new LinearLayout(mContext);
        mlLayoutContainer.setOrientation(LinearLayout.VERTICAL);
        mlLayoutContainer.setGravity(Gravity.CENTER);
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);
        paramsContainer.gravity = Gravity.CENTER;
        mlLayoutContainer.setLayoutParams(paramsContainer);
    }

    /**
     * 添加菜单logo界面
     */
    private void addMenuIconView() {
        mIvIcon = new ImageView(mContext);
        mIvIcon.setImageResource(mMenuIcon);
        mIvIcon.setColorFilter(mNotSelectedColor);
        mlLayoutContainer.addView(mIvIcon);
    }

    /**
     * 添加菜单标题界面
     */
    private void addMenuTitleView() {
        mTvTitle = new TextView(mContext);
        mTvTitle.setText(mMenuTitle);
        mTvTitle.setTextColor(mNotSelectedColor);
        mTvTitle.setTextSize(mTextSize);
        LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTvTitle.setLayoutParams(tvLayoutParams);
        mlLayoutContainer.addView(mTvTitle);
    }

    /**
     * 添加未读信息界面
     */
    private void addUnreadView() {
        int imageHeight = 0;
        int imageWidth = 0;
        //未读信息
        if (mMenuIcon != Color.WHITE) {
            Drawable bgDrawable = ContextCompat.getDrawable(mContext, mMenuIcon);
            imageHeight = dip2px(mContext, bgDrawable.getIntrinsicHeight());
            imageWidth = dip2px(mContext, bgDrawable.getIntrinsicWidth());
        }
        int padding = dip2px(mContext, mTextSize / 2);
        mTvUnreadCount = new TextView(mContext);
        mTvUnreadCount.setTextColor(Color.WHITE);
        mTvUnreadCount.setTextSize(mTextSize);
        mTvUnreadCount.setBackgroundResource(R.drawable.bg_menu_unread);
        mTvUnreadCount.setGravity(Gravity.CENTER);
        mTvUnreadCount.setPadding(padding, 0, padding, 0);
        LayoutParams tvUnreadLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        tvUnreadLayoutParams.gravity = Gravity.CENTER;
        tvUnreadLayoutParams.leftMargin = imageWidth / 4;
        tvUnreadLayoutParams.bottomMargin = imageHeight / 4;
        mTvUnreadCount.setLayoutParams(tvUnreadLayoutParams);
        mTvUnreadCount.setVisibility(GONE);

        addView(mTvUnreadCount);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mSelected = selected;
        if (selected) {
            mIvIcon.setColorFilter(mSelectedColor);
            mTvTitle.setTextColor(mSelectedColor);
        } else {
            mIvIcon.setColorFilter(mNotSelectedColor);
            mTvTitle.setTextColor(mNotSelectedColor);
        }
    }

    /**
     * 设置位置
     *
     * @param position 位置
     */
    public void setPosition(int position) {
        mPosition = position;
    }

    /**
     * 获取位置
     *
     * @return 位置
     */
    public int getPosition() {
        return mPosition;
    }

    /**
     * 设置未读数量
     *
     * @param count 数量
     */
    public void setUnreadCount(int count) {
        //如果数量小于0
        if (count <= 0) {
            mUnreadCount = 0;
            mTvUnreadCount.setVisibility(GONE);
        } else {
            mUnreadCount = count;
            mTvUnreadCount.setText(String.valueOf(count));
            mTvUnreadCount.setVisibility(VISIBLE);
        }
    }

    /**
     * 获取未读信息数量
     *
     * @return 未读数量
     */
    public int getUnreadCount() {
        return mUnreadCount;
    }

    /**
     * 清除未读信息
     */
    public void clearUnreadCount() {
        setUnreadCount(0);
    }

    /**
     * 设置字体大小
     *
     * @param textSize 字体大小
     */
    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mTvTitle.setTextSize(mTextSize);
        mTvUnreadCount.setTextSize(mTextSize);
    }

    /**
     * 设置菜单标题
     *
     * @param title 菜单标题
     */
    public void setMenuTabTitle(String title) {
        mMenuTitle = title;
        mTvTitle.setText(mMenuTitle);
    }

    /**
     * 设置菜单icon
     *
     * @param icon 图标资源
     */
    public void setMenuTabIcon(int icon) {
        mMenuIcon = icon;
        mIvIcon.setImageResource(mMenuIcon);
    }

    /**
     * 设置菜单不同状态颜色
     *
     * @param selectedColor    选中状态
     * @param notSelectedColor 非选中状态
     */
    public void setMenuTabColor(int selectedColor, int notSelectedColor) {
        mSelectedColor = selectedColor;
        mNotSelectedColor = notSelectedColor;
        setSelected(mSelected);
    }

    private int dip2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }
}
