package mainueng.calorie.calories1;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "Work")
public class Work extends Model implements Serializable {

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "date")
    public String date;

    @Column(name = "dueAt", index = true)
    public Date dueAt = null;

    @Column(name = "createdAt", index = true)
    public Date createdAt = null;

    @Column(name = "updatedAt", index = true)
    public Date updatedAt = null;

    public static List<Work> getAll() {
        return new Select().from(Work.class).orderBy("updatedAt DESC").execute();
    }
    public static List<Work> getbyfoodname(String foodname){
        return new Select().from(Work.class).where("title like '%" + foodname + "%'").execute();
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
        return this.title;
    }
}
