package org.mox.spikes.monad.validation;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mox.spikes.monad.validation.Failure.failure;
import static org.mox.spikes.monad.validation.Success.success;
import static org.mox.spikes.monad.validation.ValidationTestCase.Person.validateAge;
import static org.mox.spikes.monad.validation.ValidationTestCase.Person.validateName;
import static org.testng.Assert.*;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ValidationTestCase {

    @Test
    public void testValidInstance() throws Exception {

        final Person alex = new Person(100, "Alex");
        assertTrue(validateAge(alex).isSuccess());
        assertTrue(validateName(alex).isSuccess());

    }

    @Test
    public void testNotValidInstance() throws Exception {

        final Person christine = new Person(131, "christine");
        assertFalse(validateAge(christine).isSuccess());
        assertFalse(validateName(christine).isSuccess());
        assertNotNull(validateName(christine).value);

    }

    @Test
    public void testOnListWithGuava() throws Exception {

        final Person alex = new Person(100, "Alex");
        final Person christine = new Person(131, "christine");

        List<Person> all = new ArrayList<Person>();
        all.add(alex);
        all.add(christine);

        final Iterable<Person> onlyValidAges = Iterables.filter(all, new HasValidAge());
        final List<Person> persons = Lists.newArrayList(onlyValidAges);
        assertEquals(persons.size(), 1);
        assertEquals(persons.get(0), alex);

    }

    public static class Person {

        private Integer age;

        private String name;

        public Person(Integer age, String name) {

            this.age = age;
            this.name = name;
        }

        private Integer getAge() {

            return age;
        }

        public String getName() {

            return name;
        }

        public static Validation<String, Person> validateAge(final Person p) {

            final boolean isValidAge = p.getAge() > 0 && p.getAge() < 130;
            final Validation<String, Person> success = success(p);
            return isValidAge ? success : failure("age not valid", p);
        }

        public static Validation<String, Person> validateName(final Person p) {

            final boolean isValidName = Character.isUpperCase(p.getName().charAt(0));
            final Validation<String, Person> success = success(p);
            return isValidName ? success : failure("name not valid", p);
        }
    }

    private class HasValidAge implements Predicate<Person> {

        @Override
        public boolean apply(Person person) {

            return validateAge(person).isSuccess();
        }
    }
}
