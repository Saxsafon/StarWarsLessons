package design.alex.starwars;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import design.alex.starwars.model.dao.FilmDao;
import design.alex.starwars.model.dao.PeopleDao;
import design.alex.starwars.model.entity.Film;
import design.alex.starwars.model.entity.People;

@Database(entities = { People.class, Film.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PeopleDao getPeopleDao();

    public abstract FilmDao getFilmDao();

}
