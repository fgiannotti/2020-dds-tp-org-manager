package repositorios;

import db.EntityManagerHelper;
import entidades.Organizaciones.Categoria;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class RepoCategorias {

    public Categoria find(int id){
        return (Categoria) EntityManagerHelper.createQuery("FROM Categoria WHERE id ='"+id+"'").getSingleResult();
    }

    public ArrayList<Categoria> getAll(){
        return (ArrayList<Categoria>) EntityManagerHelper.createQuery("FROM Categoria").getResultList();
    }
    public int persistCategoria(Categoria categoria){
        EntityManagerHelper.beginTransaction();
        try{
            EntityManagerHelper.getEntityManager().persist(categoria);

        }catch (PersistenceException e){
            EntityManagerHelper.rollback();
            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().merge(categoria);
        }
        EntityManagerHelper.commit();
        return categoria.getId();
    }

    public RepoCategorias() {
    }
}
