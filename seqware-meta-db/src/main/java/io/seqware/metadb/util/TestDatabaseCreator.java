package io.seqware.metadb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handles basic database creation.
 * 
 * @author boconnor
 * @version $Id: $Id
 */
public class TestDatabaseCreator {

    private static final String DEFAULT_DB_HOST = "127.0.0.1";
    private static final String DEFAULT_DB_PORT = "5432";
    private static final String POSTGRE_DB = "postgres";
    private static final String SEQWARE_DB = "test_seqware_meta_db";
    private static final String SEQWARE_USER = "seqware";
    private static final String SEQWARE_PASSWORD = "seqware";
    private static boolean database_changed;
    private static boolean first_time_created = true;
    private final Logger logger = LoggerFactory.getLogger(TestDatabaseCreator.class);

    /**
     * @return the DEFAULT_DB_HOST
     */
    protected String getDEFAULT_DB_HOST() {
        return DEFAULT_DB_HOST;
    }
    
    /**
     * @return the DEFAULT_DB_PORT
     */
    protected String getDEFAULT_DB_PORT() {
        return DEFAULT_DB_PORT;
    }
    
    /**
     * <p>
     * createNewDatabase.
     * </p>
     *
     * @param loadTestingData load the provided testing data
     *
     * @throws java.sql.SQLException
     *                               if any.
     */
    public void createNewDatabase(boolean loadTestingData) throws SQLException {
        try (Connection connectionToPostgres = createConnection(getPOSTGRE_DB(), getSEQWARE_USER(), getSEQWARE_PASSWORD())) {
            connectionToPostgres.createStatement().execute("CREATE DATABASE " + getSEQWARE_DB() + ";");
            createDatabase(loadTestingData);
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * createDatabase.
     * </p>
     * 
     * @throws java.sql.SQLException
     *             if any.
     */
    public void createDatabase() throws SQLException {

        createDatabase(true);
    }

    /**
     * <p>
     * createDatabase.
     * </p>
     * 
     * @param loadTestingData
     *            load the provided testing data
     * @throws java.sql.SQLException
     *             if any.
     */
    public void createDatabase(boolean loadTestingData) throws SQLException {
        if (!first_time_created && !database_changed) {
            logger.info("TestDatabaseCreator.createDatabase: database not changed or not first time so not creating DB");
            return;
        }

        first_time_created = false;
        Connection connectionToPostgres = null;
        Connection connectionToSeqware = null;
        try {
            connectionToSeqware = createConnection(getSEQWARE_DB(), getSEQWARE_USER(), getSEQWARE_PASSWORD());
            loadDBStructure(connectionToSeqware, loadTestingData);
        } catch (Exception e) {
            logger.info("TestDatabaseCreator.createDatabase " + e.getMessage());
        } finally {
            if (connectionToPostgres != null) {
                connectionToPostgres.close();
            }
            if (connectionToSeqware != null) {
                connectionToSeqware.close();
            }
        }
        database_changed = false;
    }

    /**
     * Convenient method to run a query against the test database, avoids unclosed connections.
     * 
     * @param <T>
     * @param h
     * @param query
     * @param params
     * @return
     */

    public <T extends Object> T runQuery(ResultSetHandler<T> h, String query, Object... params) {
        return runQuery(h, query, false, params);
    }

    /**
     * Run an insert, update, or delete
     * 
     * @param query
     * @param params
     * @return
     */
    public int runUpdate(String query, Object... params) {
        return runQuery(new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools |
                                                                               // Templates.
            }
        }, query, true, params);
    }

