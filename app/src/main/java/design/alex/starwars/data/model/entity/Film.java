package design.alex.starwars.data.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Объкт фильма для хранения в БД
 */
@Entity(
        tableName = "films", // название таблицы
        indices = @Index({"people_id"}), //индекс по столбцу
        foreignKeys = @ForeignKey( // внешний ключ
                entity = People.class, // к какой таблице привязан внешний ключ
                parentColumns = "id", // к какому столбцу привязан ключ в текущей таблице
                childColumns = "people_id", // к какому столбцу привязан ключ во внешней таблице
                onDelete = CASCADE // как обрабатывается удаление данных из внешней таблицы
))
public class Film {

    @PrimaryKey // Первичный ключ
    @ColumnInfo(name = "id") // Название столбца
    private long mId;

    @ColumnInfo(name = "people_id")
    private long mPeopleId;

    public Film() { }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getPeopleId() {
        return mPeopleId;
    }

    public void setPeopleId(long peopleId) {
        mPeopleId = peopleId;
    }
}
