package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.Operacion;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RepoOperaciones {
    private List<Operacion> operaciones = new ArrayList<Operacion>();
    EntityManager em = EntityManagerHelper.getEntityManager();

    public RepoOperaciones () {
        operaciones = new ArrayList<Operacion>();
    }

    public List<Operacion> mostrarTodos() {
        em.createQuery("from OperacionIngreso").getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        em.createQuery("from OperacionEgreso").getResultList().forEach((a) -> { operaciones.add((OperacionEgreso)a); });
        return operaciones;
    }
}
