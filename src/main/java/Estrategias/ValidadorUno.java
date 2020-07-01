package Estrategias;

import BandejaDeEntrada.BandejaDeEntrada;
import BandejaDeEntrada.Resultado;
import Configuracion.Configuracion;
import Items.Item;
import Operaciones.OperacionEgreso;
import Operaciones.Presupuesto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidadorUno implements Validador {
    int presupuestosNecesarios;
    private BandejaDeEntrada bandejaDeEntrada;

    public ValidadorUno(BandejaDeEntrada bandejaDeEntrada) {
        this.bandejaDeEntrada = bandejaDeEntrada;
        Configuracion configuracion = new Configuracion();
        this.presupuestosNecesarios = configuracion.getPresupuestosMinimos();
    }

    @Override
    public Boolean validar(OperacionEgreso unEgreso){
        System.out.println("Validando...");
        boolean detalleCorrecto;
        boolean criterioCorrecto;
        boolean carga = this.cargaCorrecta(unEgreso);

        List<Presupuesto> presupuestosFiltrados = unEgreso.getPresupuestosPreliminares().stream().filter(presupuesto ->
                this.compararDetalles(unEgreso,presupuesto)).collect(Collectors.toList());

        if (presupuestosFiltrados.size() == 0){
            detalleCorrecto = false;
            criterioCorrecto = false;
        }else{
            detalleCorrecto = true;
            criterioCorrecto = presupuestosFiltrados.stream()
                    .anyMatch(presupuesto -> this.elegirPorCriterio(unEgreso, presupuesto));
        }

        this.enviarResultado(unEgreso,carga,detalleCorrecto,criterioCorrecto);
        System.out.println(carga && detalleCorrecto && criterioCorrecto);
        return carga && detalleCorrecto && criterioCorrecto;
    }

    @Override
    public Boolean cargaCorrecta(OperacionEgreso unEgreso) {
        if(null != unEgreso.getCantidadMinimaDePresupuestos()) {
            return unEgreso.getCantidadMinimaDePresupuestos() >= this.presupuestosNecesarios;
        }
        return false;
    }

    @Override
    public Boolean compararDetalles(OperacionEgreso unEgreso, Presupuesto presupuesto) {
        ArrayList<Item> articulosDeEgresos = (ArrayList<Item>) unEgreso.getItems();
        return articulosDeEgresos.stream().allMatch(item ->
                item.estoyEnEstosItemsDelPresupuesto(presupuesto.getItems())
        && articulosDeEgresos.size() == presupuesto.getItems().size());
    }

    @Override
    public Boolean elegirPorCriterio(OperacionEgreso unEgreso, Presupuesto presupuesto) {
        return unEgreso.getCriterio() == Criterio.MENOR_VALOR ?
                unEgreso.presupuestoMenorValor(presupuesto) : true;
    }

    private void enviarResultado(OperacionEgreso unEgreso,Boolean carga, Boolean detalle, Boolean criterio ) {
        Resultado resultado = new Resultado(
                unEgreso.getNumeroOperacion(),
                unEgreso.getProveedor(),
                carga,
                detalle,
                criterio,
                false,
                LocalDate.now());
        this.bandejaDeEntrada.guardarResultado(resultado);
    }

    @Override
    public Void guardarResultados() {
        return null;
    }

    public void setPresupuestosNecesarios(int presupuestosNecesarios) { this.presupuestosNecesarios = presupuestosNecesarios; }
}
