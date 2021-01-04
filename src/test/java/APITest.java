import entidades.DatosFinancieros.ListadoDeDivisas;
import entidades.DatosGeograficos.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utils.Services.ServicioMELI;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class APITest {
    ServicioMELI servicioMELI = ServicioMELI.instancia();
    ListadoDePaises listadoDePaisesDisponibles = new ListadoDePaises(servicioMELI.listadoDePaises());
    ListadoDeDivisas divisasDisponibles = new ListadoDeDivisas(servicioMELI.listadoDeDivisas());
    Scanner inputScanner;
    ListadoDeProvincias provinciasDelPais;
    ListadoDeCiudades ciudadesDeLaProvincia;
    String idPaisElegido;
    String idProvinciaElegida;

    public APITest() throws IOException {
    }

    @Test
    public void testFuncionamientoIntegracion() throws IOException {
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


