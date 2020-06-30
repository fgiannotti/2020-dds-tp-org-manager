package Estrategias;

import Configuracion.Configuracion;
import Items.Item;
import Operaciones.OperacionEgreso;
import Operaciones.Presupuesto;

import java.util.ArrayList;

public class ValidadorUno implements Validador {
    int presupuestosNecesarios;

    public ValidadorUno() {
        Configuracion configuracion = new Configuracion();
        this.presupuestosNecesarios = configuracion.getPresupuestosMinimos();
    }

    @Override
    public Boolean cargaCorrecta(OperacionEgreso unEgreso) {
        if(null != unEgreso.getCantidadMinimaDePresupuestos()) {
            return unEgreso.getCantidadMinimaDePresupuestos() >= this.presupuestosNecesarios
                    &&
                    unEgreso.getPresupuestosPreliminares().stream()
                    .anyMatch(presupuesto -> this.compararDetalles(unEgreso,presupuesto) &&
                            this.elegirPorCriterio(unEgreso, presupuesto)
                            );
        }
        return true;
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
