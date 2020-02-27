package com.sendbird.android.sample.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.dashboard.dashboard;
import com.sendbird.android.sample.groupchannel.GroupChannelActivity;
import com.sendbird.android.sample.openchannel.OpenChannelActivity;
import com.sendbird.android.sample.utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, About.OnFragmentInteractionListener{

    //private Toolbar mToolbar;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TEST", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener((DrawerLayout.DrawerListener) toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag, new MyMood()).commit();

//        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
//        setSupportActionBar(mToolbar);
//
//        findViewById(R.id.linear_layout_group_channels).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, GroupChannelActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        findViewById(R.id.linear_layout_open_channels).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, OpenChannelActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        findViewById(R.id.button_disconnect).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Unregister push tokens and disconnect
//                disconnect();
//            }
//        });

        // Displays the SDK version in a TextView
        String sdkVersion = "ComfortZone";
//        ((TextView) findViewById(R.id.text_main_versions)).setText(sdkVersion);

        checkExtra();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        checkExtra();
    }

    private void checkExtra() {
        if (getIntent().hasExtra("groupChannelUrl")) {
            String extraChannelUrl = getIntent().getStringExtra("groupChannelUrl");
            Intent mainIntent = new Intent(MainActivity.this, GroupChannelActivity.class);
            mainIntent.putExtra("groupChannelUrl", extraChannelUrl);
            startActivity(mainIntent);
        }
    }

    /**
     * Unregisters all push tokens for the current user so that they do not receive any notifications,
     * then disconnects from SendBird.
     */
    private void disconnect() {
        SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
            @Override
            public void onUnregistered(SendBirdException e) {
                if (e != null) {
                    // Error!
                    e.printStackTrace();

                    // Don't return because we still need to disconnect.
                } else {
//                    Toast.makeText(MainActivity.this, "All push tokens unregistered.", Toast.LENGTH_SHORT).show();
                }

                ConnectionManager.logout(new SendBird.DisconnectHandler() {
                    @Override
                    public void onDisconnected() {
                        PreferenceUtils.setConnected(false);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Intent intent;
        switch (menuItem.getItemId())
        {
//
            case R.id.nav_contact_us :
                intent = new Intent(Intent.ACTION_SEND);
//To send an email you need to specify mailto as URI
                intent.setData(Uri.parse("mailto:"));
                //String to = "kps@gmail.com";
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"comfortzone@gmail.com"});
                // intent.putExtra(Intent.EXTRA_SUBJECT,"Sending Email Using Implicit Intent");
                //intent.putExtra(Intent.EXTRA_TEXT,"Email body message");
                // intent.putExtra(Intent.EX)

//this is mime type of email without it no activity can be found to send email.
                intent.setType("message/rfc822");
                //Intent chooserIntent =Intent.createChooser(intent,"Launch Email");
                Intent chooserIntent = Intent.createChooser(intent,"Send Email");
                startActivity(chooserIntent);
                break;

            case R.id.nav_explore :
                intent = new Intent(MainActivity.this, OpenChannelActivity.class);
                intent.putExtra("status","explore");
                startActivity(intent);
                break;

            case R.id.nav_logout :
                disconnect();
                break;

            case R.id.nav_mybuddies :
                intent = new Intent(MainActivity.this, GroupChannelActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_mygroups :
                intent = new Intent(MainActivity.this, OpenChannelActivity.class);
                intent.putExtra("status","mygroups");
                startActivity(intent);
                break;

            case R.id.nav_abt_us :
                getSupportFragmentManager().beginTransaction().replace(R.id.frag, new About()).commit();
                // getSupportFragmentManager().beginTransaction().replace(R.id.frag,new About()).commit();
                break;

            case R.id.nav_dashboard :
                intent = new Intent(MainActivity.this, dashboard.class);
                startActivity(intent);
                break;

            case R.id.nav_mood :
                getSupportFragmentManager().beginTransaction().replace(R.id.frag, new MyMood()).commit();
                break;

            case R.id.nav_sos :
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,new Sos()).commit();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
