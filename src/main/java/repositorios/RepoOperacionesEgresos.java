package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;

import java.util.ArrayList;
import java.util.List;

public class RepoOperacionesEgresos {
    private List<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();

    public RepoOperacionesEgresos () {
        operaciones = new ArrayList<OperacionEgreso>();
    }

    public ArrayList<OperacionEgreso> getAll () {
        String query = "from OperacionEgreso";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionEgreso)a); });
        return operaciones;
    }

    public void agregar(OperacionEgreso nuevoEgreso) {
        this.operaciones.add(nuevoEgreso);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(nuevoEgreso);
        EntityManagerHelper.commit();
    }
}
