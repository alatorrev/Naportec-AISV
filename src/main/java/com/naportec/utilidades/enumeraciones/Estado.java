package com.naportec.utilidades.enumeraciones;

/**
 * Clase que nos permite manejar estados en la aplicación
 * @author Fernando
 */
public enum Estado {

    Activo, Inactivo, Pendiente, Aprobado, Desaprobado, Suspendido, PenAuto, Anulado,
    Documental, noDocumental, noAprobado, bloqueoPan, edicion, Creacion, DesbloqueoPan,
    bloqueoPreembarque, DesbloqueoPreembarque, IngresoRocho,SalidaRocho,Cancelado;

    public String getName() {
        return this.name();
    }

    public Estado getEstado(String name) {
        if (name.equals(Anulado.getName())) {
            return Anulado;
        }
        if (name.equals(Activo.getName())) {
            return Activo;
        }
        if (name.equals(Inactivo.getName())) {
            return Inactivo;
        }
        if (name.equals(Pendiente.getName())) {
            return Pendiente;
        }
        if (name.equals(Aprobado.getName())) {
            return Aprobado;
        }
        if (name.equals(Desaprobado.getName())) {
            return Desaprobado;
        }
        if (name.equals(Suspendido.getName())) {
            return Suspendido;
        }
        if (name.equals(PenAuto.getName())) {
            return PenAuto;
        }
        if (name.equals(Documental.getName())) {
            return Documental;
        }
        if (name.equals(noDocumental.getName())) {
            return noDocumental;
        }
        if (name.equals(noAprobado.getName())) {
            return noAprobado;
        }
        if (name.equals(bloqueoPan.getName())) {
            return bloqueoPan;
        }
        if (name.equals(edicion.getName())) {
            return edicion;
        }
        if (name.equals(Creacion.getName())) {
            return Creacion;
        }
        if (name.equals(DesbloqueoPan.getName())) {
            return DesbloqueoPan;
        }
        if (name.equals(bloqueoPreembarque.getName())) {
            return bloqueoPreembarque;
        }
        if (name.equals(DesbloqueoPreembarque.getName())) {
            return DesbloqueoPreembarque;
        }
        if (name.equals(Cancelado.getName())) {
            return Cancelado;
        }
        return Activo;
    }

}
