package com.contacts.DataBase;

import java.sql.*;

public class DataBase {
    public static final int ODBC = 0;
    public static final int ORACLE = 1;
    public static final int MYSQL = 2;

    private static final String MYSQL_BRIDGE = "jdbc:mysql:";
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String ORACLE_BRIDGE = "jdbc:oracle:thin:";
    private static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";

    private String driver, bridge, dbName;
    private Connection con;
    private DatabaseMetaData dbm;

    private static DataBase db;

    public static DataBase getDBInstance(int type) {
        if (db == null) {
            db = new DataBase(type);
        }
        return db;
    }

    private DataBase(int type) {
        switch (type) {
            case ORACLE:
                driver = ORACLE_DRIVER;
                bridge = ORACLE_BRIDGE;
                break;
            case MYSQL:
                driver = MYSQL_DRIVER;
                bridge = MYSQL_BRIDGE;
                break;
            default:
                throw new IllegalArgumentException("Unsupported database type");
        }

        try {
            System.out.println("Loading driver: " + driver);
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect(String name, String host, int port, String login, String pwd) {
        this.dbName = name;
        String url = bridge + "//" + host + ":" + port + "/" + dbName;
        try {
            con = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection established...");
            dbm = con.getMetaData();
            printInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printInfo() {
        try {
            System.out.println("Driver Name: " + dbm.getDriverName());
            System.out.println("Driver Version: " + dbm.getDriverVersion());
            System.out.println("Catalog: " + con.getCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query) {
        try {
            Statement stmt = con.createStatement();
            if (query.trim().toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsm = rs.getMetaData();
                int columnCount = rsm.getColumnCount();

                // Print column headers
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsm.getColumnName(i) + "\t");
                }
                System.out.println();

                // Print rows
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getString(i) + "\t");
                    }
                    System.out.println();
                }
            } else {
                int rowsAffected = stmt.executeUpdate(query);
                System.out.println("Query executed successfully. Rows affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String table, String[] columns, String[] values) {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("Columns and values must have the same length.");
        }

        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(table).append(" (");
        query.append(String.join(", ", columns));
        query.append(") VALUES (");

        for (int i = 0; i < values.length; i++) {
            query.append("?");
            if (i < values.length - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        try (PreparedStatement pstmt = con.prepareStatement(query.toString())) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i + 1, values[i]);
            }
            pstmt.executeUpdate();
            System.out.println("Insert successful.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error
                System.err.println("Duplicate entry: " + e.getMessage());
                throw new RuntimeException("A record with the same unique field already exists.");
            } else {
                System.err.println("SQL Error: " + e.getMessage());
                throw new RuntimeException("Error during insertion: " + e.getMessage());
            }
        }
    }


    public void update(String table, String[] columns, String[] values, String condition) {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("Columns and values must have the same length.");
        }

        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(table).append(" SET ");

        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }

        query.append(" WHERE ").append(condition);

        try (PreparedStatement pstmt = con.prepareStatement(query.toString())) {
            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i + 1, values[i]);
            }
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Update successful. Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int delete(String table, String condition) {
        String query = "DELETE FROM " + table + " WHERE " + condition;

        try (Statement stmt = con.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                System.err.println("No record found to delete with condition: " + condition);
                throw new RuntimeException("No record found to delete.");
            }
            System.out.println("Delete successful. Rows affected: " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new RuntimeException("Error during deletion: " + e.getMessage());
        }
    }


    public void printTables() {
        try {
            ResultSet rs = dbm.getTables(con.getCatalog(), null, null, new String[]{"TABLE", "VIEW"});
            System.out.println("Tables in database: " + con.getCatalog());
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        return con;
    }
}
