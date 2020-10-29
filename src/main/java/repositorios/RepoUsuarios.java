package repositorios;

import db.EntityManagerHelper;
import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.Estrategias.FiltroPorFecha;
import entidades.Usuarios.Revisor;
import entidades.Usuarios.TipoUsuario;
import entidades.Usuarios.User;
import entidades.Usuarios.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (unUsuario.getTipoUsuario() == TipoUsuario.REVISOR){
            unUsuario.setClaseUsuario(new Revisor(new BandejaDeEntrada(new ArrayList<>(Arrays.asList(new FiltroPorFecha(LocalDate.of(2020,1,1))))))); //TODO: darle una bandeja
        }else{
            unUsuario.setClaseUsuario(new User());
        }
        return unUsuario;
    }

    public void agregar(Usuario nuevoUsuario) {
        //TODO: Falta agregarlo a la DB acá
        this.usuarios.add(nuevoUsuario);
    }
}
