package entidades.Usuarios;

import entidades.BandejaDeEntrada.BandejaDeEntrada;

public class Revisor implements ClaseUsuario {

    private BandejaDeEntrada bandejaDeEntrada;

    public Revisor(BandejaDeEntrada bandejaDeEntrada){
        this.bandejaDeEntrada = bandejaDeEntrada;
    }
    public void verMensajes(){
        bandejaDeEntrada.mostrarMensajes();
    }
}
