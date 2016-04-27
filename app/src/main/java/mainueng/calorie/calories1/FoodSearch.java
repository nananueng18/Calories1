package mainueng.calorie.calories1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;

public class FoodSearch extends BaseActivity {

    private static final int NEW_WORK = 1;
    private static final int SHOW_WORK = 2;

    private ArrayList<Work> work;

    private ListView listView;
    private Button bt_foodsearch;
//    private TextView emptyLabel;
    private EditText et_foodsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsearch);
        setDrawer(true);
        setTitle(R.string.foodsearch);


        listView = (ListView) findViewById(R.id.listView);
//        emptyLabel = (TextView) findViewById(R.id.emptyLabel);
        et_foodsearch = (EditText) findViewById(R.id.et_foodsearch);
        bt_foodsearch = (Button)findViewById(R.id.bt_foodsearch);


        bt_foodsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView();
            }
        });

    }

    private void setView() {
        work = new ArrayList<Work>(Work.getbyfoodname(et_foodsearch.getText().toString()));
        //Toast.makeText(FoodSearch.this, String.valueOf(work.size()), Toast.LENGTH_LONG).show();
//        if (work.isEmpty()) {
//            listView.setVisibility(View.GONE);
//            et_foodsearch.setVisibility(View.GONE);
//            emptyLabel.setVisibility(View.VISIBLE);
//        } else {
//            emptyLabel.setVisibility(View.GONE);
            et_foodsearch.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new foodAdapter(this, work));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("food", work.get(position));
                    setResult(Activity.RESULT_OK, intent);
                    //Toast.makeText(context, "show", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
//        }
    }
}

class foodAdapter extends ArrayAdapter<Work> {

    public foodAdapter(Context context, ArrayList<Work> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new ViewHolder();
            holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Work task = getItem(position);
        holder.text1.setText(task.title);

//        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
//        tv.setText(task.title);
        return convertView;
    }

    static class ViewHolder {
        TextView text1;
    }
}
