/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.uv.dapp01practica01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author zaireko
 */
public class DAPP01Practica01 {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        try{
            String url="jdbc:postgresql://localhost:5432/ejemplo";
            String usr="postgres";
            String pwd="postgres";
            con =DriverManager.getConnection(url, usr, pwd);
            
            /*
            st = con.createStatement();
            String sql= "insert into empleadotemporal (nombre, direccion, telefono)" + "values ('Aldahir', 'Av. 1', '5555')"; 
            st.execute(sql);
            */
            
            
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, "Se conectó");
        }catch (SQLException ex){
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "No se pudo", ex);
        }
        finally {
            if (st != null){
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error", ex);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error", ex);
                }
            }
        }
    }
}