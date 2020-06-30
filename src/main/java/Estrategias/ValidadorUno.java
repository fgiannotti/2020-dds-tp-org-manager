package Estrategias;

import BandejaDeEntrada.Resultado;
import Configuracion.Configuracion;
import Items.Item;
import Operaciones.OperacionEgreso;
import Operaciones.Presupuesto;

import java.time.LocalDate;
import java.util.ArrayList;

public class ValidadorUno implements Validador {
    int presupuestosNecesarios;

    public ValidadorUno() {
        Configuracion configuracion = new Configuracion();
        this.presupuestosNecesarios = configuracion.getPresupuestosMinimos();
    }

    public Boolean validar(OperacionEgreso unEgreso){
        boolean carga = this.cargaCorrecta(unEgreso);
        boolean detalle = unEgreso.getPresupuestosPreliminares().stream()
                .anyMatch(presupuesto -> this.compararDetalles(unEgreso,presupuesto));
        boolean criterio = unEgreso.getPresupuestosPreliminares().stream()
                .anyMatch(presupuesto -> this.elegirPorCriterio(unEgreso, presupuesto));
        this.crearResultado(unEgreso,carga,detalle,criterio);
        return carga && detalle && criterio;
    }

    @Override
    public Boolean cargaCorrecta(OperacionEgreso unEgreso) {
        if(null != unEgreso.getCantidadMinimaDePresupuestos()) {
            return unEgreso.getCantidadMinimaDePresupuestos() >= this.presupuestosNecesarios;
        }
        return false;
    }

    private void crearResultado(OperacionEgreso unEgreso,Boolean carga, Boolean detalle, Boolean criterio ) {
        Resultado resultado = new Resultado(
                unEgreso.getComprobante().getNumeroComprobante(),
                unEgreso.getProveedor(),
                carga,
                detalle,
                criterio,
                false,
                LocalDate.now());
    }

    @Override
    public Boolean compararDetalles(OperacionEgreso unEgreso, Presupuesto presupuesto) {
        ArrayList<Item> articulosDeEgresos = (ArrayList<Item>) unEgreso.getItems();
        return articulosDeEgresos.stream().allMatch(item -> item.estoyEnEstosItemsDelPresupuesto(presupuesto.getItems())
        && articulosDeEgresos.size() == presupuesto.getItems().size());
    }

    @Override
    public Boolean elegirPorCriterio(OperacionEgreso unEgreso, Presupuesto presupuesto) {
        return unEgreso.getCriterio() == Criterio.MENOR_VALOR ?
                unEgreso.presupuestoMenorValor(presupuesto) : true;
    }

    @Override
    public Void guardarResultados() {
        return null;
    }
}
