/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.controladores;

import com.naportec.aisv.logica.PrecargaFacade;
import com.naportec.aisv.entidades.Precarga;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyDModelPrecargaExportacion extends LazyDataModel<Precarga> {

    private final static Logger log = Logger.getLogger(LazyDModelPrecargaExportacion.class.getName());
    private volatile PrecargaFacade facade;
    private int tamanioLista = 0;
    private String rucExportador;
    private String tipoPerfil;
    private String condicionContenedor;

    public LazyDModelPrecargaExportacion(PrecargaFacade facade) {
        super();
        this.facade = facade;
    }

    public LazyDModelPrecargaExportacion() {
        super();
    }

    @Override
    public List<Precarga> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        TablaPrecarga tp=new TablaPrecarga();
        try {
            StringBuilder jpqlBase = new StringBuilder();
            Map<String,Object> jpqlParameters=new HashMap<String, Object>();
            
            jpqlBase.append("SELECT p FROM Precarga p WHERE p.tipoPrec='E' AND p.idItinerarioPrec.fechaZarpeItin >= :fechaZarpe AND p.condicionContenedorPrec=:condicion ");
            if(condicionContenedor.equals("FCL/FCL")){
                jpqlBase.append("AND p.espaciosDisponiblesPrec!=0 ");
            }
            jpqlParameters.put("fechaZarpe",new java.sql.Date(new Date().getTime()));
            jpqlParameters.put("condicion", getCondicionContenedor());
            
            if (!filters.isEmpty()) {
                Set<Map.Entry<String, Object>> entries = filters.entrySet();
                for (Map.Entry<String, Object> filter : entries) {
                    if (filter.getValue().toString().trim().length() != 0) {
                        jpqlBase.append(" AND ");
                        jpqlBase.append("p.").append(filter.getKey()).append(" LIKE ").append(":").append(filter.getKey().replace(".", ""));
                        jpqlParameters.put(filter.getKey().replace(".", ""), "%"+filter.getValue()+"%");
                    }
                }
            }
            StringBuilder jpqlUno = new StringBuilder(jpqlBase);
            jpqlUno.append(" AND p.idLineaPrec.perfilamientoNavi=false");
            StringBuilder jpqlDos = new StringBuilder(jpqlBase.toString().replace("SELECT p FROM Precarga p", "SELECT q FROM Precarga q").replace("p.","q."));
            jpqlDos.append(" AND q.idLineaPrec.perfilamientoNavi=true AND q.impExpIdPrec= :impExpIdPrec");
            StringBuilder jpqlTres = new StringBuilder(jpqlBase.toString().replace("SELECT p FROM Precarga p", "SELECT r FROM Precarga r").replace("p.","r."));
            jpqlTres.append(" AND r.idLineaPrec.perfilamientoNavi=true AND r.impExpIdPrec IN (SELECT c.rucCons FROM Consolidadora c WHERE c.estado='ACTIVO')");
            StringBuilder jpqlUnion=new StringBuilder();
            if(getTipoPerfil().trim().equals("IE")){
                jpqlUnion.append("").append(jpqlUno).append(" UNION ").append(jpqlDos).append(" UNION ").append(jpqlTres).append("");
                jpqlParameters.put("impExpIdPrec", getRucExportador());
            }else{
                jpqlUnion=new StringBuilder(jpqlBase);
            }
            
            tp=facade.loadData(first, pageSize, jpqlUnion.toString(),jpqlUno.toString(),jpqlDos.toString(),jpqlTres.toString(),tipoPerfil, jpqlParameters);
        } catch (Exception ex) {
            Logger.getLogger(LazyDModelPrecargaExportacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        log.log(Level.INFO,tp.getListado().size()+" tamanio de lista");
        log.log(Level.INFO, "list.size():{0}", String.valueOf(tp.getListado().size()));
        if (tp.getListado() == null) {
            setRowCount(0);
            setTamanioLista(0);
        } else {
            setRowCount(tp.getCount());
            setTamanioLista(getRowCount());
        }
        return tp.getListado();
    }

    /**
     * @return the facade
     */
    public PrecargaFacade getFacade() {
        return facade;
    }

    /**
     * @param facade the facade to set
     */
    public void setFacade(PrecargaFacade facade) {
        this.facade = facade;
    }

    /**
     * @return the rucExportador
     */
    public String getRucExportador() {
        return rucExportador;
    }

    /**
     * @param rucExportador the rucExportador to set
     */
    public void setRucExportador(String rucExportador) {
        this.rucExportador = rucExportador;
    }

    /**
     * @return the tipoPerfil
     */
    public String getTipoPerfil() {
        return tipoPerfil;
    }

    /**
     * @param tipoPerfil the tipoPerfil to set
     */
    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    /**
     * @return the condicionContenedor
     */
    public String getCondicionContenedor() {
        return condicionContenedor;
    }

    /**
     * @param condicionContenedor the condicionContenedor to set
     */
    public void setCondicionContenedor(String condicionContenedor) {
        this.condicionContenedor = condicionContenedor;
    }

    /**
     * @return the tamanioLista
     */
    public int getTamanioLista() {
        return tamanioLista;
    }

    /**
     * @param tamanioLista the tamanioLista to set
     */
    public void setTamanioLista(int tamanioLista) {
        this.tamanioLista = tamanioLista;
    }

}
