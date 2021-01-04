import entidades.Estrategias.Criterio;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.Debito;
import entidades.Operaciones.Comprobante;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Operaciones.Proveedor;
import entidades.Organizaciones.Juridica;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VinculadorTest {

    Proveedor proveedorTest0 = new Proveedor();
    Debito tarjetaDeDebito = new Debito("Tarjeta Debito ICBC", 12204210);
    OperacionIngreso ingreso1;
    OperacionIngreso ingreso2;
    OperacionEgreso egreso1;
    OperacionEgreso egreso2;
    OperacionEgreso egreso3;
    Juridica organizacion;
    Comprobante comprobanteTest2;
    List<Proveedor> proveedoresTest0;
    List<Item> itemsTest1;
    Item itemTest1;
    List<Articulo> articulos;
    Articulo articuloTest0;
    Articulo articuloTest1;
    Articulo articuloTest2;

    @BeforeAll
    public void Setup () {
        proveedorTest0.setDireccionPostal("6321456");
        proveedorTest0.setNombre_apellido_razon("Jorge Guaymallen");
        proveedorTest0.setDireccionPostal("1714");
        articuloTest0 = new Articulo("Auriculares", 20, "Maximo sonido", proveedorTest0);
        articuloTest1 = new Articulo("Pendrive", 10, "Maxima capacidad", proveedorTest0);
        articuloTest2 = new Articulo("Salsa", 280, "Maximo sabor", proveedorTest0);
        articulos = Arrays.asList(articuloTest0,articuloTest1,articuloTest2);
        itemTest1 = new Item("Auriculares, pendrive y salsa" ,"Promo1", articulos);
        itemsTest1 = Arrays.asList(itemTest1);
        comprobanteTest2 = new Comprobante(itemsTest1);
        organizacion = new Juridica("organizacionJuridica.SRL","Sarasa", 2023123123, null, 1, null);
        ingreso1 = new OperacionIngreso(52, "Ingreso 1", LocalDate.of(2020,8,23), organizacion);
        ingreso1.setId(1);
        ingreso2 = new OperacionIngreso(345, "Ingreso 2", LocalDate.of(2020,8,26), organizacion);
        ingreso2.setId(2);
        proveedoresTest0 = new ArrayList<Proveedor>();
        proveedoresTest0.add(proveedorTest0);
        egreso1 = new OperacionEgreso(1, "Chicle vencido", proveedoresTest0, tarjetaDeDebito, LocalDate.of(2020,8,5),
                "Facturita", comprobanteTest2, itemsTest1, 1, Criterio.MENOR_VALOR);
        egreso1.setId(1);
        egreso2 = new OperacionEgreso(2, "Chicle no vencido", proveedoresTest0, tarjetaDeDebito, LocalDate.of(2020,9,2),
                "Facturita", comprobanteTest2, itemsTest1, 1, Criterio.MENOR_VALOR);
        egreso2.setId(2);
        egreso3 = new OperacionEgreso(50, "Chicle super no vencido", proveedoresTest0, tarjetaDeDebito, LocalDate.of(2020,8,2),
                "Facturita", comprobanteTest2, itemsTest1, 1, Criterio.MENOR_VALOR);
        egreso3.setId(3);
        egreso1.setOrganizacion(organizacion);
        egreso2.setOrganizacion(organizacion);
        egreso3.setOrganizacion(organizacion);
        organizacion.agregarOperacion(egreso1);
        organizacion.agregarOperacion(egreso2);
        organizacion.agregarOperacion(egreso3);
        organizacion.agregarOperacion(ingreso1);
        organizacion.agregarOperacion(ingreso2);
    }

    @Test
    public void RealizaVinculacion() {
        this.Setup();
        organizacion.realizarVinculacion();
        assertTrue(ingreso1.getOperacionEgresos().contains(egreso1));
        assertTrue(ingreso2.getOperacionEgresos().contains(egreso3));
        assertEquals(2, ingreso1.getOperacionEgresos().size());
        assertEquals(1, ingreso2.getOperacionEgresos().size());
    }

}
*/