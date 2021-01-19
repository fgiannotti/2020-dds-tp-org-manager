package repositorios;

import db.EntityManagerHelper;
import entidades.Organizaciones.Categoria;

public class RepoCategorias {

    public Categoria find(int id){
        return (Categoria) EntityManagerHelper.createQuery("FROM Categoria WHERE id ='"+id+"'").getSingleResult();
    }
    public RepoCategorias() {
    }
}
