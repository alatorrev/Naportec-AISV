/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.logica;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * ESTA CLASE FUE UTILIZADA CUANDO SE USO TOMCAT.
 * AHORA NO ES USADA POR NINGUN PROCESO
 * @author Fernando
 */
public class EntityManagerSecurity {

    private final EntityManager entityManager;

    private EntityManagerSecurity() {
//        Properties propiedades = new ArchivoConfiguracion("naportec", "config").obtenerArchivo();
        EntityManagerFactory factory = javax.persistence.Persistence.createEntityManagerFactory("NaportecPU");
        entityManager = factory.createEntityManager();
    }

    public static EntityManagerSecurity getInstance() {
        return EntityManagerSecurityHolder.INSTANCE;
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    private static class EntityManagerSecurityHolder {

        private static final EntityManagerSecurity INSTANCE = new EntityManagerSecurity();
    }
}
