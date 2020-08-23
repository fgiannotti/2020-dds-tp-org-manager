package Services;

import DatosFinancieros.Divisa;
import DatosGeograficos.ListadoDeCiudades;
import DatosGeograficos.ListadoDeProvincias;
import DatosGeograficos.Pais;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface MELIService {

    @GET("classified_locations/countries")
    Call<List<Pais>> countries();

    @GET("classified_locations/countries/{Country_id}")
    Call<ListadoDeProvincias> states(@Path("Country_id") String countryId);

    @GET("classified_locations/states/{State_id}")
    Call<ListadoDeCiudades> cities(@Path("State_id") String stateId);

    @GET("currencies")
    Call<List<Divisa>> currencies();

}
