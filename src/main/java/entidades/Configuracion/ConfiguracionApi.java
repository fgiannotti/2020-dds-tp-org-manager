package entidades.Configuracion;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class ConfiguracionApi {

    public ConfiguracionApi() {
        this.levantarConfiguracion();
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getCondicionVinculador() {
        return condicionVinculador;
    }

    public void setCondicionVinculador(String condicionVinculador) {
        this.condicionVinculador = condicionVinculador;
    }

    public String getCriterioVinculacion() {
        return criterioVinculacion;
    }

    public void setCriterioVinculacion(String criterioVinculacion) {
        this.criterioVinculacion = criterioVinculacion;
    }

    public ArrayList<String> getCriteriosDeMix() {
        return criteriosDeMix;
    }

    public void setCriteriosDeMix(ArrayList<String> criteriosDeMix) {
        this.criteriosDeMix = criteriosDeMix;
    }

    private String fechaDesde;
    private String fechaHasta;
    private String condicionVinculador;
    private String criterioVinculacion;
    private ArrayList<String> criteriosDeMix = new ArrayList<>();

    public void levantarConfiguracion() {
        try {
            File archivo = new File("configApi.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document archivoDeConfiguracion = documentBuilder.parse(archivo);
            archivoDeConfiguracion.getDocumentElement().normalize();

            Node nodo = archivoDeConfiguracion.getElementsByTagName("General").item(0);

            if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodo;
                this.setFechaDesde(element.getElementsByTagName("fechaDesde").item(0).getTextContent());
                this.setFechaHasta(element.getElementsByTagName("fechaHasta").item(0).getTextContent());
                this.setCondicionVinculador(element.getElementsByTagName("condicionVinculador").item(0).getTextContent());
                this.setCriterioVinculacion(element.getElementsByTagName("criterioVinculacion").item(0).getTextContent());
                Node nodoDeCriterios = element.getElementsByTagName("criteriosDeMix").item(0);
                if(nodoDeCriterios.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoDeCriterio = (Element) nodoDeCriterios;
                    int cantidadDeCriterios = elementoDeCriterio.getElementsByTagName("criterio").getLength();
                    for (int i = 0; i < cantidadDeCriterios; i++) {
                        criteriosDeMix.add(elementoDeCriterio.getElementsByTagName("criterio").item(i).getTextContent());
                    }
                } else{
                    System.out.println("El elementoDeCriterio no es un ELEMENT_NODE");
                }
            } else{
                System.out.println("El element no es un ELEMENT_NODE");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonConfig() {
        JSONObject json= new JSONObject();
        json.put("fechaDesde",fechaDesde);
        json.put("fechaHasta",fechaHasta);
        json.put("condicionVinculador",condicionVinculador);
        json.put("criterioVinculacion",criterioVinculacion);
        json.put("criteriosDeMix",criteriosDeMix);
        return json;
    }
}