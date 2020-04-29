package Usuarios;

import Seguridad.Autenticador;
import Organizaciones.Organizacion;

public class UsuarioBuilder {
    private Autenticador autenticador;

    //Sólo acá creamos usuarios para mantener un control sobre la creación de los mismos
    public Usuario crearUsuario (ClaseUsuario clase, String nombre, String password, Organizacion organizacion) throws RuntimeException {
        if (! autenticador.controlDePassword(password) ) {
            throw new RuntimeException("Tu contraseña es malarda");
        }
        return new Usuario(nombre, password, organizacion, clase);
    }

    public Autenticador getAutenticador() {
        return autenticador;
    }

    public void setAutenticador(Autenticador autenticador) {
        this.autenticador = autenticador;
    }

    public UsuarioBuilder (Autenticador aut) {
        this.autenticador = aut;
    }
}
