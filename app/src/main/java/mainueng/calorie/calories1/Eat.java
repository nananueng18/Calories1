package mainueng.calorie.calories1;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


@Table(name = "Eat")
public class Eat extends Model {

    @Column(name = "atdate")
    public String atdate;

    @Column(name = "breakfastkilo")
    public Integer breakfastkilo = 0 ;

    @Column(name = "lunchfastkilo")
    public Integer lunchkilo = 0;

    @Column(name = "dinnerfastkilo")
    public Integer dinnerkilo = 0;

    @Column(name = "totalkilo")
    public Integer totalkilo;

    @Column(name = "createdAt", index = true)
    public Date createdAt = null;

    @Column(name = "updatedAt", index = true)
    public Date updatedAt = null;

    public static List<Eat> getAll() {
        return new Select().from(Eat.class).orderBy("updatedAt DESC").execute();
    }

    public static Eat getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        return new Select().from(Eat.class).where("atdate = '" + currentDateandTime + "'").orderBy("updatedAt DESC").executeSingle();
    }

    public static Eat getFromDate(String date) {

        return new Select().from(Eat.class).where("atdate = '" + date+ "'" ).orderBy("updatedAt DESC").executeSingle();
    }

    public void saveWithTimestamp() {
        Date now = new Date();
        updatedAt = now;
        if (createdAt == null)
            createdAt = now;
        save();
    }

    @Override
    public String toString() {
        return this.atdate.toString();
    }


}

