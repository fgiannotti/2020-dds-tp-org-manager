package repositorios;

import db.EntityManagerHelper;
import entidades.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    public RepoUsuarios () {
        usuarios = new ArrayList<Usuario>();
    }

    public Usuario buscarPorNombre(String nombre) {
        String query = "from Usuario where nombre = '" +  nombre + "'";
        Usuario unUsuario = this.usuarios.stream()
                .filter(usuario -> nombre.equals(usuario.getNombre()))
                .findAny()
                .orElse(null);
        unUsuario = (Usuario) EntityManagerHelper.createQuery(query).getResultList().get(0);
        //TODO: chequear si el usuario tiene bandeja
        return unUsuario;
    }

    public void agregar(Usuario nuevoUsuario) {
        this.usuarios.add(nuevoUsuario);
    }
}
