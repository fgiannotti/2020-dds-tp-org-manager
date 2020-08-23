package Services;

import DatosFinancieros.Divisa;
import DatosGeograficos.ListadoDeCiudades;
import DatosGeograficos.ListadoDeProvincias;
import DatosGeograficos.Pais;
import DatosGeograficos.Provincia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServicioMELI {
    private static ServicioMELI instancia = null;
    private static int maximaCantidadRegistrosDefault = 100;
    private Retrofit retrofit;

    private ServicioMELI(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioMELI instancia(){
        if(instancia== null){
            instancia = new ServicioMELI();
        }
        return instancia;
    }

    public List<Pais> listadoDePaises() throws IOException {
        MELIService meliService = this.retrofit.create(MELIService.class);
        Call<List<Pais>> requestListadoDePaises = meliService.countries();
        Response<List<Pais>> responseListadoDePaises = requestListadoDePaises.execute();
        List<Pais> paises =  responseListadoDePaises.body();
        return paises;
    }


    public ListadoDeProvincias listadoDeProvinciasDePais(Pais pais) throws IOException {
        MELIService meliService = this.retrofit.create(MELIService.class);
        Call<ListadoDeProvincias> requestListadoDeProvincias = meliService.states(pais.id);
        Response<ListadoDeProvincias> responseListadoDeProvincias = requestListadoDeProvincias.execute();
        ListadoDeProvincias provincias = responseListadoDeProvincias.body();
        return provincias;
    }

    public ListadoDeCiudades listadoDeCiudadesDeProvincia(Provincia provincia) throws IOException {
        MELIService meliService = this.retrofit.create(MELIService.class);
        Call<ListadoDeCiudades> requestListadoDeCiudades = meliService.cities(provincia.id);
        Response<ListadoDeCiudades> responseListadoDeCiudades= requestListadoDeCiudades.execute();
        ListadoDeCiudades ciudades = responseListadoDeCiudades.body();
        return ciudades;
    }

    public List<Divisa> listadoDeDivisas() throws IOException {
        MELIService meliService = this.retrofit.create(MELIService.class);
        Call<List<Divisa>> requestListadoDeDivisas = meliService.currencies();
        Response<List<Divisa>> responseListadoDeDivisas = requestListadoDeDivisas.execute();
        List<Divisa> divisas = responseListadoDeDivisas.body();
        return divisas;
    }

}
