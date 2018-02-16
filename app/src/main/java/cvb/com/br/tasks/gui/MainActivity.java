package cvb.com.br.tasks.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cvb.com.br.tasks.R;
import cvb.com.br.tasks.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private class ViewHolder {
        private FloatingActionButton floatButton;
        private Toolbar toolbar;
        private NavigationView navigationView;


        private void init(Activity act) {
            toolbar = act.findViewById(R.id.toolbar);

            floatButton = act.findViewById(R.id.fab);

            navigationView = act.findViewById(R.id.nav_view);
        }
    }

    //------------------------------------

    private ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vh.init(this);

        setSupportActionBar(vh.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, vh.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        vh.navigationView.setNavigationItemSelectedListener(navigationListener);

        MenuItem itemMenuInicial = vh.navigationView.getMenu().getItem(0);
        itemMenuInicial.setChecked(true);
        navigationListener.onNavigationItemSelected(itemMenuInicial);

        vh.floatButton.setOnClickListener(floatButtonListener);
    }

    private Context getContext() {
        return this;
    }

    private View.OnClickListener floatButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ToastUtil.showMessage(getContext(), "TESTE - Assiste");
        }
    };

    private NavigationView.OnNavigationItemSelectedListener navigationListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.nav_camera) {
            } else if (id == R.id.nav_gallery) {
            } else if (id == R.id.nav_slideshow) {
            } else if (id == R.id.nav_manage) {
            } else if (id == R.id.nav_share) {
            } else if (id == R.id.nav_send) {
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
