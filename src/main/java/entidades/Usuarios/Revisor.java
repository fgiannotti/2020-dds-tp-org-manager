package entidades.Usuarios;

import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.Organizaciones.Organizacion;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("revisor")
public class Revisor extends Usuario {

    @OneToOne(cascade = {CascadeType.ALL})
    private BandejaDeEntrada bandejaDeEntrada;

    public Revisor(BandejaDeEntrada bandejaDeEntrada){
        this.bandejaDeEntrada = bandejaDeEntrada;
    }

    public Revisor (String nombre, String password, Organizacion organizacion,BandejaDeEntrada bandejaOpcional) {
        super(nombre,password,organizacion);
        this.bandejaDeEntrada = bandejaOpcional != null ? bandejaOpcional : this.bandejaDeEntrada;
    }

    public BandejaDeEntrada getBandejaDeEntrada() {
        return bandejaDeEntrada;
    }

    public void setBandejaDeEntrada(BandejaDeEntrada bandejaDeEntrada) {
        this.bandejaDeEntrada = bandejaDeEntrada;
    }


    public void verMensajes(){
        bandejaDeEntrada.mostrarMensajes();
    }
    public Revisor(){}
    public String toString(){return "revisor";}

}
