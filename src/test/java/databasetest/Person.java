package databasetest;

public class Person {
    public String uid;
    public String name;
    public int age;

    public Person(String uid, String name, int age) {
        this.uid = uid;
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

}
