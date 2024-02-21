    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package org.uv.dapp01practica01;

    import java.sql.Connection;

    /**
     *
     * @author zaireko
     */
    public abstract class TransanctionDB <T>{
        protected T p;

        TransanctionDB(T p) {
            this.p = p;
        }

        protected TransanctionDB() {

        }


        public abstract boolean execute(Connection con);
    }