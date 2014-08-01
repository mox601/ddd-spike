package org.mox.spikes.monad.transaction;

import org.testng.annotations.Test;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TransactionTestCase {

    @Test
    public void testSuccess() {

        final Db db = new Db();
        try {
            String object = db.getById("1");
        } catch (Db.DbException e) {
            e.printStackTrace();
        }

        try {
            db.storeObject("ok");
        } catch (Db.DbException e) {
            e.printStackTrace();
        }
    }

    public static class Db {

        public String getById(String id) throws DbException {

            if (id.equals("1")) {
                return "document";
            } else {
                throw new DbException("azz!");
            }
        }

        public void storeObject(String object) throws DbException {

            if (object.equals("ok")) {
                //NOP
            } else {
                throw new DbException("azz!");
            }
        }

        public static class DbException extends Exception {

            public DbException(String s) {

                super(s);
            }
        }
    }
}
