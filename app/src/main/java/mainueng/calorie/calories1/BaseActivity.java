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

    protected static final int NEW_WORK = 1;

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
                final Intent intent1 = new Intent(this,CaloriePerDay.class);
                final Intent intent = new Intent(this, RecordBreakfast.class);
                final Intent intent2 = new Intent(this,ListActivity.class);
                new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar_main)
                        .addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.new_work)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_plus)
                                        .withSelectable(false)
                                        .withIdentifier(3)
                        ).withSelectedItem(-1)

                        /*.addDrawerItems(
                                new PrimaryDrawerItem()
                                        .withName(R.string.)
                                        .withIcon(MaterialDesignIconic.Icon.gmi_plus)
                                        .withSelectable(false)
                                        .withIdentifier(3)
                        ).withSelectedItem(-1)*/

                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                switch (drawerItem.getIdentifier()) {
                                    case 1:
                                        startActivityForResult(intent, NEW_WORK);
                                        break;
                                }
                                return false;
                            }
                        })
                        .build();
            }
    }

   // Timestamp var = new Timestamp(System.currentTimeMillis());
   // String var = new SimpleDateFormat("dd/MM/yyyy").format(timestamp.getTime());

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
