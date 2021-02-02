package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.Organizacion;

import javax.persistence.EntityManager;
import java.util.*;

public class RepoPresupuestos {
    EntityManager em = EntityManagerHelper.getEntityManager();
    RepoOperacionesEgresos repoEgresos = new RepoOperacionesEgresos();
    public Presupuesto find(int id){
        return (Presupuesto) em.createQuery("FROM Presupuesto WHERE id ='"+id+"'").getSingleResult();
    }

    public List<Presupuesto> findByProv(int proveedorID){
        return (List<Presupuesto>) em.createQuery("FROM Presupuesto WHERE proveedor_id ='"+proveedorID+"'").getResultList();
    }

    public RepoPresupuestos() {
    }

    public void asociarCategorias(Presupuesto presupuesto, ArrayList<Categoria> categorias) {
        presupuesto.getCategorias().addAll(categorias);
        try {
            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().merge(presupuesto);
            EntityManagerHelper.commit();
        } catch (Exception e){
            System.err.println("ERROR asociando presupuesto con categorias: "+e.getMessage());
            throw(e);
        }
    }

    public ArrayList<Presupuesto> getAllByOrg(Organizacion organizacion) {
        Set<Presupuesto> result = new HashSet<>();
        ArrayList<OperacionEgreso> egresos = repoEgresos.getAllByOrg(organizacion);
        for(OperacionEgreso e : egresos){
            result.addAll(e.getPresupuestosPreliminares());
        }
        return new ArrayList<>(result);
    }
}
