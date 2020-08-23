import DatosFinancieros.ListadoDeDivisas;
import DatosGeograficos.*;
import Services.ServicioMELI;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class APITest {
    Scanner inputScanner;
    ServicioMELI servicioMELI;
    ListadoDePaises listadoDePaisesDisponibles;
    ListadoDeProvincias provinciasDelPais;
    ListadoDeCiudades ciudadesDeLaProvincia;
    ListadoDeDivisas divisasDisponibles;
    String idPaisElegido;
    String idProvinciaElegida;

    @Before
    public void setup() throws  IOException{
     servicioMELI = ServicioMELI.instancia();
     listadoDePaisesDisponibles = new ListadoDePaises(servicioMELI.listadoDePaises());
     divisasDisponibles = new ListadoDeDivisas(servicioMELI.listadoDeDivisas());
    }

    @Test
    public void testFuncionamientoIntegracion() throws IOException {
        this.setup();
        inputScanner = new Scanner(System.in);
        System.out.println("Listado de todos los paises disponibles:");
        System.out.println(listadoDePaisesDisponibles.toString());

        Optional<Pais> posiblePais = listadoDePaisesDisponibles.getPaisById("UY");
        if(posiblePais.isPresent()){
            Pais paisSeleccionado = posiblePais.get();
            provinciasDelPais = servicioMELI.listadoDeProvinciasDePais(paisSeleccionado);
            System.out.println("\n\nLas provincias del pais "+ paisSeleccionado.name + " son:");
            for(Provincia provinciatarget: provinciasDelPais.states){
                System.out.println(provinciatarget.toString());
            }
        }
        else{
            System.out.println("\nNo existe el pais seleccionado");
        }

        Optional<Provincia> posibleProvincia = provinciasDelPais.getProvinciaByName("Rocha");
        if(posibleProvincia.isPresent()){
            Provincia provinciaSeleccionada = posibleProvincia.get();
            ciudadesDeLaProvincia = servicioMELI.listadoDeCiudadesDeProvincia(provinciaSeleccionada);
            System.out.println("\n\nLas ciudades de la provincia " + provinciaSeleccionada.name + " son:" );
            for(Ciudad ciudadTarget: ciudadesDeLaProvincia.cities){
                System.out.println(ciudadTarget.toString());
            }
        }
        else{
            System.out.println("\nNo existe la provincia seleccionada");
        }
        System.out.println("\n\n\nListado de divisas disponibles:");
        System.out.println(divisasDisponibles.toString());
        }
    }


