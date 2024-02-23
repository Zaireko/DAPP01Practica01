/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

/**
 *
 * @author zaireko
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {
    private static final String BD = "ejemplo";
    private static final String URL = "jdbc:postgresql://localhost:5432/" + BD;
    private static final String USR = "postgres";
    private static final String PWD = "postgres";
    private Connection connection = null;
    private static ConexionDB instance = null;

    private ConexionDB() {
        try {
            connection = DriverManager.getConnection(URL, USR, PWD);
            Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, "SE HA ESTABLECIDO CONEXIÓN CON LA BASE DE DATOS {0}.", BD);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, "NO SE PUDO ESTABLECER LA CONEXIÓN CON LA BASE DE DATOS.", ex);
        }
    }

    public static ConexionDB getInstance() {
        if (instance == null) {
            instance = new ConexionDB();
        }
        
        return instance;
    }

    public boolean execute(TransanctionDB t) {
        return t.execute(connection);
    }

    public <T>List<T> select(SelectionDB<T> sel) {
        return sel.select(connection);
    }

    public void close() {
        try {
            connection.close();
            Logger.getLogger(ConexionDB.class.getName()).log(Level.INFO, "CONEXIÓN CERRADA!");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}