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

    public OperacionEgreso find (int id) {
        String query = "from OperacionEgreso";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionEgreso)a); });
        for (OperacionEgreso operacion : operaciones) {
            if (operacion.getId() == id){
                return operacion;
            }
        }
        throw new RuntimeException(("Entidad no encontrada. id: ").concat(String.valueOf(id)));
    }

    public void agregar(OperacionEgreso nuevoEgreso) {
        this.operaciones.add(nuevoEgreso);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(nuevoEgreso);
        EntityManagerHelper.commit();
    }
}
