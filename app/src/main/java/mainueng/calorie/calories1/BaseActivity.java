package mainueng.calorie.calories1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int NEW_List = 1; //New3
    protected static final int HISTORY = 3;
    protected static final int NEW_Eat1 = 2; //New2
    protected static final int NEW_WORK = 4;
    protected static final int About = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setDrawer(Boolean upEnabled) {
        Toolbar toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            if (upEnabled) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar_main)
                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.home)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_home)
                                        .withSelectable(false)
                                        .withIdentifier(1)
                        //).withSelectedItem(-1)

                        ).addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.new_work)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_plus)
                                        .withSelectable(false)
                                        .withIdentifier(4)

                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.today)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_calendar_note)
                                        .withSelectable(false)
                                        .withIdentifier(2)
                        ).withSelectedItem(-1)

                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.history)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_calendar)
                                        .withSelectable(false)
                                        .withIdentifier(3)
                        ).withSelectedItem(-1)
                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.about)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_android)
                                        .withSelectable(false)
                                        .withIdentifier(5)
                        ).withSelectedItem(-1)

                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                                switch (drawerItem.getIdentifier()) {
                                    case 1:
                                        Intent intent = new Intent(BaseActivity.this,ListActivity.class);
                                        startActivityForResult(intent,NEW_List);
                                        break;
                                    case 4:
                                        Intent intent1 = new Intent(BaseActivity.this,RecordcalActivity.class);
                                        startActivityForResult(intent1, HISTORY);
                                        break;
                                    case 2:
                                        Intent intent2 = new Intent(BaseActivity.this,EatActivity.class);
                                        startActivityForResult(intent2, NEW_Eat1);
                                        break;
                                    case 3:
                                        Intent intent3 = new Intent(BaseActivity.this,HistoryActivity.class);
                                        startActivityForResult(intent3, NEW_WORK);
                                        break;
                                    case 5:
                                        Intent intent4 = new Intent(BaseActivity.this,MainActivity.class);
                                        startActivityForResult(intent4, About);
                                        break;
                                }
                                return false;
                            }
                        })
                        .build();
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    protected Drawable buildDrawable(IIcon icon) {
        return new IconicsDrawable(this).icon(icon).color(Color.WHITE).sizeDp(24).paddingDp(4);
    }

    protected void addMenuItem(Menu menu, int id, int labelId, Drawable icon) {
        MenuItem menuItem = menu.add(Menu.NONE, id, Menu.NONE, labelId);
        menuItem.setIcon(icon);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
}
