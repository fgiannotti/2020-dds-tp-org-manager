package repositorios;

import db.EntityManagerHelper;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.CriterioDeEmpresa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class RepoCategorias {
    EntityManager em = EntityManagerHelper.getEntityManager();

    public Categoria find(int id){
        return (Categoria) EntityManagerHelper.createQuery("FROM Categoria WHERE id ='"+id+"'").getSingleResult();
    }

    public ArrayList<Categoria> getAll(){
        return (ArrayList<Categoria>) em.createQuery("FROM Categoria").getResultList();
    }
    public int persistCategoria(Categoria categoria){
        em.getTransaction().begin();
        try{
            em.persist(categoria);

        }catch (PersistenceException e){
            em.getTransaction().rollback();
            em.getTransaction().begin();
            em.merge(categoria);
        }
        em.getTransaction().commit();
        return categoria.getId();
    }

    public RepoCategorias() {
    }

    public List<Categoria> getAllFromCritIDs(List<Integer> critIDs) {
        String queryIN = "";
        for(Integer id: critIDs){queryIN= queryIN+id+",";}
        List<Categoria> categorias = new ArrayList<>();

        queryIN = queryIN.substring(0,queryIN.length()-1);
        em.createQuery("FROM Categoria where criterio_id IN (" + queryIN + ")").getResultList().forEach((a) -> {
            categorias.add((Categoria) a);
        });
        return categorias;
    }
}
