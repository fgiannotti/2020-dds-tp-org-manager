package entidades.Estrategias;

import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Configuracion.*;
import entidades.Items.Item;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;
import javafx.util.Pair;

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
        this.presupuestosNecesarios = configuracion.getPresupuestosMinimos();;
    }

    @Override
    public Pair<Boolean, String> validar(OperacionEgreso unEgreso){
        System.out.println("Validando...");
        boolean detalleCorrecto;
        boolean criterioCorrecto;
        boolean carga = this.cargaCorrecta(unEgreso);
        String razonValidacion = "Validacion OK.";
        List<Presupuesto> presupuestosFiltrados = unEgreso.getPresupuestosPreliminares().stream().filter(presupuesto ->
                this.compararDetalles(unEgreso,presupuesto)).collect(Collectors.toList());

        if (presupuestosFiltrados.size() == 0){
            detalleCorrecto = false;
            criterioCorrecto = false;
            //ITEMS DIERON DISTINTO
            razonValidacion = "Inconsistencias al comparar detalles entre los presupuestos y el egreso.";
        }else{
            detalleCorrecto = true;
            criterioCorrecto = presupuestosFiltrados.stream()
                    .anyMatch(presupuesto -> this.elegirPorCriterio(unEgreso, presupuesto));
            if (!criterioCorrecto){
                razonValidacion = "El presupuesto no fue elegido correctamente en base a nuestros criterios.";
            }
        }

        this.guardarResultados(unEgreso,carga,detalleCorrecto,criterioCorrecto);

        System.out.println(carga && detalleCorrecto && criterioCorrecto);
        Boolean validarOK = carga && detalleCorrecto && criterioCorrecto;
        return new Pair<>(validarOK,razonValidacion);
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
    @Override
    public void guardarResultados(OperacionEgreso unEgreso, Boolean carga, Boolean detalle, Boolean criterio ) {
        Resultado resultado = new Resultado(
                unEgreso.getNumeroOperacion(),
                unEgreso.getProveedorElegido(),
                carga,
                detalle,
                criterio,
                false,
                LocalDate.now(),
                this.bandejaDeEntrada);
        this.bandejaDeEntrada.guardarResultado(resultado);
    }

    public void setPresupuestosNecesarios(int presupuestosNecesarios) { this.presupuestosNecesarios = presupuestosNecesarios; }
}
