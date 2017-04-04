/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naportec.utilidades.logica;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

/**
 * Clase Utilidad para la generación de consultas
 *
 * @author Fernando
 */
public class Consulta {

    private StringBuilder sb;
    private List<Filtro> filtros;
    private int filas;
    private String sqlQuery;

    /**
     * Método para inicializar unaconsulta
     *
     * @param clase
     */
    public Consulta(String clase) {
        sb = new StringBuilder();
        sb.append("SELECT x FROM ");
        sb.append(clase);
        sb.append(" x ");
    }

    /**
     * Métodos para agregar Filtors
     *
     * @param fil
     */
    public void agregarFiltrado(List<Filtro> fil) {
        filtros = fil;
        int order = 0;
        int in = 0;
        int ultimoand = 0;
        if (filtros != null) {
            if (filtros.size() > 0) {
                if (filtros.size() > 1 || !filtros.get(0).getTipo().equals("ORDER")) {
                    sb.append(" WHERE ");
                }
                for (Filtro f : filtros) {
                    if (!f.getTipo().equals("ORDER")) {
                        if (!f.getTipo().equals("IN")) {
                            sb.append(f.obtenerConsultaParcial());
                            sb.append(" AND ");
                            ultimoand++;
                        }
                    }
                }
//                if (ultimoand == 1) {
                sb.delete(sb.length() - 4, sb.length());
//                }

                for (Filtro f : filtros) {
                    if (f.getTipo().equals("IN")) {
                        List<String> lista = (List<String>) f.getValue1();
                        in++;
                        if (in == 1) {
                            if (ultimoand > 0) {
                                sb.append("AND ");
                                sb.append(f.obtenerConsultaParcial());
                                sb.append(" ");
                            }
                        } else {
                            int init = sb.length();
                            sb.append(f.obtenerConsultaParcial());
                            sb.replace(init - 2, (init + 2) + (f.getKey1().length() + 5), ",");

                        }
                    }
                }

                for (Filtro f : filtros) {
                    if (f.getTipo().equals("ORDER")) {
                        order++;
                        if (order == 1) {
                            sb.append(f.obtenerConsultaParcial());
                        } else {
                            int init = sb.append(" ").length();
                            sb.append(f.obtenerConsultaParcial());
                            sb.replace(init, init + (f.getTipo().length() + 3), ",");
                        }
                    }
                }
            }
        }
    }

