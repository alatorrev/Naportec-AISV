/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.aisv.entidades;

import com.naportec.seguridad.entidades.SUser;
import com.naportec.utilidades.enumeraciones.Estado;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "aisv_transaccion_imp_exp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.countIngresos", query = "SELECT COUNT(t) FROM Transaccion t WHERE t.ingresoRochoTrans=true AND t.salidaRochoTrans=false AND t.estado='Activo'"),
    @NamedQuery(name = "Transaccion.findByCodigoTrans", query = "SELECT t FROM Transaccion t WHERE t.codigoTrans = :codigoTrans"),
    @NamedQuery(name = "Transaccion.findByPrecarga", query = "SELECT t FROM Transaccion t WHERE t.codigoPrec = :codigoPrec AND t.estado != 'Anulado'"),
    @NamedQuery(name = "Transaccion.findByContenedorTrans", query = "SELECT t FROM Transaccion t WHERE t.contenedorTrans = :contenedorTrans"),
    @NamedQuery(name = "Transaccion.findBySelloUnoTrans", query = "SELECT t FROM Transaccion t WHERE t.selloUnoTrans = :selloUnoTrans"),
    @NamedQuery(name = "Transaccion.findBySelloDosTrans", query = "SELECT t FROM Transaccion t WHERE t.selloDosTrans = :selloDosTrans"),
    @NamedQuery(name = "Transaccion.findByDaesTrans", query = "SELECT t FROM Transaccion t WHERE t.daesTrans = :daesTrans"),
    @NamedQuery(name = "Transaccion.findByFechaSalidaTrans", query = "SELECT t FROM Transaccion t WHERE t.fechaSalidaTrans = :fechaSalidaTrans"),
    @NamedQuery(name = "Transaccion.findByPropositoCargaTrans", query = "SELECT t FROM Transaccion t WHERE t.propositoCargaTrans = :propositoCargaTrans"),
    @NamedQuery(name = "Transaccion.findByCupoCargaTrans", query = "SELECT t FROM Transaccion t WHERE t.cupoCargaTrans = :cupoCargaTrans"),
    @NamedQuery(name = "Transaccion.findByNumeroCajasTrans", query = "SELECT t FROM Transaccion t WHERE t.numeroCajasTrans = :numeroCajasTrans"),
    @NamedQuery(name = "Transaccion.findByNombreTrasnportistaTrans", query = "SELECT t FROM Transaccion t WHERE t.nombreTrasnportistaTrans = :nombreTrasnportistaTrans"),
    @NamedQuery(name = "Transaccion.findByCedulaTrasnportistaTrans", query = "SELECT t FROM Transaccion t WHERE t.cedulaTrasnportistaTrans = :cedulaTrasnportistaTrans"),
    @NamedQuery(name = "Transaccion.findByPlacaTrasnportistaTrans", query = "SELECT t FROM Transaccion t WHERE t.placaTrasnportistaTrans = :placaTrasnportistaTrans"),
    @NamedQuery(name = "Transaccion.findByGuiaRemisionTrans", query = "SELECT t FROM Transaccion t WHERE t.guiaRemisionTrans = :guiaRemisionTrans"),
    @NamedQuery(name = "Transaccion.findByEstadoTrans", query = "SELECT t FROM Transaccion t WHERE t.estado = :estadoTrans")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_trans")
    private Long codigoTrans;
    @Column(name = "contenedor_trans")
    private String contenedorTrans;
    @Column(name = "sello_uno_trans")
    private String selloUnoTrans;
    @Column(name = "sello_dos_trans")
    private String selloDosTrans;
    @Column(name = "sello_tres_trans")
    private String selloTresTrans;
    @Column(name = "sello_cuatro_trans")
    private String selloCuatroTrans;
    @Column(name = "daes_trans")
    private String daesTrans;
    @Column(name = "fecha_salida_trans")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalidaTrans;
    @Column(name = "cupo_carga_trans")
    private boolean cupoCargaTrans;
    @Column(name = "habilitado_trans")
    private boolean habilitadoTrans;
    @Column(name = "numero_cajas_trans")
    private Integer numeroCajasTrans;
    @Column(name = "nombre_trasnportista_trans")
    private String nombreTrasnportistaTrans;
    @Column(name = "cedula_trasnportista_trans")
    private String cedulaTrasnportistaTrans;
    @Column(name = "placa_trasnportista_trans")
    private String placaTrasnportistaTrans;
    @Column(name = "guia_remision_trans")
    private String guiaRemisionTrans;
    @Column(name = "nombre_retira_trans")
    private String nombreRetiraTrans;
    @Column(name = "ciudad_planta_trans")
    private String ciudadPlantaTrans;
    @Column(name = "direccion_planta_trans")
    private String direccionPlantaTrans;
    @Column(name = "cedula_retira_trans")
    private String cedulaRetiraTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion_trans")
    private Date fechaCreacinTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inspeccion_trans")
    private Date fechaInspeccionTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inspeccion_m_trans")
    private Date fechaInspeccionMTrans;
    @Column(name = "estado_trans")
    private String estado;
    @Column(name = "estado_pan")
    private String estadoPan;
    @Column(name = "peso_neto_trans")
    private Double pesoNetoTrans;
    @Column(name = "tara_trans")
    private Double taraTrans;
    @Column(name = "eir_trans")
    private String eirTrans;
    @Column(name = "peso_bascula_trans")
    private Double pesoBasculaTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_eir_trans")
    private Date fechaEirTrans;
    @Column(name = "fecha_preembarque_trans")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPreembarqueTrans;
    @Column(name = "preembarque_trans")
    private Boolean preembarqueTrans;
    @Size(max = 15)
    @Column(name = "uva_trans")
    private String uvaTrans;
    @Size(max = 50)
    @Column(name = "puerto_origen_trans")
    private String puertoOrigenTrans;
    @Size(max = 50)
    @Column(name = "puerto_trasbordo_trans")
    private String puertoTrasbordoTrans;
    @Size(max = 50)
    @Column(name = "puerto_final_trans")
    private String puertoFinalTrans;
    @Size(max = 50)
    @Column(name = "puerto_destino_trans")
    private String puertoDestinoTrans;
    @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    @ManyToOne
    private SUser usrId;
    @JoinColumn(name = "codigo_prec", referencedColumnName = "codigo_prec")
    @ManyToOne
    private Precarga codigoPrec;
    @JoinColumn(name = "proposito_carga_trans", referencedColumnName = "codigo_prop")
    @ManyToOne
    private PropositoCarga propositoCargaTrans;
    @JoinColumn(name = "embalajeTrans", referencedColumnName = "codigo_emba")
    @ManyToOne
    private Embalaje embalajeTrans;
    @OneToMany(mappedBy = "codigoTrans", cascade = CascadeType.REFRESH)
    private List<Movimiento> movimientoList;
    @Column(name = "autorizacion_peso_trans")
    private Boolean autorizacionPesoTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_retiro_trans")
    private Date fechaRetiroTrans = new Date();
    @JoinColumn(name = "num_factura_trans",referencedColumnName = "numero_pago")
    private Pagos codigo_pago;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ingreso_rocho_trans")
    private Date ingresoRochoTrans;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "salida_rocho_trans")
    private Date salidaRochoTrans;
    @Transient
    private Date fechaIngresoPuerto;
    @Transient
    private Date fechaCreacion;
    @Transient
    private Date fechaRevisionPan;
    @Transient
    private String correos;
    @Transient
    private String tipo;
    @Transient
    private String estadoTran;
    @Transient
    private Date fechaAprobacion;
    @Transient
    private Date fechaInspeccionVista;
    @Transient
    private boolean diferenciaPeso;
    @Transient
    private Double difFechas;
    @Transient
    private String sellos;
    @Transient
    private String usuarioBloqueo;
    @Transient
    private String usuarioDesbloqueo;
    @Transient
    private Date fechaBloqueo;
    @Transient
    private Date fechaDesbloqueo;
    @Transient
    private String usuarioBloqueoPre;
    @Transient
    private String usuarioDesbloqueoPre;
    @Transient
    private Date fechaBloqueoPre;
    @Transient
    private Date fechaDesbloqueoPre;
    @Transient
    private String documentoIdentificador;

    public Transaccion() {
        this.codigo_pago = new Pagos();
    }

    public Transaccion(Long codigoTrans) {
        this.codigo_pago = new Pagos();
        this.codigoTrans = codigoTrans;
    }

    public Long getCodigoTrans() {
        return codigoTrans;
    }

    public void setCodigoTrans(Long codigoTrans) {
        this.codigoTrans = codigoTrans;
    }

    public String getContenedorTrans() {
        if (contenedorTrans != null) {
            contenedorTrans = contenedorTrans.toUpperCase();
        }
        return contenedorTrans;
    }

    public void setContenedorTrans(String contenedorTrans) {
        if (contenedorTrans != null) {
            this.contenedorTrans = contenedorTrans.toUpperCase();
        }
        this.contenedorTrans = contenedorTrans;
    }

    public String getSelloUnoTrans() {
        return selloUnoTrans;
    }

    public void setSelloUnoTrans(String selloUnoTrans) {
        this.selloUnoTrans = selloUnoTrans;
    }

    public String getSelloDosTrans() {
        return selloDosTrans;
    }

    public void setSelloDosTrans(String selloDosTrans) {
        this.selloDosTrans = selloDosTrans;
    }

    public String getDaesTrans() {
        return daesTrans;
    }

    public void setDaesTrans(String daesTrans) {
        this.daesTrans = daesTrans;
    }

    public Date getFechaSalidaTrans() {
        return fechaSalidaTrans;
    }

    public void setFechaSalidaTrans(Date fechaSalidaTrans) {
        this.fechaSalidaTrans = fechaSalidaTrans;
    }

    public boolean getCupoCargaTrans() {
        return cupoCargaTrans;
    }

    public void setCupoCargaTrans(boolean cupoCargaTrans) {
        this.cupoCargaTrans = cupoCargaTrans;
    }

    public Integer getNumeroCajasTrans() {
        return numeroCajasTrans;
    }

    public void setNumeroCajasTrans(Integer numeroCajasTrans) {
        this.numeroCajasTrans = numeroCajasTrans;
    }

    public String getNombreTrasnportistaTrans() {
        return nombreTrasnportistaTrans;
    }

    public void setNombreTrasnportistaTrans(String nombreTrasnportistaTrans) {
        this.nombreTrasnportistaTrans = nombreTrasnportistaTrans;
    }

    public String getCedulaTrasnportistaTrans() {
        return cedulaTrasnportistaTrans;
    }

    public void setCedulaTrasnportistaTrans(String cedulaTrasnportistaTrans) {
        this.cedulaTrasnportistaTrans = cedulaTrasnportistaTrans;
    }

    public String getPlacaTrasnportistaTrans() {
        return placaTrasnportistaTrans;
    }

    public void setPlacaTrasnportistaTrans(String placaTrasnportistaTrans) {
        this.placaTrasnportistaTrans = placaTrasnportistaTrans;
    }

    public String getGuiaRemisionTrans() {
        return guiaRemisionTrans;
    }

    public void setGuiaRemisionTrans(String guiaRemisionTrans) {
        this.guiaRemisionTrans = guiaRemisionTrans;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SUser getUsrId() {
        return usrId;
    }

    public void setUsrId(SUser usrId) {
        this.usrId = usrId;
    }

    public Precarga getCodigoPrec() {
        return codigoPrec;
    }

    public void setCodigoPrec(Precarga codigoPrec) {
        this.codigoPrec = codigoPrec;
    }

    public Pagos getCodigo_pago() {
        return codigo_pago;
    }

    public void setCodigo_pago(Pagos codigo_pago) {
        this.codigo_pago = codigo_pago;
    }
    
    @XmlTransient
    public List<Movimiento> getMovimientoList() {
        return movimientoList;
    }

    public void setMovimientoList(List<Movimiento> movimientoList) {
        this.movimientoList = movimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTrans != null ? codigoTrans.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.codigoTrans == null && other.codigoTrans != null) || (this.codigoTrans != null && !this.codigoTrans.equals(other.codigoTrans))) {
            return false;
        }
        return true;
    }

    /**
     * @return the fechaCreacinTrans
     */
    public Date getFechaCreacinTrans() {
        if (fechaCreacinTrans == null) {
            if (this.getFechaCreacion() != null) {
                this.fechaCreacinTrans = this.getFechaCreacion();
            }
        }
        return fechaCreacinTrans;
    }

    /**
     * @param fechaCreacinTrans the fechaCreacinTrans to set
     */
    public void setFechaCreacinTrans(Date fechaCreacinTrans) {
        this.fechaCreacinTrans = fechaCreacinTrans;
    }

    /**
     * @return the fechaInspeccionTrans
     */
    public Date getFechaInspeccionTrans() {
        return fechaInspeccionTrans;
    }

    /**
     * @param fechaInspeccionTrans the fechaInspeccionTrans to set
     */
    public void setFechaInspeccionTrans(Date fechaInspeccionTrans) {
        this.fechaInspeccionTrans = fechaInspeccionTrans;
    }

    /**
     * @return the fechaIngresoPuerto
     */
    public Date getFechaIngresoPuerto() {
        if (this.movimientoList != null) {
            for (Movimiento m : this.movimientoList) {
                if (m.getTipoMovi().equals(Estado.Aprobado.getName().toUpperCase())) {
                    this.fechaIngresoPuerto = m.getFechaMovi();
                    break;
                }
            }
        }
        return fechaIngresoPuerto;
    }

    /**
     * @param fechaIngresoPuerto the fechaIngresoPuerto to set
     */
    public void setFechaIngresoPuerto(Date fechaIngresoPuerto) {
        this.fechaIngresoPuerto = fechaIngresoPuerto;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        if (this.movimientoList != null) {
            for (Movimiento m : this.movimientoList) {
                if (m.getTipoMovi().equals(Estado.Creacion.getName().toUpperCase())) {
                    this.fechaCreacion = m.getFechaMovi();
                    break;
                }
            }
        }
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaRevisionPan
     */
    public Date getFechaRevisionPan() {
        if (this.movimientoList != null) {
            for (Movimiento m : this.movimientoList) {
                if (m.getTipoMovi().equals(Estado.bloqueoPan.getName().toUpperCase())) {
                    this.fechaRevisionPan = m.getFechaMovi();
                    break;
                }
            }
        }
        //CUANDO SE PONE FECHA DE BLOQUEO DE PAN EN LA PANTALLA BLOQUEAR PAN SOLO PUEDEN BLOAQUEAR A LOS APROBADOS
        //Y CUANDOYA SE DESBLOQUEA VUELVEN A ESTAR APROBADOS.
        return fechaRevisionPan;
    }

    /**
     * @param fechaRevisionPan the fechaRevisionPan to set
     */
    public void setFechaRevisionPan(Date fechaRevisionPan) {
        this.fechaRevisionPan = fechaRevisionPan;
    }

    /**
     * @return the nombreRetiraTrans
     */
    public String getNombreRetiraTrans() {
        return nombreRetiraTrans;
    }

    /**
     * @param nombreRetiraTrans the nombreRetiraTrans to set
     */
    public void setNombreRetiraTrans(String nombreRetiraTrans) {
        this.nombreRetiraTrans = nombreRetiraTrans;
    }

    /**
     * @return the cedulaRetiraTrans
     */
    public String getCedulaRetiraTrans() {
        return cedulaRetiraTrans;
    }

    /**
     * @param cedulaRetiraTrans the cedulaRetiraTrans to set
     */
    public void setCedulaRetiraTrans(String cedulaRetiraTrans) {
        this.cedulaRetiraTrans = cedulaRetiraTrans;
    }

    /**
     * @return the pesoNetoTrans
     */
    public Double getPesoNetoTrans() {
        if (pesoNetoTrans == null) {
            pesoNetoTrans = 0d;
        }
        return pesoNetoTrans;
    }

    /**
     * @param pesoNetoTrans the pesoNetoTrans to set
     */
    public void setPesoNetoTrans(Double pesoNetoTrans) {
        this.pesoNetoTrans = pesoNetoTrans;
    }

    /**
     * @return the eirTrans
     */
    public String getEirTrans() {
        return eirTrans;
    }

    /**
     * @param eirTrans the eirTrans to set
     */
    public void setEirTrans(String eirTrans) {
        this.eirTrans = eirTrans;
    }

    /**
     * @return the fechaEirTrans
     */
    public Date getFechaEirTrans() {
        return fechaEirTrans;
    }

    /**
     * @param fechaEirTrans the fechaEirTrans to set
     */
    public void setFechaEirTrans(Date fechaEirTrans) {
        this.fechaEirTrans = fechaEirTrans;
    }

    /**
     * @return the habilitadoTrans
     */
    public boolean getHabilitadoTrans() {
        return habilitadoTrans;
    }

    /**
     * @param habilitadoTrans the habilitadoTrans to set
     */
    public void setHabilitadoTrans(boolean habilitadoTrans) {
        this.habilitadoTrans = habilitadoTrans;
    }

    /**
     * @return the correos
     */
    public String getCorreos() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getUsrId().getCodigoSoli().getCorreoSoli());
        sb.append(";");
        sb.append(this.getCodigoPrec().getIdLineaPrec().getCorreoNotifNavi());
        sb.append(";");
        correos = sb.toString();
        return correos;
    }

    /**
     * @param correos the correos to set
     */
    public void setCorreos(String correos) {
        this.correos = correos;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        if (usrId.getCodigoSoli() != null) {
            tipo = usrId.getCodigoSoli().getTipo();
            if (tipo.startsWith("IMPOR")) {
                if (codigoPrec.getTipoPrec().equals("I")) {
                    tipo = "IMPORTADOR";
                } else {
                    tipo = "EXPORTADOR";
                }
            }
        }
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the estadoTran
     */
    public String getEstadoTran() {
        this.estadoTran = this.getEstado();
        return estadoTran;
    }

    /**
     * @param estadoTran the estadoTran to set
     */
    public void setEstadoTran(String estadoTran) {
        this.estadoTran = estadoTran;
    }

    /**
     * @return the fechaInspeccionMTrans
     */
    public Date getFechaInspeccionMTrans() {
        if (fechaInspeccionMTrans == null) {
            if (this.getFechaAprobacion() != null) {
                fechaInspeccionMTrans = this.getFechaAprobacion();
            }
        }
        return fechaInspeccionMTrans;
    }

    /**
     * @param fechaInspeccionMTrans the fechaInspeccionMTrans to set
     */
    public void setFechaInspeccionMTrans(Date fechaInspeccionMTrans) {
        this.fechaInspeccionMTrans = fechaInspeccionMTrans;
    }

    /**
     * @return the estadoPan
     */
    public String getEstadoPan() {
        return estadoPan;
    }

    /**
     * @param estadoPan the estadoPan to set
     */
    public void setEstadoPan(String estadoPan) {
        this.estadoPan = estadoPan;
    }

    /**
     * @return the fechaAprobacion
     */
    public Date getFechaAprobacion() {
        if (this.movimientoList != null) {
            for (Movimiento m : this.movimientoList) {
                if (m.getTipoMovi().equals("APROBADO")) {
                    this.fechaAprobacion = m.getFechaMovi();
                    break;
                }
            }
        }
        return fechaAprobacion;
    }

    /**
     * @param fechaAprobacion the fechaAprobacion to set
     */
    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    /**
     * @return the selloTresTrans
     */
    public String getSelloTresTrans() {
        return selloTresTrans;
    }

    /**
     * @param selloTresTrans the selloTresTrans to set
     */
    public void setSelloTresTrans(String selloTresTrans) {
        this.selloTresTrans = selloTresTrans;
    }

    /**
     * @return the selloCuatroTrans
     */
    public String getSelloCuatroTrans() {
        return selloCuatroTrans;
    }

    /**
     * @param selloCuatroTrans the selloCuatroTrans to set
     */
    public void setSelloCuatroTrans(String selloCuatroTrans) {
        this.selloCuatroTrans = selloCuatroTrans;
    }

    /**
     * @return the propositoCargaTrans
     */
    public PropositoCarga getPropositoCargaTrans() {
        return propositoCargaTrans;
    }

    /**
     * @param propositoCargaTrans the propositoCargaTrans to set
     */
    public void setPropositoCargaTrans(PropositoCarga propositoCargaTrans) {
        this.propositoCargaTrans = propositoCargaTrans;
    }

    /**
     * @return the pesoBasculaTrans
     */
    public Double getPesoBasculaTrans() {
        if (pesoBasculaTrans == null) {
            pesoBasculaTrans = 0d;
        }
        return pesoBasculaTrans;
    }

    /**
     * @param pesoBasculaTrans the pesoBasculaTrans to set
     */
    public void setPesoBasculaTrans(Double pesoBasculaTrans) {
        this.pesoBasculaTrans = pesoBasculaTrans;
    }

    /**
     * @return the ciudadPlantaTrans
     */
    public String getCiudadPlantaTrans() {
        return ciudadPlantaTrans;
    }

    /**
     * @param ciudadPlantaTrans the ciudadPlantaTrans to set
     */
    public void setCiudadPlantaTrans(String ciudadPlantaTrans) {
        this.ciudadPlantaTrans = ciudadPlantaTrans;
    }

    /**
     * @return the direccionPlantaTrans
     */
    public String getDireccionPlantaTrans() {
        return direccionPlantaTrans;
    }

    /**
     * @param direccionPlantaTrans the direccionPlantaTrans to set
     */
    public void setDireccionPlantaTrans(String direccionPlantaTrans) {
        this.direccionPlantaTrans = direccionPlantaTrans;
    }

    /**
     * @return the fechaPreembarqueTrans
     */
    public Date getFechaPreembarqueTrans() {
        return fechaPreembarqueTrans;
    }

    /**
     * @param fechaPreembarqueTrans the fechaPreembarqueTrans to set
     */
    public void setFechaPreembarqueTrans(Date fechaPreembarqueTrans) {
        this.fechaPreembarqueTrans = fechaPreembarqueTrans;
    }

    /**
     * @return the preembarqueTrans
     */
    public Boolean getPreembarqueTrans() {
        return preembarqueTrans;
    }

    /**
     * @param preembarqueTrans the preembarqueTrans to set
     */
    public void setPreembarqueTrans(Boolean preembarqueTrans) {
        this.preembarqueTrans = preembarqueTrans;
    }

    /**
     * @return the uvaTrans
     */
    public String getUvaTrans() {
        return uvaTrans;
    }

    /**
     * @param uvaTrans the uvaTrans to set
     */
    public void setUvaTrans(String uvaTrans) {
        this.uvaTrans = uvaTrans;
    }

    /**
     * @return the puertoOrigenTrans
     */
    public String getPuertoOrigenTrans() {
        return puertoOrigenTrans;
    }

    /**
     * @param puertoOrigenTrans the puertoOrigenTrans to set
     */
    public void setPuertoOrigenTrans(String puertoOrigenTrans) {
        this.puertoOrigenTrans = puertoOrigenTrans;
    }

    /**
     * @return the puertoTrasbordoTrans
     */
    public String getPuertoTrasbordoTrans() {
        return puertoTrasbordoTrans;
    }

    /**
     * @param puertoTrasbordoTrans the puertoTrasbordoTrans to set
     */
    public void setPuertoTrasbordoTrans(String puertoTrasbordoTrans) {
        this.puertoTrasbordoTrans = puertoTrasbordoTrans;
    }

    /**
     * @return the puertoDestinoTrans
     */
    public String getPuertoDestinoTrans() {
        return puertoDestinoTrans;
    }

    /**
     * @param puertoDestinoTrans the puertoDestinoTrans to set
     */
    public void setPuertoDestinoTrans(String puertoDestinoTrans) {
        this.puertoDestinoTrans = puertoDestinoTrans;
    }

    /**
     * @return the embalajeTrans
     */
    public Embalaje getEmbalajeTrans() {
        return embalajeTrans;
    }

    /**
     * @param embalajeTrans the embalajeTrans to set
     */
    public void setEmbalajeTrans(Embalaje embalajeTrans) {
        this.embalajeTrans = embalajeTrans;
    }

    /**
     * @return the fechaInspeccionVista
     */
    public Date getFechaInspeccionVista() {
        if (estado.equals(Estado.Aprobado.name())) {
            if (fechaInspeccionTrans != null) {
                this.fechaInspeccionVista = this.fechaInspeccionTrans;
            }
        }
        return fechaInspeccionVista;
    }

    /**
     * @param fechaInspeccionVista the fechaInspeccionVista to set
     */
    public void setFechaInspeccionVista(Date fechaInspeccionVista) {
        this.fechaInspeccionVista = fechaInspeccionVista;
    }

    /**
     * @return the diferenciaPeso
     */
    public boolean getDiferenciaPeso() {
        if (this.getCodigoPrec().getPesoPrec() != null && this.getPesoBasculaTrans() != null) {
            if (this.getCodigoPrec().getPesoPrec() > 0d) {
                double aux = this.getCodigoPrec().getPesoPrec() - this.getPesoBasculaTrans();
                double porcentaje = this.getPesoBasculaTrans() * 0.10;
                if (aux < 0) {
                    aux = aux * -1;
                }
                if (porcentaje > aux) {
                    diferenciaPeso = true;
                } else {
                    diferenciaPeso = false;
                }
            } else {
                diferenciaPeso = false;
            }
        } else {
            diferenciaPeso = false;
        }
        return diferenciaPeso;
    }

    /**
     * @param diferenciaPeso the diferenciaPeso to set
     */
    public void setDiferenciaPeso(boolean diferenciaPeso) {
        this.diferenciaPeso = diferenciaPeso;
    }

    /**
     * @return the taraTrans
     */
    public Double getTaraTrans() {
        return taraTrans;
    }

    /**
     * @param taraTrans the taraTrans to set
     */
    public void setTaraTrans(Double taraTrans) {
        this.taraTrans = taraTrans;
    }

    /**
     * @return the difFechas
     */
    public Double getDifFechas() {
        if (this.getFechaEirTrans() != null && this.getFechaSalidaTrans() != null) {
            double MILLSECS_PER_HOUR = 60 * 60 * 1000; //Milisegundos por hora
            difFechas = (((double) this.getFechaEirTrans().getTime() - (double) this.getFechaSalidaTrans().getTime()) / MILLSECS_PER_HOUR);
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat format = new DecimalFormat("####.##", simbolos);
            difFechas = Double.parseDouble(format.format(difFechas));
        }
        return difFechas;
    }

    /**
     * @param difFechas the difFechas to set
     */
    public void setDifFechas(Double difFechas) {
        this.difFechas = difFechas;
    }

    /**
     * @return the sellos
     */
    public String getSellos() {
        StringBuilder sb = new StringBuilder();
        if (selloUnoTrans != null) {
            sb.append(selloUnoTrans);
        }
        if (selloDosTrans != null) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(selloDosTrans);
        }
        if (selloTresTrans != null) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(selloTresTrans);
        }
        if (selloCuatroTrans != null) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(selloCuatroTrans);
        }
        sellos = sb.toString();
        return sellos;
    }

    /**
     * @param sellos the sellos to set
     */
    public void setSellos(String sellos) {
        this.sellos = sellos;
    }

    /**
     * @return the usuarioBloqueo
     */
    public String getUsuarioBloqueo() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.bloqueoPan.name().toUpperCase())) {
                this.usuarioBloqueo = m.getDescripcionMovi();
            }
        }
        return usuarioBloqueo;
    }

    /**
     * @param usuarioBloqueo the usuarioBloqueo to set
     */
    public void setUsuarioBloqueo(String usuarioBloqueo) {
        this.usuarioBloqueo = usuarioBloqueo;
    }

    /**
     * @return the usuarioDesbloqueo
     */
    public String getUsuarioDesbloqueo() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.DesbloqueoPan.name().toUpperCase())) {
                this.usuarioDesbloqueo = m.getDescripcionMovi();
            }
        }
        return usuarioDesbloqueo;
    }

    /**
     * @param usuarioDesbloqueo the usuarioDesbloqueo to set
     */
    public void setUsuarioDesbloqueo(String usuarioDesbloqueo) {
        this.usuarioDesbloqueo = usuarioDesbloqueo;
    }

    /**
     * @return the fechaBloqueo
     */
    public Date getFechaBloqueo() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.bloqueoPan.name().toUpperCase())) {
                this.fechaBloqueo = m.getFechaMovi();
            }
        }
        return fechaBloqueo;
    }

    /**
     * @param fechaBloqueo the fechaBloqueo to set
     */
    public void setFechaBloqueo(Date fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    /**
     * @return the fechaDesbloqueo
     */
    public Date getFechaDesbloqueo() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.DesbloqueoPan.name().toUpperCase())) {
                this.fechaDesbloqueo = m.getFechaMovi();
            }
        }
        return fechaDesbloqueo;
    }

    /**
     * @param fechaDesbloqueo the fechaDesbloqueo to set
     */
    public void setFechaDesbloqueo(Date fechaDesbloqueo) {
        this.fechaDesbloqueo = fechaDesbloqueo;
    }

    /**
     * @return the usuarioBloqueoPre
     */
    public String getUsuarioBloqueoPre() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.bloqueoPreembarque.name().toUpperCase())) {
                this.usuarioBloqueoPre = m.getDescripcionMovi();
            }
        }
        return usuarioBloqueoPre;
    }

    /**
     * @param usuarioBloqueoPre the usuarioBloqueoPre to set
     */
    public void setUsuarioBloqueoPre(String usuarioBloqueoPre) {
        this.usuarioBloqueoPre = usuarioBloqueoPre;
    }

    /**
     * @return the usuarioDesbloqueoPre
     */
    public String getUsuarioDesbloqueoPre() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.DesbloqueoPreembarque.name().toUpperCase())) {
                this.usuarioDesbloqueoPre = m.getDescripcionMovi();
            }
        }
        return usuarioDesbloqueoPre;
    }

    /**
     * @param usuarioDesbloqueoPre the usuarioDesbloqueoPre to set
     */
    public void setUsuarioDesbloqueoPre(String usuarioDesbloqueoPre) {
        this.usuarioDesbloqueoPre = usuarioDesbloqueoPre;
    }

    /**
     * @return the fechaBloqueoPre
     */
    public Date getFechaBloqueoPre() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.bloqueoPreembarque.name().toUpperCase())) {
                this.fechaBloqueoPre = m.getFechaMovi();
            }
        }
        return fechaBloqueoPre;
    }

    /**
     * @param fechaBloqueoPre the fechaBloqueoPre to set
     */
    public void setFechaBloqueoPre(Date fechaBloqueoPre) {
        this.fechaBloqueoPre = fechaBloqueoPre;
    }

    /**
     * @return the fechaDesbloqueoPre
     */
    public Date getFechaDesbloqueoPre() {
        for (Movimiento m : this.movimientoList) {
            if (m.getTipoMovi().equals(Estado.DesbloqueoPreembarque.name().toUpperCase())) {
                this.fechaDesbloqueoPre = m.getFechaMovi();
            }
        }
        return fechaDesbloqueoPre;
    }

    /**
     * @param fechaDesbloqueoPre the fechaDesbloqueoPre to set
     */
    public void setFechaDesbloqueoPre(Date fechaDesbloqueoPre) {
        this.fechaDesbloqueoPre = fechaDesbloqueoPre;
    }

    /**
     * @return the puertoFinalTrans
     */
    public String getPuertoFinalTrans() {
        return puertoFinalTrans;
    }

    /**
     * @param puertoFinalTrans the puertoFinalTrans to set
     */
    public void setPuertoFinalTrans(String puertoFinalTrans) {
        this.puertoFinalTrans = puertoFinalTrans;
    }

    /**
     * @return the autorizacionPesoTrans
     */
    public Boolean getAutorizacionPesoTrans() {
        return autorizacionPesoTrans;
    }

    /**
     * @param autorizacionPesoTrans the autorizacionPesoTrans to set
     */
    public void setAutorizacionPesoTrans(Boolean autorizacionPesoTrans) {
        this.autorizacionPesoTrans = autorizacionPesoTrans;
    }

    /**
     * @return the fechaRetiroTrans
     */
    public Date getFechaRetiroTrans() {
        return fechaRetiroTrans;
    }

    /**
     * @param fechaRetiroTrans the fechaRetiroTrans to set
     */
    public void setFechaRetiroTrans(Date fechaRetiroTrans) {
        this.fechaRetiroTrans = fechaRetiroTrans;
    }

    /**
     * @return the numFacturaTrans
     */
    

    public Date getIngresoRochoTrans() {
        return ingresoRochoTrans;
    }

    public void setIngresoRochoTrans(Date ingresoRochoTrans) {
        this.ingresoRochoTrans = ingresoRochoTrans;
    }

    public Date getSalidaRochoTrans() {
        return salidaRochoTrans;
    }

    public void setSalidaRochoTrans(Date salidaRochoTrans) {
        this.salidaRochoTrans = salidaRochoTrans;
    }

    public String getDocumentoIdentificador() {
        return documentoIdentificador;
    }

    public void setDocumentoIdentificador(String documentoIdentificador) {
        this.documentoIdentificador = documentoIdentificador;
    }

}
