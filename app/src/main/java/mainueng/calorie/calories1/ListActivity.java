package mainueng.calorie.calories1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static mainueng.calorie.calories1.R.id.Calorie;

public class ListActivity extends BaseActivity {


    private static final int NEW_Eat = 1;
    private static final int SHOW_Eat = 2;
    private ArrayList<Eat> eat;


    private ListView listView;
    private TextView emptyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setDrawer(false);
        setTitle(R.string.tasks);

        listView = (ListView) findViewById(R.id.listView);
        emptyLabel = (TextView) findViewById(R.id.emptyLabel);

        FloatingActionButton newFab = (FloatingActionButton) findViewById(R.id.newFab);
        newFab.setImageDrawable(buildDrawable(MaterialDesignIconic.Icon.gmi_plus));
        newFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, EatActivity.class);
                startActivityForResult(intent, NEW_Eat1);
            }
        });

        setView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            switch (requestCode) {
                case NEW_Eat:
                    if (extras != null && extras.getLong("id", 0) > 0)
                        setView();
                    break;
                case SHOW_Eat:
                    if (extras != null && extras.getBoolean("refreshNeeded", false))
                        setView();
                    break;
            }
        }
    }

    private void setView() {
        eat = new ArrayList<Eat>(Eat.getAll());
        if (eat.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyLabel.setVisibility(View.VISIBLE);
        } else {
            emptyLabel.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new WorkAdapter(this,eat));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });
        }
    }
}

class WorkAdapter extends ArrayAdapter<Eat> {

    public WorkAdapter(Context context, ArrayList<Eat> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);

            holder = new ViewHolder();
            holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            holder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            //holder.text3 = (TextView) convertView.findViewById(R.id.Calorie);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Eat Eat = getItem(position);
        holder.text1.setText(Eat.atdate);
        holder.text2.setText(Eat.totalkilo.toString());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, parent, false);

            holder = new ViewHolder();
            holder.text3 = (TextView) convertView.findViewById(R.id.Calorie);
            convertView.setTag(holder);
        } else {

        }

        return convertView;
    }

    class ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
    }

}

