/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.util.List;

/**
 *
 * @author zaireko
 */
public interface IDAOGeneral <T, I>{
    public T guardar(T pojo);
    public T modificar(T pojo, I clave);
    public boolean eliminar(I clave);
    public T buscarById(I id);
    public List<T> buscarAll();
}