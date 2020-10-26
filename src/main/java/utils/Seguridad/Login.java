package utils.Seguridad;

import entidades.Organizaciones.Organizacion;

public class Login {
    private Autenticador autenticador;
    public Boolean login (String nombre, String password) {
        try {
            return this.autenticador.checkUser(nombre, password);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().equals("Contraseña incorrecta")){
                return false;
            }
            throw(e);
        }
    }

    public void register (String nombre, Organizacion organizacion, String password) {
        try {
            autenticador.crearUsuario(nombre, organizacion, password);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public Login (Autenticador autenticador) {
        this.autenticador = autenticador;
    }
}