    /**
     * Método para crear una consulta
     *
     * @param em
     * @param first
     * @param count
     * @return
     */
    public Query crearConsulta(EntityManager em, int first, int count) {
        Query q = em.createQuery(sb.toString());
        int orderby = sb.toString().replace("SELECT x", "SELECT Count(x)").indexOf("ORDER");
        String countQuery = "";
        if (orderby == -1) {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)");
        } else {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)").substring(0, orderby - 1);
        }
        Query q1 = em.createQuery(countQuery);
        for (Filtro f : filtros) {
            if (f.getTipo().equals("BETWEENFECHA")) {
                if (f.getKey1().trim().length() != 0 && f.getKey2().trim().length() != 0) {
                    q.setParameter(f.parametroKey1(), f.getValue1());
                    q.setParameter(f.parametroKey2(), f.getValue2());
                    q1.setParameter(f.parametroKey1(), f.getValue1());
                    q1.setParameter(f.parametroKey2(), f.getValue2());
                }
            } else if (!f.getTipo().equals("ORDER")) {
                if (f.getKey1().trim().length() != 0) {
                    if (f.getTipo().equals("LIKE")) {
                        f.setValue1("%" + f.getValue1().toString().toUpperCase() + "%");
                    }
                    if (f.getTipo().equals("SUB")) {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                    } else {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                    }
                }
                if (!f.getTipo().equals("SUB")) {
                    if (f.getKey2().trim().length() != 0) {
                        if (f.getTipo().equals("LIKE")) {
                            f.setValue2(f.getValue2().toString().toUpperCase() + "%");
                        }
                        q.setParameter(f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey2(), f.getValue2());
                    }
                }
            }
        }
        Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
        Record r = new org.eclipse.persistence.sessions.DatabaseRecord();
        DatabaseQuery databaseQuery = ((EJBQueryImpl) q).getDatabaseQuery();
        for (Filtro xd : this.filtros) {
            if (!xd.getTipo().equals("SUB")) {
                r.put(xd.parametroKey1(), xd.getValue1());
            } else {
                r.put(xd.parametroKey1(), xd.getValue1());
                r.put(xd.parametroKey2() + xd.parametroKey2(), xd.getValue2());
            }
        }
        this.sqlQuery = databaseQuery.getTranslatedSQLString(session, r);
        this.filas = Integer.parseInt(q1.getSingleResult().toString());
        System.out.println("SQL QUERY : " + this.sqlQuery);
        q.setMaxResults(count);
        q.setFirstResult(first);
        return q;
    }

    public Object[] generarLazyQuery(EntityManager em, int first, int count) {
        Query q = em.createQuery(sb.toString());
        int orderby = sb.toString().replace("SELECT x", "SELECT Count(x)").indexOf("ORDER");
        String countQuery = "";
        if (orderby == -1) {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)");
        } else {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)").substring(0, orderby - 1);
        }
        Query q1 = em.createQuery(countQuery);
        for (Filtro f : filtros) {
            if (f.getTipo().equals("BETWEENFECHA")) {
                if (f.getKey1().trim().length() != 0 && f.getKey2().trim().length() != 0) {
                    q.setParameter(f.parametroKey1(), f.getValue1());
                    q.setParameter(f.parametroKey2(), f.getValue2());
                    q1.setParameter(f.parametroKey1(), f.getValue1());
                    q1.setParameter(f.parametroKey2(), f.getValue2());
                }
            } else if (!f.getTipo().equals("ORDER")) {
                if (f.getKey1().trim().length() != 0) {
                    if (f.getTipo().equals("LIKE")) {
                        f.setValue1("%" + f.getValue1().toString().toUpperCase() + "%");
                    }
                    if (f.getTipo().equals("SUB")) {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                    } else {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                    }
                }
                if (!f.getTipo().equals("SUB")) {
                    if (f.getKey2().trim().length() != 0) {
                        if (f.getTipo().equals("LIKE")) {
                            f.setValue2(f.getValue2().toString().toUpperCase() + "%");
                        }
                        q.setParameter(f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey2(), f.getValue2());
                    }
                }
            }
        }
        Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
        Record r = new org.eclipse.persistence.sessions.DatabaseRecord();
        DatabaseQuery databaseQuery = ((EJBQueryImpl) q).getDatabaseQuery();

        for (Filtro xd : this.filtros) {
            if (!xd.getTipo().equals("SUB")) {

                if (xd.getTipo().equals("BETWEENFECHA")) {
                    r.put(xd.parametroKey1(), xd.getValue1());
                    r.put(xd.parametroKey2(), xd.getValue2());
                } else {
                    r.put(xd.parametroKey1(), xd.getValue1());
                }
            } else {
                r.put(xd.parametroKey1(), xd.getValue1());
                r.put(xd.parametroKey2() + xd.parametroKey2(), xd.getValue2());
            }
        }
//        this.sqlQuery= databaseQuery.getTranslatedSQLString(session, r);
//        this.filas = Integer.parseInt(q1.getSingleResult().toString());
//        System.out.println("SQL QUERY : "+this.sqlQuery);
        q.setMaxResults(count);
        q.setFirstResult(first);
        return new Object[]{q.getResultList(), Integer.parseInt(q1.getSingleResult().toString()), databaseQuery.getTranslatedSQLString(session, r)};
    }

    /**
     * Método para crear una consulta
     *
     * @param em
     * @return
     */
    public Query crearConsulta(EntityManager em) {
        Query q = em.createQuery(sb.toString());
        for (Filtro f : filtros) {
            if (!f.getTipo().equals("ORDER")) {
                if (f.getKey1().trim().length() != 0) {
                    if (f.getTipo().equals("LIKE")) {
                        f.setValue1(f.getValue1().toString().toUpperCase() + "%");
                    }
                    if (f.getTipo().equals("SUB")) {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                    } else {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                    }
                }
                if (!f.getTipo().equals("SUB")) {
                    if (f.getKey2().trim().length() != 0) {
                        if (f.getTipo().equals("LIKE")) {
                            f.setValue2(f.getValue2().toString().toUpperCase() + "%");
                        }
                        q.setParameter(f.parametroKey2(), f.getValue2());
                    }
                }
            }
        }

        return q;
    }

    /**
     * @return the filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @param filas the filas to set
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }

    /**
     * @return the sqlQuery
     */
    public String getSqlQuery() {
        return sqlQuery;
    }
}
