package databasetest;

import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.util.*;


public class AgeTest {

    DataBase userRepository = DataBase.getInstance();
    Random ran = new Random();

    @Test
    public void GetAgeTest() throws SQLException {
        userRepository.dropPersonTable();

        List<Person> persons = new ArrayList();
        for (int i = 0; i < 9; i++) {
            UUID uuid = UUID.randomUUID();
            String userName = ran.ints(97, 123).limit(10)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            int age = ran.nextInt(100);
            userRepository.addPerson(uuid.toString(), userName, age);
            persons.add(new Person(uuid.toString(), userName, age));
        }

        List<Integer> ages = userRepository.getPersonsAges();
        persons.sort(Comparator.comparing(Person::getAge).reversed());
        for (int i = 0; i < persons.size(); i++) {
            int exceptedAge = persons.get(i).age;
            int resultAge = ages.get(i);
            Assert.assertEquals(exceptedAge, resultAge);
        }


    }
}
