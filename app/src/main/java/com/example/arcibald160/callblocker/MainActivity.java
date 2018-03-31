package com.example.arcibald160.callblocker;

import android.Manifest;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.arcibald160.callblocker.tools.SectionsPageAdapter;
import com.example.arcibald160.callblocker.tools.Tab1Fragment;
import com.example.arcibald160.callblocker.tools.Tab2Fragment;
import com.example.arcibald160.callblocker.tools.Tab3Fragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        // permission for writing data
        int PERMISSION_ACCESS_CALL_PHONE = 0;
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CALL_PHONE},
                PERMISSION_ACCESS_CALL_PHONE
        );
    }



    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), getString(R.string.recent_blocked_numbers_label));
        adapter.addFragment(new Tab2Fragment(), getString(R.string.blocked_numbers_label));
        adapter.addFragment(new Tab3Fragment(), getString(R.string.busy_label));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.busy_mode_id:
                MenuItem busyIcon = menu.findItem(R.id.busy_mode_icon_id);

                if (item.isChecked()) {
                    item.setChecked(false);
                    busyIcon.setIcon(R.mipmap.turn_off_round);
                } else {
                    item.setChecked(true);
                    busyIcon.setIcon(R.mipmap.turn_on_round);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
