package entidades.Operaciones;

import db.EntityManagerHelper;
import entidades.Items.Articulo;
import entidades.MedioDePago.MedioDePago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EgresoBuilder {
    private OperacionEgreso unEgreso;

    public void nuevoEgreso () {
        unEgreso = new OperacionEgreso();
    }

    public void asignarFechaPresupuestosMinYValor (LocalDate fecha, int presupuestos, int valor) {
        unEgreso.setFechaOperacion(fecha);
        unEgreso.setCantidadMinimaDePresupuestos(presupuestos);
        unEgreso.setMontoTotal(valor);
    }

    public void asignarProveedor (Proveedor proveedor) {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        proveedores.add(proveedor);
        unEgreso.setProveedores(proveedores);
    }

    public void asignarMedioDePago (MedioDePago medio) {
        unEgreso.setMedioDePago(medio);
    }

    public void asignarArticulo (Articulo unArticulo){
        unEgreso.setArticulo(unArticulo);
    }

    public void asignarComprobante (Comprobante unComprobante){
        unEgreso.setComprobante(unComprobante);
    }

    public void confirmarEgreso() {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.persist(unEgreso);
        EntityManagerHelper.commit();
    }

    /*public void asignarCriterio(CriterioDeEmpresa unCriterio) {
        unEgreso.
    }*/
}
