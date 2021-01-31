package repositorios;

import db.EntityManagerHelper;
import entidades.Usuarios.Revisor;
import entidades.Usuarios.User;
import entidades.Usuarios.Usuario;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoUsuarios {
    public RepoUsuarios() {
    }
    EntityManager em = EntityManagerHelper.getEntityManager();

    public Usuario buscarPorNombre(String nombre) throws UserNotFoundException {
        Usuario unUsuario;
        String query = "from Usuario where nombre = '" + nombre + "'";
        ArrayList<Usuario> usuarios = (ArrayList<Usuario>) em.createQuery(query).getResultList();
        if (usuarios.isEmpty()) {
            throw new UserNotFoundException("Error: usuario " + nombre + " no fue encontrado");
        }
        return usuarios.get(0);
    }
    public Usuario find(String id) {
        Usuario unUsuario;

        String query = "from Usuario where id = '" + id + "'";
        unUsuario = (Usuario) em.createQuery(query).getResultList().get(0);
        return unUsuario;
    }

    public User buscarBasicoPorNombre(String nombre) {
        User unUsuario;

        String query = "from Usuario where nombre = '" + nombre + "' and DTYPE = 'basico' ";
        unUsuario = (User) em.createQuery(query).getResultList().get(0);
        return unUsuario;
    }

    public Revisor buscarRevisorPorNombre(String nombre) {
        Revisor unUsuario;
        String query = "from Usuario where nombre = '" + nombre + "' and DTYPE = 'revisor'";
        unUsuario = (Revisor) em.createQuery(query).getResultList().get(0);
        return unUsuario;
    }

    public List<Revisor> buscarRevisoresPorOrganizacion(int orgID) {
        String query = "from Usuario where organizacion_id = '" + orgID + "' and DTYPE = 'revisor'";
        List<Revisor> revisores = new ArrayList<Revisor>();
        List revisoresDB = em.createQuery(query).getResultList();

        for (Object revisor : revisoresDB) {
            revisores.add((Revisor) revisor);
        }

        return revisores;
    }

    public void agregar(Usuario nuevoUsuario) {
    }
}
