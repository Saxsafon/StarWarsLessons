package design.alex.starwars.data.rest;

import design.alex.starwars.data.model.rest.RawResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiPeoples {

    /**
     * Получение списка персонажей
     * @param page - страница
     * @return RawResult - сырой результат
     */
    @GET("/api/people/?format=json")
    Observable<RawResult> getAllPeoples(
            @Query("page") Integer page
    );

}
