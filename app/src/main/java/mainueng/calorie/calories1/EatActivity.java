package mainueng.calorie.calories1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EatActivity extends BaseActivity {

    private Eat eat = null;
    private static final int MenuItem_SaveID = 1;

    private TextView etAtdate;

    private ListView lvBreakfast;
    private ListView lvLunch;
    private ListView lvdinner;

    private TextView tvTotalKilo;
    private Button bt_breakfast;
    private Button bt_lunch;
    private Button bt_dinner;

    private int MornKilo = 0;
    private int LunchKilo = 0;
    private int DinnerKilo = 0;

    private Work MornFood;
    private Work LunchFood;
    private Work DinnerFood;

    private Calendar calendar;
    private int year, month, day;

    private static final int BREAKID = 111;
    private static final int LUNCHID = 222;
    private static final int DINNERID = 333;

    private Eat eating;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String today = sdf.format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);
        setDrawer(false);

        etAtdate = (TextView) findViewById(R.id.etAtdate);
        tvTotalKilo = (TextView) findViewById(R.id.tvTotalKilo);
        bt_breakfast =(Button) findViewById(R.id.bt_breakfast);
        lvBreakfast = (ListView)findViewById(R.id.lvBreakfast);
        bt_lunch = (Button)findViewById(R.id.bt_lunch);
        lvLunch = (ListView)findViewById(R.id.lvlunch);
        bt_dinner = (Button)findViewById(R.id.bt_dinner);
        lvdinner = (ListView)findViewById(R.id.lvdinner);

        setTitle(R.string.new_eat);

        if (getIntent().getExtras() != null) {
            today = getIntent().getExtras().getString("date");
            year = getIntent().getExtras().getInt("year");
            month = getIntent().getExtras().getInt("month");
            day = getIntent().getExtras().getInt("day");
            showDate(year, month + 1, day);

            eating = Eat.getFromDate(today);

            CalKilo();

            bt_breakfast.setVisibility(View.GONE);
            bt_lunch.setVisibility(View.GONE);
            bt_dinner.setVisibility(View.GONE);
        } else {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDate(year, month + 1, day);

            eating = Eat.getToday();

            if (eating == null) {
                eating = new Eat();
                eating.atdate = today;
                eating.breakfastkilo = 0;
                eating.lunchkilo = 0;
                eating.dinnerkilo = 0;
                eating.saveWithTimestamp();
            }

            CalKilo();
        }


        etAtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(EatActivity.this, myDateListener, year, month, day).show();
            }
        });

        bt_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatActivity.this,FoodSearch.class);
                startActivityForResult(intent, BREAKID);
            }
        });

        bt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatActivity.this,FoodSearch.class);
                startActivityForResult(intent,LUNCHID);
            }
        });

        bt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EatActivity.this,FoodSearch.class);
                startActivityForResult(intent,DINNERID);
            }
        });
    }

    private void CalKilo() {
        List<EatMeal> breakfast = EatMeal.getBreakfast(today);
        List<EatMeal> lunch = EatMeal.getLunch(today);
        List<EatMeal> dinner = EatMeal.getDinner(today);

        EatMealAdapter breakfastadapt = new EatMealAdapter(this,breakfast);
        lvBreakfast.setAdapter(breakfastadapt);

        EatMealAdapter lunchtadapt = new EatMealAdapter(this,lunch);
        lvLunch.setAdapter(lunchtadapt);

        EatMealAdapter dinneradapt = new EatMealAdapter(this,dinner);
        lvdinner.setAdapter(dinneradapt);

        MornKilo = 0;
        for(EatMeal meal : breakfast) {
            MornKilo += meal.kilocal;
        }

        LunchKilo = 0;
        for(EatMeal meal : lunch) {
            LunchKilo += meal.kilocal;
        }

        DinnerKilo = 0;
        for(EatMeal meal : dinner) {
            DinnerKilo += meal.kilocal;
        }

        tvTotalKilo.setText(String.valueOf(MornKilo + LunchKilo + DinnerKilo));
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        etAtdate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                            onBackPressed();
                break;

            case MenuItem_SaveID:
                save();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    private void save() {

            eating.atdate = today; //etAtdate.getText().toString()
            eating.breakfastkilo = MornKilo;
            eating.lunchkilo = LunchKilo;
            eating.dinnerkilo = DinnerKilo;
            eating.totalkilo = MornKilo + LunchKilo + DinnerKilo;

            eating.saveWithTimestamp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();

            switch (requestCode) {
                case BREAKID:
                    if (extras != null) {
                        MornFood = (Work)extras.getSerializable("food");

                        EatMeal meal = new EatMeal();
                        meal.atdate = today;
                        meal.meal = "breakfast";
                        meal.food = MornFood.title;
                        meal.kilocal = Integer.valueOf(MornFood.content);
                        meal.saveWithTimestamp();

                        CalKilo();
                        save();
                    }
                    break;
                case LUNCHID:
                    if (extras != null){
                        LunchFood = (Work)extras.getSerializable("food");
                        EatMeal meal = new EatMeal();
                        meal.atdate = today;
                        meal.meal = "lunch";
                        meal.food = LunchFood.title;
                        meal.kilocal = Integer.valueOf(LunchFood.content);
                        meal.saveWithTimestamp();

                        CalKilo();
                        save();
                    }
                    break;
                case DINNERID:
                    if (extras != null){
                        DinnerFood = (Work)extras.getSerializable("food");
                        EatMeal meal = new EatMeal();
                        meal.atdate = today;
                        meal.meal = "dinner";
                        meal.food = DinnerFood.title;
                        meal.kilocal = Integer.valueOf(DinnerFood.content);
                        meal.saveWithTimestamp();

                        CalKilo();

                        save();
                    }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
        }
    }

    class EatMealAdapter extends ArrayAdapter<EatMeal> {
        private Context context;
        public EatMealAdapter(Context context, List<EatMeal> foods) {
            super(context, 0, foods);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, parent, false);

                holder = new ViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.tvFood);
                holder.text2 = (TextView) convertView.findViewById(R.id.tvKilo);
                holder.btRemove = (Button)convertView.findViewById(R.id.btRemove);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final EatMeal food = getItem(position);
            holder.text1.setText(food.food);
            holder.text2.setText(String.valueOf(food.kilocal));
            holder.btRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    food.delete();
                    CalKilo();
                    save();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView text1;
            TextView text2;

            Button btRemove;
        }
    }
}


