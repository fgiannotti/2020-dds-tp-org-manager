package entidades.Estrategias;

import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Configuracion.*;
import entidades.Items.Item;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidadorUno implements Validador {
    int presupuestosNecesarios;
    private BandejaDeEntrada bandejaDeEntrada;
    private EntityManager em = EntityManagerHelper.getEntityManager();

    public ValidadorUno(BandejaDeEntrada bandejaDeEntrada) {
        this.bandejaDeEntrada = bandejaDeEntrada;
        Configuracion configuracion = new Configuracion();
        this.presupuestosNecesarios = configuracion.getPresupuestosMinimos();
        ;
    }

    @Override
    public List<Object> validar(OperacionEgreso unEgreso) {
        System.out.println("Validando...");
        boolean detalleCorrecto;
        boolean criterioCorrecto;
        boolean carga = this.cargaCorrecta(unEgreso);
        String razonValidacion = "Validacion OK.";
        List<Presupuesto> presupuestosFiltrados = unEgreso.getPresupuestosPreliminares().stream().filter(presupuesto ->
                this.compararDetalles(unEgreso, presupuesto)).collect(Collectors.toList());

        if (presupuestosFiltrados.size() == 0) {
            detalleCorrecto = false;
            criterioCorrecto = false;
            //ITEMS DIERON DISTINTO
            razonValidacion = "Inconsistencias al comparar detalles entre los presupuestos y el egreso.";
        } else {
            detalleCorrecto = true;
            criterioCorrecto = presupuestosFiltrados.stream()
                    .anyMatch(presupuesto -> this.elegirPorCriterio(unEgreso, presupuesto));
            if (!criterioCorrecto) {
                razonValidacion = "El presupuesto no fue elegido correctamente en base a nuestros criterios.";
            }
        }

        this.guardarResultados(unEgreso, carga, detalleCorrecto, criterioCorrecto, razonValidacion);

        System.out.println(razonValidacion);
        Boolean validarOK = carga && detalleCorrecto && criterioCorrecto;
        return new ArrayList<>(Arrays.asList(validarOK, razonValidacion));
    }

    @Override
    public Boolean cargaCorrecta(OperacionEgreso unEgreso) {
        if (null != unEgreso.getCantidadMinimaDePresupuestos()) {
            return unEgreso.getCantidadMinimaDePresupuestos() >= this.presupuestosNecesarios;
        }
        return false;
    }

    @Override
    public Boolean compararDetalles(OperacionEgreso unEgreso, Presupuesto presupuesto) {
        List<Item> itemsEgreso = unEgreso.getItems();

        //todos los items del egreso, tienen que ser igual a alguno dle presupuesto
        return itemsEgreso.stream().allMatch(itemEg ->
                presupuesto.getItems().stream().anyMatch(
                        itemPresu -> itemPresu.equals(itemEg)))
                && itemsEgreso.size() == presupuesto.getItems().size();
    }

    @Override
    public Boolean elegirPorCriterio(OperacionEgreso unEgreso, Presupuesto presupuesto) {
        return unEgreso.getCriterio() == Criterio.MENOR_VALOR ?
                unEgreso.presupuestoMenorValor(presupuesto) : unEgreso.presupuestoMayorValor(presupuesto);
    }

    @Override
    public void guardarResultados(OperacionEgreso unEgreso, Boolean carga, Boolean detalle, Boolean criterio, String descripcion) {
        Resultado resultado = new Resultado(
                unEgreso.getNumeroOperacion(),
                unEgreso.getProveedorElegido(),
                carga,
                detalle,
                criterio,
                false,
                LocalDateTime.now(),
                descripcion);

        this.bandejaDeEntrada.guardarResultado(resultado);

        em.getTransaction().begin();
        em.merge(this.bandejaDeEntrada);
        em.getTransaction().commit();
    }

    public void setPresupuestosNecesarios(int presupuestosNecesarios) {
        this.presupuestosNecesarios = presupuestosNecesarios;
    }
}
