package de.morrisbr.reportbooklet;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import de.morrisbr.reportbooklet.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String hostname = "192.168.178.107";
    public static MainActivity instance = null;
    public static final int port = 82;
    private ActivityMainBinding binding;
    ListView listView;
    private AppBarConfiguration mAppBarConfiguration;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ActivityMainBinding inflate = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)new StrictMode.ThreadPolicy.Builder().permitAll().build());
        AndroidNetworking.initialize(getApplicationContext());
        setSupportActionBar(this.binding.appBarMain.toolbar);
        this.binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make((View)view, (CharSequence)"Replace with your own action", (int)0).setAction((CharSequence)"Action", null).show();
            }
        });
        DrawerLayout drawer = this.binding.drawerLayout;
        NavigationView navigationView = this.binding.navView;
        this.mAppBarConfiguration = new AppBarConfiguration.Builder(new int[]{R.id.nav_berichte, R.id.nav_gallery, R.id.nav_slideshow}).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, this.mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment_content_main), this.mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void openView(Class viewClass) {
        startActivity(new Intent(this, viewClass));
    }

    public void openViewWhifObject(Class viewClass, String key, String value) {
        Intent mIntent = new Intent(this, viewClass);
        Bundle mBundle = new Bundle();
        mBundle.putString(key, value);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }
}