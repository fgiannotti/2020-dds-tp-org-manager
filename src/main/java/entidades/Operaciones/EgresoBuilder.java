package entidades.Operaciones;

import db.EntityManagerHelper;
import entidades.Items.Articulo;
import entidades.MedioDePago.MedioDePago;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.Organizacion;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import repositorios.RepoOperacionesEgresos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EgresoBuilder {
    public OperacionEgreso unEgreso;
    public RepoOperacionesEgresos repoEgreso = new RepoOperacionesEgresos();

    public void nuevoEgreso() {
        unEgreso = new OperacionEgreso();
    }

    public void asignarPresupuestosPreliminares(List<Presupuesto> presupuestosPrelim) {
        unEgreso.setPresupuestosPreliminares(presupuestosPrelim);
    }

    public void asignarDescripcion(String desc) {
        unEgreso.setDescripcion(desc);
    }

    public void asignarFechaPresupuestosMinYValor(LocalDate fecha, int presupuestos, float valor) {
        unEgreso.setFechaOperacion(fecha);
        unEgreso.setCantidadMinimaDePresupuestos(presupuestos);
        unEgreso.setMontoTotal(valor);
    }

    public void asignarOrganizacion(Organizacion org) {
        unEgreso.setOrganizacion(org);
    }

    public void asignarProveedor(Proveedor proveedor) {
        unEgreso.setProveedorElegido(proveedor);
    }

    public void asignarMedioDePago(MedioDePago medio) {
        unEgreso.setMedioDePago(medio);
    }

    public void asignarCategorias(List<Categoria> cats) {
        unEgreso.setCategorias(cats);
    }

    public void generarNroOperacion() {
        unEgreso.setNumeroOperacion(Math.abs(UUID.randomUUID().hashCode() / 1000));
    }

    public void asignarArticulo(Articulo unArticulo) {
        unEgreso.setArticulo(unArticulo);
    }

    public void asignarComprobante(Comprobante unComprobante) {
        unEgreso.setComprobante(unComprobante);
    }

    public void confirmarEgreso() {
        repoEgreso.agregar(unEgreso);
        try {
            System.err.println("EGRESO PERSISTIDO: \n" + unEgreso.toString());
        }catch (Exception e){
            System.out.println("fallo mostrar egreso");
        }
    }

    /*public void asignarCriterio(CriterioDeEmpresa unCriterio) {
        unEgreso.
    }*/
}
