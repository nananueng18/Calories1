package mainueng.calorie.calories1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class RecordcalActivity extends BaseActivity {

    private static final int MenuItem_SaveID = 1;

    private Work work = null;
    private EditText titleEdit;
    private EditText contentEdit;
    private EditText dateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordcalform);
        setDrawer(true);

        titleEdit = (EditText) findViewById(R.id.titleEdit);
        contentEdit = (EditText) findViewById(R.id.contentEdit);
        dateEdit = (EditText) findViewById(R.id.dateEdit);

        long id = getIntent().getLongExtra("id", 0);
        if (id == 0) {
            setTitle(R.string.new_work);
        } else {
            setTitle(R.string.edit_work);
            work = Work.load(Work.class, id);
            if (work != null) {
                titleEdit.setText(work.title);
                contentEdit.setText(work.content);
                dateEdit.setText(work.date);
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        addMenuItem(menu, MenuItem_SaveID, R.string.save, buildDrawable(MaterialDesignIconic.Icon.gmi_save));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isEdited()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle(android.R.string.dialog_alert_title);
                    alert.setMessage(R.string.unsaved_exit_alert);
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                    return true;
                }
                break;
            case MenuItem_SaveID:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* surpass all keys in activity; force the user to use form controls */
        return true;
    }

    private boolean isEdited() {
        if (work == null)
            return titleEdit.getText().length() > 0 || contentEdit.getText().length() > 0 || dateEdit.getText().length() > 0;
        else
            return !work.title.equals(titleEdit.getText().toString()) || !work.content.equals(contentEdit.getText().toString())|| !work.date.equals(dateEdit.getText().toString());
    }

    private void save() {
        if (titleEdit.getText().length() > 0) {
            if (work == null)
                work = new Work();
            work.title = titleEdit.getText().toString();
            work.content = contentEdit.getText().toString();
            work.date = dateEdit.getText().toString();
            work.saveWithTimestamp();
            setResult(Activity.RESULT_OK, new Intent().putExtra("id", work.getId()));
            this.finish();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(android.R.string.dialog_alert_title);
            alert.setMessage(R.string.title_is_required);
            alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }
    }
}
