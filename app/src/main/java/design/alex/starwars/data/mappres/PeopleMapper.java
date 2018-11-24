package design.alex.starwars.data.mappres;

import java.util.ArrayList;
import java.util.List;

import design.alex.starwars.data.model.entity.People;
import design.alex.starwars.data.model.rest.RawPeople;

/**
 * Маппер данных из сырых данных в данные внутреннего пользования
 */
public class PeopleMapper {

    public PeopleMapper() { }

    public List<People> transform(List<RawPeople> rawPeopleList) {
        List<People> result = new ArrayList<>();
        if (rawPeopleList == null || rawPeopleList.isEmpty()) {
            return result;
        }
        for (RawPeople rawPeople : rawPeopleList) {
            result.add(transform(rawPeople));
        }
        return result;
    }

    private People transform(RawPeople rawPeople) {
        String id = rawPeople.getUrl().replaceAll("[\\D+]","");
        String image = String.format("https://starwars-visualguide.com/assets/img/characters/%s.jpg", id);
        People people = new People(Long.parseLong(id), rawPeople.getName(), image);
        try {
            people.setHeight(Integer.parseInt(rawPeople.getHeight()));
        } catch (NumberFormatException ignored) {}
        try {
            people.setMass(Integer.parseInt(rawPeople.getMass()));
        } catch (NumberFormatException ignored) {}

        people.setHairColor(rawPeople.getHairColor());
        people.setEyeColor(rawPeople.getEyeColor());
        people.setBirthYear(rawPeople.getBirthYear());
        people.setGender(rawPeople.getGender());
        people.setHomeWorld(rawPeople.getHoumeWorld());
        return people;

    }
}
