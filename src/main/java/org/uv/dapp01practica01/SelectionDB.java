/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author zaireko
 */
public abstract class SelectionDB<T> {
    protected T p;
    
    SelectionDB(T p){
        this.p = p;
    }
     
    public abstract List<T> select(Connection con);
}