    /**
     * Convenient method to run a query against the test database, avoids unclosed connections.
     * 
     * @param <T>
     * @param h
     * @param query
     * @param update
     * @param params
     * @return
     */
    public <T extends Object> T runQuery(ResultSetHandler<T> h, String query, boolean update, Object... params) {
        QueryRunner run = new QueryRunner();
        T result = null;
        Connection connectionToSeqware = null;
        try {
            connectionToSeqware = createConnection(getSEQWARE_DB(), getSEQWARE_USER(), getSEQWARE_PASSWORD());
            if (update) {
                return (T) (Integer) run.update(connectionToSeqware, query, params);
            } else {
                result = run.query(connectionToSeqware, query, h, params);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Use this helper method so we don't have to check for null
            DbUtils.closeQuietly(connectionToSeqware);
        }
        return result;
    }

    /**
     * <p>
     * dropDatabase.
     * </p>
     * 
     * @throws java.sql.SQLException
     *             if any.
     */
    public void dropDatabase() throws SQLException {
        try (Connection connectionToPostgres = createConnection(getPOSTGRE_DB(), getSEQWARE_USER(), getSEQWARE_PASSWORD())) {
            unLoadDatabase(connectionToPostgres);
        } catch (Exception e) {
            logger.info("TestDatabaseCreator.dropDatabase" + e.getMessage());
        }
    }

    /**
     * Drop a database schema even when users are connected to it
     * 
     * @throws java.sql.SQLException
     *             if any.
     */
    public void dropDatabaseWithUsers() throws SQLException {
        try (Connection connectionToPostgres = createConnection(getSEQWARE_DB(), getSEQWARE_USER(), getSEQWARE_PASSWORD())) {
            connectionToPostgres.createStatement().execute("drop schema if exists public cascade;");
            connectionToPostgres.createStatement().execute("create schema public;");
        } catch (Exception e) {
            logger.info("TestDatabaseCreator.dropDatabaseWithUsers" + e.getMessage());
        }
    }

    /**
     * <p>
     * markDatabaseChanged.
     * </p>
     */
    public static void markDatabaseChanged() {
        database_changed = true;
    }

    private void unLoadDatabase(Connection connection) throws SQLException {
        connection.createStatement().execute("DROP DATABASE IF EXISTS " + getSEQWARE_DB() + ";");
    }

    private Connection createConnection(String databaseName, String userName, String password) throws Exception {
        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            throw new Exception("Where is your PostgreSQL JDBC Driver? Include in your library path!");

        }

        try {

            return DriverManager.getConnection("jdbc:postgresql://" + getDEFAULT_DB_HOST() + ":" + getDEFAULT_DB_PORT() + "/" + databaseName, userName, password);

        } catch (SQLException e) {

            throw new Exception("Connection Failed! Check output console " + e);

        }
    }

    private static void loadDBStructure(Connection connection) throws SQLException {
        loadDBStructure(connection, true);
    }

    private static void loadDBStructure(Connection connection, boolean loadTestingData) throws SQLException {
        System.out.println("----------------Loading dump into PostgreSQL--------------------");
        try {
            System.out.println("Loading schema");
            connection.createStatement().execute(getClassPathFileToString("seqware_meta_db.sql")
                    .replaceAll("OWNER TO seqware;", "OWNER TO " + connection.getMetaData().getUserName() + ";"));
            if (loadTestingData) {
                System.out.println("Loading testing data");
                connection.createStatement().execute(getClassPathFileToString("seqware_meta_db_testdata.sql"));
            } else {
                System.out.println("Loading basic data");
                connection.createStatement().execute(getClassPathFileToString("seqware_meta_db_data.sql"));
            }
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(TestDatabaseCreator.class);
            logger.error("could not load testing database", e);
        }
        System.out.println("----------------Dump Loaded--------------------");
    }

    private static String getClassPathFileToString(String path) throws IOException {
        InputStream in = TestDatabaseCreator.class.getResourceAsStream(path);
        StringBuilder fileData = new StringBuilder(1000);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
        }
        return fileData.toString();
    }

    /**
     * @return the POSTGRE_DB
     */
    protected static String getPOSTGRE_DB() {
        return POSTGRE_DB;
    }

    /**
     * @return the SEQWARE_DB
     */
    protected String getSEQWARE_DB() {
        return SEQWARE_DB;
    }

    /**
     * @return the SEQWARE_USER
     */
    protected String getSEQWARE_USER() {
        return SEQWARE_USER;
    }

    /**
     * @return the SEQWARE_PASSWORD
     */
    protected String getSEQWARE_PASSWORD() {
        return SEQWARE_PASSWORD;
    }

    /**
     * Unfortunately, postgres does not allow the straight dropdb and createdb when tomcat is used (perhaps we leave open a connection)
     */
    protected void basicResetDatabaseWithUsers() {
        basicResetDatabaseWithUsers(true);
    }

    /**
     * Unfortunately, postgres does not allow the straight dropdb and createdb when tomcat is used (perhaps we leave open a connection)
     * 
     * @param loadTestingData
     */
    protected void basicResetDatabaseWithUsers(boolean loadTestingData) {
        try {
            this.dropDatabaseWithUsers();
            TestDatabaseCreator.markDatabaseChanged();
            this.createDatabase(loadTestingData);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
