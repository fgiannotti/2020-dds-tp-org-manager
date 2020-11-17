package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionIngreso;

import java.util.ArrayList;
import java.util.List;

public class RepoOperacionesIngresos {
    private List<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();

    public RepoOperacionesIngresos () {
        operaciones = new ArrayList<OperacionIngreso>();
    }

    public ArrayList<OperacionIngreso> getAll () {
        String query = "from OperacionIngreso";
        ArrayList<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();
        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        return operaciones;
    }

    public OperacionIngreso get (int id) {
        String query = "from OperacionIngreso where id = " + id;
        OperacionIngreso operacion = new OperacionIngreso();
        EntityManagerHelper.createQuery(query).getFirstResult();
        return operacion;
    }
}
