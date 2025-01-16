package com.example.suzette.sqlrepository;

import java.sql.Connection;

/***
 * Interface for class that will create a JDBC connection to the database.
 */
public interface iJDBCConnection {

    /***
     * Creates a connection to the database. Utilized by SQL Repository methods to query
     * the database
     *
     * @return: JDBC Connection object
     */
    public Connection createConnection ();

    /***
     * Disconnects from database. Must be called at the end of any method that creates a
     * connection
     */
    public void disconnect ();

}
