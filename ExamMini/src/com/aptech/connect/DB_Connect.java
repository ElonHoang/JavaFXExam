/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connect {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "2002dev";
    private static final String CONN_STRING
            = "jdbc:mysql://localhost/Java2";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

    }

    public static Connection getConnection(DB_Type type) throws SQLException {
        switch (type) {
            case SQL:
                break;
            case SQLite:
//                return getConnectionSqlLite();
                break;
            default:
                return getConnection();
        }
        return getConnection();
    }
}
