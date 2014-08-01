package org.mox.spikes.monad.state;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.mox.spikes.monad.state.Maybe.unit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class MaybeTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaybeTestCase.class);

    @Test
    public void givenNotNullMaybeIsJust() throws Exception {

        final Integer marcoId = 122;
        final Person marcoWithId = new Person("marco", marcoId);
        final UserAccountRepository userAccountRepository = new UserAccountRepository();
        final Maybe<UserAccount> maybeUserAccount = unit(marcoWithId)
                .map(new MonadicIdGetter())
                .map(new LookupUserAccount(userAccountRepository));

        assertEquals(maybeUserAccount, new Just<UserAccount>(new UserAccount(marcoId)));
        assertEquals(maybeUserAccount.value(), new UserAccount(marcoId));
    }

    @Test
    public void givenNullMaybeIsNothing() throws Exception {

        final Person marcoWithOddId = new Person("marco", 123);
        final UserAccountRepository userAccountRepository = new UserAccountRepository();

        final Maybe<UserAccount> marcoAccount = unit(marcoWithOddId)
                .map(new MonadicIdGetter())
                .map(new LookupUserAccount(userAccountRepository));

        assertNull(marcoAccount.value());
    }

    @Test (enabled = false)
    public void testUsingFlatMap() throws Exception {

        final Person marcoWithOddId = new Person("marco", 123);
        final UserAccount marcoAccount = getUserAccountFromPerson(marcoWithOddId);

//        assertNull(marcoAccount.value());

    }

    public UserAccount getUserAccountFromPerson(final Person person) {

        //TODO ...somehow missing flatMap point?

        Function<Maybe<Person>, Maybe<Integer>> flatmap = unit(person).flatMap(new Function<Person, Integer>() {

            @Override
            public Integer apply(Person person) {
                return person.getId();
            }
        });


        throw new UnsupportedOperationException("NIY");
    }

    private class Person {

        private final String name;

        private final Integer id;

        public Person(String name, Integer id) {

            this.name = name;
            this.id = id;
        }

        private String getName() {

            return name;
        }

        private Integer getId() {

            return id;
        }
    }

    private class MonadicIdGetter implements Function<Person, Maybe<Integer>> {

        @Override
        public Maybe<Integer> apply(final Person person) {

            final Integer id = person.getId();

            if (id % 2 == 0) {
                LOGGER.info("just '" + id.intValue() + "'");
                return unit(id);
            } else {
                LOGGER.info("nothing");
                return unit(null);
            }
        }
    }

    private class LookupUserAccount implements Function<Integer, Maybe<UserAccount>> {

        private final UserAccountRepository userAccountRepository;

        private LookupUserAccount(final UserAccountRepository userAccountRepository) {

            this.userAccountRepository = userAccountRepository;
        }

        @Override
        public Maybe<UserAccount> apply(final Integer userId) {

            return unit(userAccountRepository.getById(userId));
        }
    }

    private class UserAccountRepository {

        public UserAccount getById(final Integer userId) {

            if (userId % 2 == 0) {
                LOGGER.info("userId '" + userId.intValue() + "'");
                return new UserAccount(userId);

            } else {
                LOGGER.info("null userAccount");
                //represents not found
                return null;
            }
        }
    }

    private class UserAccount {

        private final Integer userId;

        public UserAccount(final Integer userId) {

            this.userId = userId;
        }

        private Integer getUserId() {

            return userId;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            UserAccount that = (UserAccount) o;

            if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {

            return userId != null ? userId.hashCode() : 0;
        }
    }
}
