package mainueng.calorie.calories1;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class Calories1 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
