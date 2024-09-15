package org.slieer.eclipseCollections;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.api.Test;
import org.slieer.guava.ObjectsTest.Person;

@Slf4j
public class First {
    @Test
    void test(){
        var people = Lists.immutable.of(new Person("Alice", "zhai"),
                new Person("Bob", "li"), new Person("Carol", "zhai"));

        var namesOver21 = people
                .select(person -> person.getAge() > 21) // Meat, no buns
                .collect(Person::getFirstName);              // Meat

        namesOver21.forEach(System.out::println);
    }
}
