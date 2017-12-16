package com.github.iron.menubar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iron.menubar.MenuBar;
import com.iron.menubar.MenuBarTab;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MenuBar menuBar = findViewById(R.id.menu_bar);

        menuBar.addMenuBarTab(R.drawable.ic_account_circle_white_24dp, "account").addMenuBarTab(R.drawable
                .ic_discover_white_24dp, "discover").addMenuBarTab(R.drawable.ic_message_white_24dp,
                "message").setCurrentTab(0);

        menuBar.setOnTabSelectedListener(new MenuBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(MenuBarTab menuBarTab, int position) {
                MenuBarTab mbt = menuBar.getMenuTab(2);

                if (position != 2) {
                    mbt.setUnreadCount(mbt.getUnreadCount() + 1);
                } else {
                    mbt.clearUnreadCount();
                }
            }

            @Override
            public void onTabUnselected(MenuBarTab barTab, int position) {

            }
        });
    }
}
