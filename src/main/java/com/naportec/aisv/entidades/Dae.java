/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.naportec.aisv.entidades;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Fernando
 */
public class Dae implements Serializable{
    private int codigo;
    private String primerDato;
    private String segundoDato;
    private String tercerDato;
    private String cuartoDato;
    private String numeroCompleto;

    public Dae(int codigo, String primerDato, String segundoDato, String tercerDato, String cuartoDato) {
        this.codigo = codigo;
        this.primerDato = primerDato;
        this.segundoDato = segundoDato;
        this.tercerDato = tercerDato;
        this.cuartoDato = cuartoDato;
    }

    public Dae(int codigo, String primerDato, String segundoDato, String tercerDato) {
        this.codigo = codigo;
        this.primerDato = primerDato;
        this.segundoDato = segundoDato;
        this.tercerDato = tercerDato;
    }

    public Dae(String primerDato, String segundoDato, String tercerDato) {
        this.primerDato = primerDato;
        this.segundoDato = segundoDato;
        this.tercerDato = tercerDato;
    }

    public Dae() {
        this.primerDato="028";
        this.segundoDato=("" + (new Date().getYear() + 1900));
        this.tercerDato="40";
        this.cuartoDato="";
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the primerDato
     */
    public String getPrimerDato() {
        return primerDato;
    }

    /**
     * @param primerDato the primerDato to set
     */
    public void setPrimerDato(String primerDato) {
        this.primerDato = primerDato;
    }

    /**
     * @return the segundoDato
     */
    public String getSegundoDato() {
        return segundoDato;
    }

    /**
     * @param segundoDato the segundoDato to set
     */
    public void setSegundoDato(String segundoDato) {
        this.segundoDato = segundoDato;
    }

    /**
     * @return the tercerDato
     */
    public String getTercerDato() {
        return tercerDato;
    }

    /**
     * @param tercerDato the tercerDato to set
     */
    public void setTercerDato(String tercerDato) {
        this.tercerDato = tercerDato;
    }

    /**
     * @return the numeroCompleto
     */
    public String getNumeroCompleto() {
        if(primerDato!=null && segundoDato!=null && cuartoDato!=null && tercerDato!=null){
            numeroCompleto=primerDato+"-"+segundoDato+"-"+tercerDato+"-"+cuartoDato;
        }
        return numeroCompleto;
    }

    /**
     * @param numeroCompleto the numeroCompleto to set
     */
    public void setNumeroCompleto(String numeroCompleto) {
        this.numeroCompleto = numeroCompleto;
    }

    /**
     * @return the cuartoDato
     */
    public String getCuartoDato() {
        return cuartoDato;
    }

    /**
     * @param cuartoDato the cuartoDato to set
     */
    public void setCuartoDato(String cuartoDato) {
        this.cuartoDato = cuartoDato;
    }
    
    
}
