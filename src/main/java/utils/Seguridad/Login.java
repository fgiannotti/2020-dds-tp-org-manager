package utils.Seguridad;

import entidades.Organizaciones.Organizacion;

public class Login {
    private Autenticador autenticador;

    public Boolean login(String nombre, String password) throws UserBlockedException {
        try {
            this.autenticador.checkUser(nombre, password);
        } catch (InvalidPasswordException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (UserBlockedException e) {
            System.out.println(e.getMessage());
            throw (e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void register(String nombre, Organizacion organizacion, String password) {
        try {
            autenticador.crearUsuario(nombre, organizacion, password);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public Login(Autenticador autenticador) {
        this.autenticador = autenticador;
    }
}
