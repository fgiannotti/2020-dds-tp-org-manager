package repositorios;

import entidades.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {
    private List<Usuario> usuarios = new ArrayList<Usuario>();

    public RepoUsuarios () {
        usuarios = new ArrayList<Usuario>();
    }

    public Usuario buscarPorNombre(String nombre) {
        Usuario unUsuario = this.usuarios.stream()
                .filter(usuario -> nombre.equals(usuario.getNombre()))
                .findAny()
                .orElse(null);
        return unUsuario;
    }

    public void agregar(Usuario nuevoUsuario) {
        this.usuarios.add(nuevoUsuario);
    }
}
