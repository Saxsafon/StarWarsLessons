package design.alex.starwars.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import design.alex.starwars.data.dao.FilmDao;
import design.alex.starwars.data.dao.PeopleDao;
import design.alex.starwars.data.model.entity.Film;
import design.alex.starwars.data.model.entity.People;

@Database(entities = { People.class, Film.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /** Интерфейс для работы с таблицей персонажей */
    public abstract PeopleDao getPeopleDao();

    /** Интерфейс для работы с таблицей фильмов */
    public abstract FilmDao getFilmDao();

}
