# MenuBarApp


### 如何使用

Gradle:
```
    compile 'com.iron.menubar:menubar:1.0.0'
```

Layout XML:
```
    <com.iron.menubar.MenuBar
        android:id="@+id/menu_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:paddingTop="10dp"
        android:paddingBottom="10dp"
        android:background="#DFDFDF"/>
```

JavaCode:
```
        MenuBar menuBar = findViewById(R.id.menu_bar);

        menuBar.addMenuBarTab(R.drawable.ic_account_circle_white_24dp, "account")
                .addMenuBarTab(R.drawable.ic_discover_white_24dp, "discover")
                .addMenuBarTab(R.drawable.ic_message_white_24dp,"message")
                .setCurrentTab(0);

        menuBar.setOnTabSelectedListener(new MenuBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(MenuBarTab menuBarTab, int position) {
            
            }

            @Override
            public void onTabUnselected(MenuBarTab barTab, int position) {

            }
        });
```

可配置属性
```
    <declare-styleable name="MenuBar">
        <!-- 选中状态颜色，默认为红色 -->
        <attr name="bar_selectedColor" format="color"/>
        <!--  非选中状态颜色，默认为灰色 -->
        <attr name="bar_notSelectedColor" format="color"/>
        <!-- 字体大小，默认为8sp -->
        <attr name="bar_textSize" format="dimension"/>
    </declare-styleable>
```
