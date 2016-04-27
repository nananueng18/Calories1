package mainueng.calorie.calories1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class HistoryActivity extends Activity {
    private CalendarView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        cv = (CalendarView)findViewById(R.id.cvHistory);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Date selectdate = new Date(2016, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                String date = String.valueOf(year) + "-" + sdf.format(selectdate);
                Intent intent = new Intent(HistoryActivity.this, EatActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", dayOfMonth);
                startActivity(intent);
            }
        });
    }
}
