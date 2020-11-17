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

    public ArrayList<OperacionIngreso> getAll() {
        String query = "from OperacionIngreso";
        ArrayList<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();
        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        return operaciones;
    }
    public OperacionIngreso find(int id) {
        String query = "from OperacionIngreso";
        ArrayList<OperacionIngreso> operaciones = new ArrayList<OperacionIngreso>();

        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        for (OperacionIngreso operacion : operaciones) {
            System.out.println("Busco ID:"+String.valueOf(id)+ "contra: "+String.valueOf(operacion.getId()));
            if (operacion.getId() == id){
                return operacion;
            }
        }
        throw new RuntimeException(("Entidad no encontrada. id: ").concat(String.valueOf(id)));
    }
    public void guardar(OperacionIngreso nuevoIngreso) {

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(nuevoIngreso);
        EntityManagerHelper.commit();
    }
}
