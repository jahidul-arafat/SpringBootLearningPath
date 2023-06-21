package com.example.testDrivenDevelopementWithJUnit.testsuites;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DatastoreSystemHealthTest {

    @Autowired
    DataSource dataSource;
    // This annotation is used to inject the DataSource bean from the Spring application context.
    // The DataSource represents a database connection pool.

    /**
     * Connect to the database, get its metadata i.e. username, password etc
     * Get the Database Name
     * Check if the database name is hanselpetal
     */
    /**
     * Overall, this test class checks the connectivity to the database, retrieves its metadata,
     * and verifies that the database name is as expected.
     * It helps to ensure that the database connection is functioning correctly in the context of
     * the Spring Boot application.
     */
    @Test
    public void dbConnectionIsOk() {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            String catalogName = dataSource.getConnection().getCatalog();

            assertNotNull(metaData);
            assertEquals(catalogName, "hanselpetal");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
