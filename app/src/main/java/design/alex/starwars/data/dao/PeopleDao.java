package design.alex.starwars.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import design.alex.starwars.data.model.entity.People;
import io.reactivex.Single;

@Dao
public interface PeopleDao {

    /**
     * Получение всех персонажей
     * @return List
     */
    @Query("SELECT * FROM peoples")
    List<People> getAllPeoples();

    /**
     * Получение персонажа по заданному Id
     * @param id - Id персонажа
     * @return Single - поток из БД для обработки полученной информации
     */
    @Query("SELECT * FROM peoples WHERE id = :id")
    Single<People> getPeople(Long id);

    /**
     * Добавление нового персонажа в БД
     * @param people - персонаж
     */
    @Insert(
            onConflict = OnConflictStrategy.REPLACE // стратегия решения конфликтов при добавлении
    )
    void insert(People people);

}
