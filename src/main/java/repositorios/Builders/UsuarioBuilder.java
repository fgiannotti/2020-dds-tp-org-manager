package repositorios.Builders;

import entidades.Usuarios.ClaseUsuario;
import entidades.Usuarios.Usuario;
import utils.Seguridad.Autenticador;
import entidades.Organizaciones.Organizacion;

public class UsuarioBuilder {
    private Autenticador autenticador;

    //Sólo acá creamos usuarios para mantener un control sobre la creación de los mismos
    public Usuario crearUsuario (ClaseUsuario clase, String nombre, String password, Organizacion organizacion){
        return new Usuario(nombre, password, organizacion, clase);
    }

    public Autenticador getAutenticador() {
        return autenticador;
    }

    public void setAutenticador(Autenticador autenticador) {
        this.autenticador = autenticador;
    }

    public UsuarioBuilder () {}
}
