package Vinculador;


import org.json.JSONObject;

public abstract class Vinculador {
    CondicionVinculador condicion;
    CriterioVinculador criterio;
    JSONObject ejecutarVinculador(JSONObject operacionesPorVincular)
    {
        JSONObject jEgresos = (JSONObject) operacionesPorVincular.get("Egresos");
        JSONObject jIngresos = (JSONObject) operacionesPorVincular.get("Ingresos");

        JSONObject response = new JSONObject();

        return response;
    }
    void cambiarCriterio(CriterioVinculador unCriterio){
        this.criterio=unCriterio;
    }

}
