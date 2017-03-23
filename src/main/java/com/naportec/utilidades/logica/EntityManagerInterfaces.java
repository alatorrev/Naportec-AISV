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
public class EntityManagerInterfaces {

    private final EntityManager entityManager;

    private EntityManagerInterfaces() {
//        Properties propiedades = new ArchivoConfiguracion("naportec", "config").obtenerArchivo();
        EntityManagerFactory factory = javax.persistence.Persistence.createEntityManagerFactory("EcuNapPU");
        entityManager = factory.createEntityManager();
    }

    public static EntityManagerInterfaces getInstance() {
        return EntityManagerInterfacesHolder.INSTANCE;
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    private static class EntityManagerInterfacesHolder {

        private static final EntityManagerInterfaces INSTANCE = new EntityManagerInterfaces();
    }
}
