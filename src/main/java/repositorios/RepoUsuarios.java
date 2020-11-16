package repositorios;

import db.EntityManagerHelper;
import entidades.Usuarios.Revisor;
import entidades.Usuarios.User;
import entidades.Usuarios.Usuario;

public class RepoUsuarios {
    public RepoUsuarios () {
    }

    public Usuario buscarPorNombre(String nombre) {
        Usuario unUsuario;

        String query = "from Usuario where nombre = '" +  nombre + "'";
        unUsuario = (Usuario) EntityManagerHelper.createQuery(query).getResultList().get(0);
        return unUsuario;
    }

    public User buscarBasicoPorNombre(String nombre) {
        User unUsuario;

        String query = "from Usuario where nombre = '" +  nombre + "' and DTYPE = 'basico' ";
        unUsuario = (User) EntityManagerHelper.createQuery(query).getResultList().get(0);
        return unUsuario;
    }

    public Revisor buscarRevisorPorNombre(String nombre) {
        Revisor unUsuario;
        String query = "from Usuario where nombre = '" +  nombre + "' and DTYPE = 'revisor'";
        unUsuario = (Revisor) EntityManagerHelper.createQuery(query).getResultList().get(0);
        //TODO: chequear si el usuario tiene bandeja
        return unUsuario;
    }
    public void agregar(Usuario nuevoUsuario) {//TODO: persistir si no existe
    }
}
