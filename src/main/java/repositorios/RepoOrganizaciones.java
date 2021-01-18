package repositorios;

import db.EntityManagerHelper;
import entidades.Organizaciones.Organizacion;

public class RepoOrganizaciones {
    public int findOrgID(String nombreFicticio){
        EntityManagerHelper.beginTransaction();
        Organizacion org = (Organizacion) EntityManagerHelper.createQuery("FROM Organizacion where nombre_ficticio = '"+nombreFicticio+"'").getSingleResult();
        return org.getId();
    }
}
