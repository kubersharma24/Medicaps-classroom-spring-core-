package com.kuber.medicapclassrooms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.kuber.medicapclassrooms.utils.Constants.*;

public class DBconnecter {
	
	private static Connection con;
    private DBconnecter(){}
    public static Connection getConnection() {
        if (con == null) {
            try {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return DriverManager.getConnection(CONNECTIONS_URL, USERNAME, PASSWORD);
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        } else {
            return con;
        }
    }

}
