package org.slieer.duckdb;

import org.duckdb.DuckDBConnection;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DuckdbTest {
    @Test
    void testExcelRead() throws ClassNotFoundException, SQLException {
        var cls =Class.forName("org.duckdb.DuckDBDriver");
        //Connection conn = DriverManager.getConnection("jdbc:duckdb:");
        DuckDBConnection conn = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:");

        var sql = """         
                INSTALL spatial;
                LOAD spatial;
                """;
        try (var stmt = conn.createStatement()) {
            stmt.execute(sql);

            var result = stmt.executeQuery("select 1");
        }

    }
}
