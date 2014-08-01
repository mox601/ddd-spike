package org.mox.spikes.monad.optional;

import com.google.common.base.Function;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class OptionalTestCase {

    @Test
    public void testNotNullOptional() throws Exception {

        final String expected = "name";
        final Insurance insurance = new Insurance(expected);
        final Car car = new Car(insurance);
        final Person person = new Person(car);
        final Optional<Person> personOptional = new Optional<Person>(person);
        final String actual = getCarInsuranceName(personOptional);
        assertEquals(actual, expected);
        assertNotEquals(actual, "not found");
    }

    @Test
    public void testNullOptional() throws Exception {

        final String expected = null;
        final Insurance insurance = new Insurance(expected);
        final Car car = new Car(insurance);
        final Person person = new Person(car);
        final Optional<Person> personOptional = new Optional<Person>(person);
        final String actual = getCarInsuranceName(personOptional);
        assertEquals(actual, "not found");
    }

    public String getCarInsuranceName(final Optional<Person> person) {

        return person.flatMap(
                new Function<Person, Optional<Car>>() {
                    @Override
                    public Optional<Car> apply(Person person) {

                        return person.getCar();
                    }
                }).flatMap(new Function<Car, Optional<Insurance>>() {
            @Override
            public Optional<Insurance> apply(Car car) {

                return car.getInsurance();
            }
        }).map(new Function<Insurance, String>() {
            @Override
            public String apply(Insurance insurance) {

                return insurance.getName();
            }
        }).or("not found");
    }

    private class Person {

        private Optional<Car> car;

        private Person(Car car) {

            this.car = new Optional<Car>(car);
        }

        private Optional<Car> getCar() {

            return car;
        }
    }

    private class Car {

        private Optional<Insurance> insurance;

        private Car(final Insurance insurance) {

            this.insurance = new Optional<Insurance>(insurance);
        }

        private Optional<Insurance> getInsurance() {

            return insurance;
        }
    }

    private class Insurance {

        private String name;

        private Insurance(String name) {

            this.name = name;
        }

        private String getName() {

            return name;
        }
    }
}
