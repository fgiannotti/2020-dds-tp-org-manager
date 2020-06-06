package Configuracion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
public class Configuracion {
    private int passwordScoreMinimo;
    private int passwordLengthMinimo;
    private int intentosMaximos;

    public static void main(String[] args) {
        try {
            levantarConfiguracion();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void levantarConfiguracion() {
        try {
            File archivo = new File("config.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document archivoDeConfiguracion = documentBuilder.parse(archivo);

            archivoDeConfiguracion.getDocumentElement().normalize();

            Node nodo = archivoDeConfiguracion.getElementById("Autenticador");
            System.out.println("Elemento: " + nodo.getNodeName());

            if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodo;

                System.out.println("passwordScoreMinimo: " + element.getElementsByTagName("passwordScoreMinimo").item(0).getTextContent());
                System.out.println("passwordLengthMinimo: " + element.getElementsByTagName("passwordLengthMinimo").item(0).getTextContent());
                System.out.println("intentosMaximos: " + element.getElementsByTagName("intentosMaximos").item(0).getTextContent());

                System.out.println("");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}