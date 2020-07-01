package Configuracion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
public class Configuracion {

    public Configuracion() {
        this.levantarConfiguracion();
    }

    public int getPasswordScoreMinimo() {
        return passwordScoreMinimo;
    }

    public void setPasswordScoreMinimo(String passwordScoreMinimo) {
        Integer result = Integer.parseInt(passwordScoreMinimo);
        this.passwordScoreMinimo = result;
    }

    public int getPasswordLengthMinimo() {
        return passwordLengthMinimo;
    }

    public void setPasswordLengthMinimo(String passwordLengthMinimo) {
        Integer result = Integer.parseInt(passwordLengthMinimo);
        this.passwordLengthMinimo = result;
    }

    public int getIntentosMaximos() {
        return intentosMaximos;
    }

    public void setIntentosMaximos(String intentosMaximos) {
        Integer result = Integer.parseInt(intentosMaximos);
        this.intentosMaximos = result;
    }

    public int getPresupuestosMinimos() {
        return presupuestosMinimos;
    }

    public void setPresupuestosMinimos(String presupuestosMinimos) {
        Integer result = Integer.parseInt(presupuestosMinimos);
        this.presupuestosMinimos = result;
    }

    private int passwordScoreMinimo;
    private int passwordLengthMinimo;
    private int intentosMaximos;
    private int presupuestosMinimos;

    public void levantarConfiguracion() {
        try {
            File archivo = new File("config.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document archivoDeConfiguracion = documentBuilder.parse(archivo);
            archivoDeConfiguracion.getDocumentElement().normalize();

            Node nodo = archivoDeConfiguracion.getElementsByTagName("*").item(0);

            if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodo;
                this.setPasswordScoreMinimo(element.getElementsByTagName("passwordScoreMinimo").item(0).getTextContent());
                this.setPasswordLengthMinimo(element.getElementsByTagName("passwordLengthMinimo").item(0).getTextContent());
                this.setIntentosMaximos(element.getElementsByTagName("intentosMaximos").item(0).getTextContent());
                this.setPresupuestosMinimos(element.getElementsByTagName("presupuestosMinimos").item(0).getTextContent());

            } else{
                System.out.println("No es un ELEMENT_NODE");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}