package Repos;

import Usuarios.Usuario;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {
    private List<Usuario> usuarios;

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
