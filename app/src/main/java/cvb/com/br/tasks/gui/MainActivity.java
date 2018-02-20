package cvb.com.br.tasks.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import cvb.com.br.tasks.api.PriorityRequestManager;
import cvb.com.br.tasks.api.RequestManager;
import cvb.com.br.tasks.util.Constant;
import cvb.com.br.tasks.util.SecurityPreferences;
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

        initialLoad();
    }

    private void initialLoad() {
        new PriorityRequestManager(this).getList(rm);
    }

    private RequestManager rm = new RequestManager() {

        @Override
        public void onError(int errorCod, String errorMsg) {
            ToastUtil.showMessage(getContext(), errorMsg);
        }

        @Override
        public void onSuccess(Object result) {
            super.onSuccess(result);
        }
    };

    private Context getContext() {
        return this;
    }

    private View.OnClickListener floatButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent(getContext(), ActCadastroTask.class);
            startActivity(it);
        }
    };

    private NavigationView.OnNavigationItemSelectedListener navigationListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            Fragment fragment = null;
            if (id == R.id.nav_todas) {
                fragment = getInstanceFragment(Constant.TASK_TYPE.NO_FILTER);
            } else if (id == R.id.nav_proximas) {
                fragment = getInstanceFragment(Constant.TASK_TYPE.NEXT_7_DAYS);
            } else if (id == R.id.nav_expiradas) {
                fragment = getInstanceFragment(Constant.TASK_TYPE.OVERDUE);
            }

            setFragment(fragment);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    private Fragment getInstanceFragment(int taskType) {
        Fragment frag = new FragBase();

        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_TASK_TYPE, taskType);

        frag.setArguments(bundle);

        return frag;
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frag_container, fragment).commit();
    }

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

        if (id == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean logout() {
        (new SecurityPreferences(this)).clear();

        Intent it = new Intent(this, ActLogin.class);
        startActivity(it);

        return true;
    }
}
