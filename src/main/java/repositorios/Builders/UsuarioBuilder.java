package repositorios.Builders;

import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.Organizaciones.Organizacion;
import entidades.Usuarios.*;
import utils.Seguridad.Autenticador;

public class UsuarioBuilder {
    private Autenticador autenticador;

    //Sólo acá creamos usuarios para mantener un control sobre la creación de los mismos
    public Usuario crearUsuario (TipoUsuario tipoUsuario, String nombre, String password, Organizacion organizacion, BandejaDeEntrada bandejaDeEntrada){
        if (tipoUsuario.toString().equalsIgnoreCase("revisor")){
            return new Revisor(nombre,password,organizacion,bandejaDeEntrada);
        }else{
            return new User(nombre, password, organizacion);
        }
    }

    public Autenticador getAutenticador() {
        return autenticador;
    }

    public void setAutenticador(Autenticador autenticador) {
        this.autenticador = autenticador;
    }

    public UsuarioBuilder () {}
}
