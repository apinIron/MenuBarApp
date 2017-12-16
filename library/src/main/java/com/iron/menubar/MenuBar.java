package com.iron.menubar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iron
 *         created at 2017/11/9
 */
public class MenuBar extends LinearLayout {

    private static final int DEFAULT_SELECTED_COLOR = Color.RED;
    private static final int DEFAULT_NOT_SELECTED_COLOR = Color.GRAY;

    private Context mContext;
    private LayoutParams mTabLayoutParams;
    private OnTabSelectedListener mListener;
    private List<MenuBarTab> mMenuBarTabs;
    private int mCurrentPosition = -1;

    private float mTextSize;
    private int mSelectedColor;
    private int mNotSelectedColor;

    public MenuBar(Context context) {
        this(context, null);
    }

    public MenuBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        init(attrs);
    }

    private void init(AttributeSet attrs) {

        initAttributeSet(attrs);
        //水平布局
        setOrientation(HORIZONTAL);
        mMenuBarTabs = new ArrayList<>();
        //菜单项布局参数
        mTabLayoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        mTabLayoutParams.weight = 1;
    }

    /**
     * 初始化属性
     *
     * @param attributeSet 属性
     */
    private void initAttributeSet(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MenuBar);

        mTextSize = typedArray.getDimension(R.styleable.MenuBar_bar_textSize, getResources().getDimension(R
                .dimen.default_text_size));

        mSelectedColor = typedArray.getColor(R.styleable.MenuBar_bar_selectedColor, DEFAULT_SELECTED_COLOR);

        mNotSelectedColor = typedArray.getColor(R.styleable.MenuBar_bar_notSelectedColor,
                DEFAULT_NOT_SELECTED_COLOR);

        typedArray.recycle();
    }

    public MenuBar addMenuBarTab(@DrawableRes int icon, String title) {
        return addMenuBarTab(new MenuBarTab(mContext, icon, title, mSelectedColor, mNotSelectedColor,
                mTextSize));
    }

    /**
     * 添加菜单标签
     *
     * @param tab 菜单页
     * @return 当前菜单控件
     */
    public MenuBar addMenuBarTab(final MenuBarTab tab) {
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = tab.getPosition();
                //如果这次选中的菜单和上次的一样
                if (position != mCurrentPosition) {
                    //触发事件
                    if (mListener != null) {
                        //tab选中触发事件
                        mListener.onTabSelected(getMenuTab(position), position);
                        //tab失去选中触发事件
                        mListener.onTabUnselected(getMenuTab(mCurrentPosition), mCurrentPosition);
                    }
                    //设置当前位置
                    setCurrentTab(position);
                }
            }
        });
        tab.setLayoutParams(mTabLayoutParams);
        tab.setPosition(mMenuBarTabs.size());
        //添加界面
        this.addView(tab);
        mMenuBarTabs.add(tab);

        return this;
    }

    /**
     * 设置当前选中菜单
     *
     * @param position 位置
     */
    public void setCurrentTab(final int position) {
        if (mCurrentPosition != -1) {
            mMenuBarTabs.get(mCurrentPosition).setSelected(false);
        }
        mMenuBarTabs.get(position).setSelected(true);
        mCurrentPosition = position;
    }

    /**
     * 获取当前选择位置
     *
     * @return 当前位置
     */
    public int getCurrentTabPosition() {
        return mCurrentPosition;
    }

    /**
     * 获取制定位置菜单
     *
     * @param position 位置
     * @return 菜单标签
     */
    public MenuBarTab getMenuTab(int position) {
        if (mMenuBarTabs.size() < position) {
            return null;
        }
        return mMenuBarTabs.get(position);
    }

    /**
     * 获取菜单tab集合
     *
     * @return 菜单tab集合
     */
    public List<MenuBarTab> getMenuTabs() {
        return mMenuBarTabs;
    }

    /**
     * 设置字体大小
     *
     * @param textSize 字体大小
     */
    public MenuBar setTextSize(float textSize) {
        mTextSize = textSize;
        for (int i = 0; i < mMenuBarTabs.size(); i++) {
            mMenuBarTabs.get(i).setTextSize(mTextSize);
        }
        return this;
    }

    /**
     * 设置颜色
     *
     * @param selectedColor    选中颜色
     * @param notSelectedColor 非选中状态
     */
    public MenuBar setColor(int selectedColor, int notSelectedColor) {
        mSelectedColor = selectedColor;
        mNotSelectedColor = notSelectedColor;
        for (int i = 0; i < mMenuBarTabs.size(); i++) {
            mMenuBarTabs.get(i).setMenuTabColor(mSelectedColor, mNotSelectedColor);
        }
        return this;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        if (mCurrentPosition != ss.position) {
            mMenuBarTabs.get(mCurrentPosition).setSelected(false);
            mMenuBarTabs.get(ss.position).setSelected(true);
        }

        mCurrentPosition = ss.position;
    }

    private static class SavedState extends BaseSavedState {

        private int position;

        SavedState(Parcel source) {
            super(source);
            position = source.readInt();
        }

        SavedState(Parcelable sourceState, int position) {
            super(sourceState);
            this.position = position;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /**
     * 设置当前监听事件
     *
     * @param listener 事件
     */
    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mListener = listener;
    }

    public interface OnTabSelectedListener {

        void onTabSelected(MenuBarTab menuBarTab, int position);

        void onTabUnselected(MenuBarTab barTab, int position);
    }

}
