package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.Presupuesto;
import entidades.Organizaciones.Categoria;

import java.util.ArrayList;

public class RepoPresupuestos {

    public Presupuesto find(int id){
        return (Presupuesto) EntityManagerHelper.createQuery("FROM Presupuesto WHERE id ='"+id+"'").getSingleResult();
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
}
