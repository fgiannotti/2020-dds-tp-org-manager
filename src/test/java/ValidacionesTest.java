import BandejaDeEntrada.*;
import Estrategias.Filtro;
import Estrategias.FiltroPorEstado;
import Estrategias.FiltroPorFecha;
import Operaciones.Proveedor;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidacionesTest {
    public BandejaDeEntrada bandeja;
    public Resultado resultado1;
    public Resultado resultado2;
    public Resultado resultado3;
    public Proveedor proveedor;
    public FiltroPorFecha filtroPorFecha;
    public FiltroPorEstado filtroPorEstadoLeido;
    public List<Filtro> filtros = new ArrayList<Filtro>();
    @Before
    public void Setup () {
        filtroPorFecha = new FiltroPorFecha(LocalDate.now());
        filtroPorEstadoLeido = new FiltroPorEstado(true);

        bandeja = new BandejaDeEntrada(filtros);
        proveedor = new Proveedor("jorgito-provides","Docu","calle-falsa123");
        resultado1 = new Resultado(1,proveedor,true,true,true,false, LocalDate.now().minusDays(1));
        resultado2 = new Resultado(2,proveedor,true,true,true,false, LocalDate.now());
        resultado3 = new Resultado(3,proveedor,true,true,true,true, LocalDate.now());

        bandeja.guardarResultado(resultado2);
        bandeja.guardarResultado(resultado1);
        bandeja.guardarResultado(resultado3);
    }
    @Test
    public void BandejaMuestra1ResultadoFiltradoPorFechaYFueLeido() {
        this.Setup();
        filtros.add(filtroPorFecha);
        bandeja.mostrarMensajes();
        Assertions.assertEquals(true, resultado1.getFueLeido());
        Assertions.assertEquals(false, resultado2.getFueLeido());
        Assertions.assertEquals(true, resultado3.getFueLeido());
    }
    @Test
    public void BandejaMuestra1ResultadoFiltradoPorEstadoLeidoYLosDemasNoFueronLeidos() {
        this.Setup();
        filtros.add(filtroPorEstadoLeido);
        bandeja.setFiltros(filtros);
        bandeja.mostrarMensajes();
        Assertions.assertEquals(false, resultado1.getFueLeido());
        Assertions.assertEquals(false, resultado2.getFueLeido());
        Assertions.assertEquals(true, resultado3.getFueLeido());
    }
}
