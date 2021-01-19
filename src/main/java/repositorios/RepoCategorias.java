package repositorios;

import db.EntityManagerHelper;
import entidades.Organizaciones.Categoria;

import java.util.ArrayList;
import java.util.List;

public class RepoCategorias {

    public Categoria find(int id){
        return (Categoria) EntityManagerHelper.createQuery("FROM Categoria WHERE id ='"+id+"'").getSingleResult();
    }

    public ArrayList<Categoria> getAll(){
        return (ArrayList<Categoria>) EntityManagerHelper.createQuery("FROM Categoria").getResultList();
    }

    public RepoCategorias() {
    }
}
