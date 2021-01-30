package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Organizaciones.Organizacion;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RepoOperacionesIngresos {
    private List<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();
    EntityManager em = EntityManagerHelper.getEntityManager();

    public RepoOperacionesIngresos () {
        operaciones = new ArrayList<OperacionIngreso>();
    }

    public ArrayList<OperacionIngreso> getAll() {
        String query = "from OperacionIngreso";
        ArrayList<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();
        em.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        return operaciones;
    }

    public ArrayList<OperacionIngreso> getAllByOrg(Organizacion org) {
        String query = "from OperacionIngreso WHERE organizacion_id = '" + org.getId() +  "'";
        ArrayList<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();
        em.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        return operaciones;
    }

    public OperacionIngreso get(int id) {
        String query = "from OperacionIngreso where id = " + id;
        return (OperacionIngreso) em.createQuery(query).getSingleResult();
    }

    public OperacionIngreso find(int id) {
        String query = "from OperacionIngreso";
        ArrayList<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();

        em.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        for (OperacionIngreso operacion : operaciones) {
            System.out.println("Busco ID:"+String.valueOf(id)+ "contra: "+String.valueOf(operacion.getId()));
            if (operacion.getId() == id){
                return operacion;
            }
        }
        throw new RuntimeException(("Entidad no encontrada. id: ").concat(String.valueOf(id)));
    }

    public void guardar(OperacionIngreso nuevoIngreso) {

        em.getTransaction().begin();
        em.persist(nuevoIngreso);
        em.getTransaction().commit();
    }
}
