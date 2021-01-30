package repositorios;

import db.EntityManagerHelper;
import entidades.Organizaciones.Organizacion;

import javax.persistence.EntityManager;

public class RepoOrganizaciones {
    EntityManager em = EntityManagerHelper.getEntityManager();
    public int findOrgID(String nombreFicticio){

        Organizacion org = (Organizacion) em.createQuery("FROM Organizacion where nombre_ficticio = '"+nombreFicticio+"'").getSingleResult();
        return org.getId();
    }
}
