package repositorios.Builders;

import entidades.Estrategias.Criterio;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.MedioDePago;
import entidades.Operaciones.*;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.Organizacion;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

public class OperacionEgresoBuilder {


    private final OperacionEgreso operacionEgreso;

    public OperacionEgreso getOperacionEgreso() {return operacionEgreso;}

    //Constructor
    public OperacionEgresoBuilder(){ this.operacionEgreso = new OperacionEgreso();}

    public OperacionEgresoBuilder agregarNumeroOperacion(int numeroOperacion){
        this.operacionEgreso.setNumeroOperacion(numeroOperacion);
        return this;
    }
    public OperacionEgresoBuilder agregarProveedor(Proveedor proveedor){
        List<Proveedor> proveedores = this.operacionEgreso.getProveedores();
        proveedores.add(proveedor);
        this.operacionEgreso.setProveedores(proveedores);
        return this;
    }
    public OperacionEgresoBuilder agregarProveedores(List<Proveedor> nuevosProveedores){
        List<Proveedor> proveedores = this.operacionEgreso.getProveedores();
        proveedores.addAll(nuevosProveedores);
        this.operacionEgreso.setProveedores(proveedores);
        return this;
    }

    public OperacionEgresoBuilder agregarFechaOperacion(LocalDate fecha){
        this.operacionEgreso.setFechaOperacion(fecha);
        return this;
    }
    public OperacionEgresoBuilder agregarMedioDePago(MedioDePago medioDePago){
        this.operacionEgreso.setMedioDePago(medioDePago);
        return this;
    }
    public OperacionEgresoBuilder agregarTipoDocumento(String tipoDocumento){
        this.operacionEgreso.setTipoDocumento(tipoDocumento);
        return this;
    }
    public OperacionEgresoBuilder agregarComprobante(Comprobante comprobante){
        this.operacionEgreso.setComprobante(comprobante);
        return this;
    }
    public OperacionEgresoBuilder agregarMontoTotal(int montoTotal){
        this.operacionEgreso.setMontoTotal(montoTotal);
        return this;
    }
    public OperacionEgresoBuilder agregarDescripcion(String desc){
        this.operacionEgreso.setDescripcion(desc);
        return this;
    }
    public OperacionEgresoBuilder agregarItems(List<Item> nuevosItems){
        List<Item> items = operacionEgreso.getItems();
        items.addAll(nuevosItems);

        this.operacionEgreso.setItems(items);
        return this;
    }

    public OperacionEgresoBuilder agregarPresupuestosPreliminares(List<Presupuesto> nuevosPresupuestos){
        List<Presupuesto> presupuestos = this.operacionEgreso.getPresupuestosPreliminares();
        presupuestos.addAll(nuevosPresupuestos);

        this.operacionEgreso.setPresupuestosPreliminares(presupuestos);
        return this;
    }

    //hace falta esto en la operacion?
    public OperacionEgresoBuilder agregarArticulo(Articulo articulo){
        this.operacionEgreso.setArticulo(articulo);
        return this;
    }

    public OperacionEgresoBuilder agregarCantidadMinimaDePresupuestos(Integer cantidadMinimaDePresupuestos){
        this.operacionEgreso.setCantidadMinimaDePresupuestos(cantidadMinimaDePresupuestos);
        return this;
    }

    public OperacionEgresoBuilder agregarIngreso(OperacionIngreso ingreso){
        this.operacionEgreso.setIngreso(ingreso);
        return this;
    }

    public OperacionEgresoBuilder agregarCriterio(Criterio criterio){
        this.operacionEgreso.setCriterio(criterio);
        return this;
    }

    public OperacionEgresoBuilder agregarCategoria(List<Categoria> nuevasCategorias){
        List<Categoria> categorias = this.operacionEgreso.getCategorias();
        categorias.addAll(nuevasCategorias);

        this.operacionEgreso.setCategorias(categorias);
        return this;
    }

    public OperacionEgresoBuilder agregarOrganizacion(Organizacion organizacion){
        this.operacionEgreso.setOrganizacion(organizacion);
        return this;
    }

    public OperacionEgreso build() throws Exception {
        if (this.operacionEgreso.getNumeroOperacion() == 0) {
            throw new Exception("No se asigno el nombre");
        }

        if ( isNull(this.operacionEgreso.getProveedores()) || this.operacionEgreso.getProveedores().size()==0) {
            throw new Exception("No se asigno razon social");
        }

        if (isNull(this.operacionEgreso.getFechaOperacion())) {
            throw new Exception("No se un asigno numero de CUIT");
        }

        if (isNull(this.operacionEgreso.getMedioDePago())) {
            throw new Exception("No se un asigno direccion postal");
        }

        if (this.operacionEgreso.getTipoDocumento().equals("") || isNull(this.operacionEgreso.getTipoDocumento()) ) {
            throw new Exception("No se asigno cantidad de personal");
        }

        if (isNull(this.operacionEgreso.getComprobante())) {
            throw new Exception("No se asigno comprobante");
        }

        if (this.operacionEgreso.getMontoTotal() == 0) {
            throw new Exception("No se asigno monto total");
        }

        if ( isNull(this.operacionEgreso.getItems())|| this.operacionEgreso.getItems().size()==0) {
            throw new Exception("No se asignaron items");
        }

        if (isNull(this.operacionEgreso.getPresupuestosPreliminares()) || this.operacionEgreso.getPresupuestosPreliminares().size()==0 ) {
            throw new Exception("No se asignaron presupuestos");
        }
        if (isNull(this.operacionEgreso.getArticulo())) {
            throw new Exception("No se asignaron articulos");
        }
        if (isNull(this.operacionEgreso.getCantidadMinimaDePresupuestos())) {
            throw new Exception("No se asigno cant minima de presupuestos");
        }
        if (isNull(this.operacionEgreso.getIngreso())) {
            throw new Exception("No se asigno ingreso");
        }
        if (isNull(this.operacionEgreso.getCriterio())) {
            throw new Exception("No se asigno criterio");
        }
        if ( isNull(this.operacionEgreso.getCategorias())|| this.operacionEgreso.getCategorias().size()==0) {
            throw new Exception("No se asignaron categorias");
        }
        if (isNull(this.operacionEgreso.getOrganizacion())) {
            throw new Exception("No se asigno organizacion");
        }

        return this.operacionEgreso;

    }
}
