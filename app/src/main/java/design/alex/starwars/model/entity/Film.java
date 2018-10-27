package design.alex.starwars.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "films")
public class Film {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long mId;

    public Film() {

    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
