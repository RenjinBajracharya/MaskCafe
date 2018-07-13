package com.example.renjin.maskcafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.renjin.maskcafe.adapter.ViewPageAdapter;
import com.example.renjin.maskcafe.pojo.Menulist;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TabLayout mcontent_main_tablayout;
    ViewPager mcontent_main_viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mcontent_main_tablayout= (TabLayout)findViewById(R.id.content_main_tablayout);
        mcontent_main_viewPager= (ViewPager)findViewById(R.id.content_main_viewpager);

        mcontent_main_tablayout.addTab(mcontent_main_tablayout.newTab().setText("About"));
        mcontent_main_tablayout.addTab(mcontent_main_tablayout.newTab().setText("Gallary"));

        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager(), mcontent_main_tablayout.getTabCount());
        mcontent_main_viewPager.setAdapter(viewPageAdapter);

        mcontent_main_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mcontent_main_tablayout));

        mcontent_main_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mcontent_main_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }

                builder.setMessage("Would you like to call Mask Cafe?");
                builder.setCancelable(false);
                builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String number="+977014411857";

                        Intent phoneIntent= new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",number,null));
                        try {
                            startActivity(phoneIntent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//closing the dialouge box
                    }
                });
                AlertDialog alertDialog= builder.create();
                //alert dialog title
                //alertDialog.setTitle("Call Mask Cafe");
                alertDialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent0=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent0);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent0=new Intent(MainActivity.this,OrderActivity.class);
            startActivity(intent0);

        } else if (id == R.id.nav_gallery) {
            Intent intent1=new Intent(MainActivity.this,MenulistActivity.class);
            startActivity(intent1);

        } else if (id == R.id.nav_location) {
            Intent intent2=new Intent(MainActivity.this,LocationActivity.class);
            startActivity(intent2);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
