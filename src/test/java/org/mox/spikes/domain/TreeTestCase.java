package org.mox.spikes.domain;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TreeTestCase {

    @Test
    public void testWithOneItem() throws Exception {

        final FamilyNameCounter familyNameCounter = new FamilyNameCounter();
        familyNameCounter.addMember("Alex", "Miller", 35);
        final List<SurnameNameOccurrences> nameOccurrencesGroupedBySurname =
                familyNameCounter.getNameOccurrencesGroupedBySurname();

        assertNotNull(nameOccurrencesGroupedBySurname);
        assertEquals(nameOccurrencesGroupedBySurname.size(), 1);
        assertTrue(nameOccurrencesGroupedBySurname
                .contains(new SurnameNameOccurrences("Miller", "Alex", 1)));

    }

    @Test
    public void testWithTwoDifferentItems() throws Exception {

        final FamilyNameCounter familyNameCounter = new FamilyNameCounter();

        familyNameCounter.addMember("Alex", "Miller", 35);
        familyNameCounter.addMember("Stephanie", "Miller", 32);

        final List<SurnameNameOccurrences> nameOccurrencesGroupedBySurname =
                familyNameCounter.getNameOccurrencesGroupedBySurname();

        assertNotNull(nameOccurrencesGroupedBySurname);
        assertEquals(nameOccurrencesGroupedBySurname.size(), 2);

        assertTrue(nameOccurrencesGroupedBySurname
                .contains(new SurnameNameOccurrences("Miller", "Alex", 1)));

        assertTrue(nameOccurrencesGroupedBySurname
                .contains(new SurnameNameOccurrences("Miller", "Stephanie", 1)));

    }

    @Test
    public void testWithTwoMembersWithSameName() throws Exception {

        final FamilyNameCounter familyNameCounter = new FamilyNameCounter();

        familyNameCounter.addMember("Alex", "Miller", 35);
        familyNameCounter.addMember("Alex", "Miller", 3);

        final List<SurnameNameOccurrences> nameOccurrencesGroupedBySurname =
                familyNameCounter.getNameOccurrencesGroupedBySurname();

        assertNotNull(nameOccurrencesGroupedBySurname);
        assertEquals(nameOccurrencesGroupedBySurname.size(), 1);

        assertTrue(nameOccurrencesGroupedBySurname
                .contains(new SurnameNameOccurrences("Miller", "Alex", 2)));

    }

    @Test
    public void testPerf() throws Exception {

        final FamilyNameCounter familyNameCounter = new FamilyNameCounter();

        final int iterations = 1000;
        for (int i = 0; i < iterations; i++) {
            String name = "" + i % 5;
            String surname = "" + i % 30;
            final long l = System.nanoTime();
            familyNameCounter.addMember(name, surname, i);
            long nanosecs = System.nanoTime() - l;
        }

        final long before = System.nanoTime();
        final List<SurnameNameOccurrences> nameOccurrencesGroupedBySurname =
                familyNameCounter.getNameOccurrencesGroupedBySurname();
        long getNames = System.nanoTime() - before;
        System.out.println(getNames);

        assertNotNull(nameOccurrencesGroupedBySurname);

    }

    private static class FamilyNameCounter {

        private final Map<String, Map<String, Integer>> stringMapMap;

        private FamilyNameCounter() {

            this.stringMapMap = new HashMap<String, Map<String, Integer>>();
        }

        public void addMember(String name, String surname, int age) {

            Map<String, Integer> nameOccsBySurname = this.stringMapMap
                    .get(surname);

            if (nameOccsBySurname == null) {
                this.stringMapMap
                        .put(surname, new HashMap<String, Integer>());
                nameOccsBySurname = this.stringMapMap.get(surname);
            }

            final Integer count = nameOccsBySurname.get(name);

            if (count == null) {
                nameOccsBySurname.put(name, 1);
            } else {
                nameOccsBySurname.put(name, count + 1);
            }

        }

        public List<SurnameNameOccurrences> getNameOccurrencesGroupedBySurname() {

            final Set<Map.Entry<String, Map<String, Integer>>> entries = this.stringMapMap
                    .entrySet();

            final List<SurnameNameOccurrences> occurrences = new ArrayList<SurnameNameOccurrences>();

            for (final Map.Entry<String, Map<String, Integer>> entry : entries) {

                final String surname = entry.getKey();

                final Map<String, Integer> value = entry.getValue();

                for (Map.Entry<String, Integer> nameOccurrences : value.entrySet()) {
                    occurrences
                            .add(new SurnameNameOccurrences(
                                    surname,
                                    nameOccurrences.getKey(),
                                    nameOccurrences.getValue()));
                }

            }

            return occurrences;
        }
    }

    private static class SurnameNameOccurrences {

        private String surname;

        private String name;

        private int occourrencies;

        private SurnameNameOccurrences() {

            this("", "", -1);

        }

        private SurnameNameOccurrences(String surname, String name,
                int occourrencies) {

            this.surname = surname;
            this.name = name;
            this.occourrencies = occourrencies;
        }

        public String getSurname() {

            return surname;
        }

        public String getName() {

            return name;
        }

        public int getOccourrencies() {

            return occourrencies;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SurnameNameOccurrences that = (SurnameNameOccurrences) o;

            if (occourrencies != that.occourrencies) {
                return false;
            }
            if (name != null ? !name.equals(that.name) : that.name != null) {
                return false;
            }
            if (surname != null ? !surname
                    .equals(that.surname) : that.surname != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {

            int result = surname != null ? surname.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + occourrencies;
            return result;
        }

        @Override
        public String toString() {

            return "SurnameNameOccurrencies{" +
                    "surname='" + surname + '\'' +
                    ", name='" + name + '\'' +
                    ", occourrencies=" + occourrencies +
                    '}';
        }
    }
}
