package mainueng.calorie.calories1;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Table(name = "EatMeal")
public class EatMeal extends Model {

    @Column(name = "atdate")
    public String atdate;

    @Column(name = "meal")
    public String meal;

    @Column(name = "food")
    public String food;

    @Column(name = "kilocal")
    public Integer kilocal = 0;

    @Column(name = "createdAt", index = true)
    public Date createdAt = null;

    @Column(name = "updatedAt", index = true)
    public Date updatedAt = null;

    public static List<EatMeal> getAll() {
        return new Select().from(EatMeal.class).orderBy("updatedAt DESC").execute();
    }

    public static EatMeal getToday(String Meal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        return new Select().from(EatMeal.class).where("atdate = '" + currentDateandTime + "' and meal='" + Meal + "'").orderBy("updatedAt DESC").executeSingle();
    }

    public static List<EatMeal> getBreakfast(String date) {
        return new Select().from(EatMeal.class).where("atdate = '" + date + "' and meal='breakfast'").orderBy("updatedAt DESC").execute();
    }

    public static List<EatMeal> getLunch(String date) {
         return new Select().from(EatMeal.class).where("atdate = '" + date + "' and meal='lunch'").orderBy("updatedAt DESC").execute();
    }

    public static List<EatMeal> getDinner(String date) {
        return new Select().from(EatMeal.class).where("atdate = '" + date + "' and meal='dinner'").orderBy("updatedAt DESC").execute();
    }

    public static EatMeal getFromDate(String date) {
        return new Select().from(EatMeal.class).where("atdate = '" + date+ "'" ).orderBy("updatedAt DESC").executeSingle();
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

