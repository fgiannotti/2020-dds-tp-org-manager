package entidades.Usuarios;

import entidades.BandejaDeEntrada.BandejaDeEntrada;
import entidades.BandejaDeEntrada.Resultado;
import entidades.Organizaciones.Organizacion;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("revisor")
public class Revisor extends Usuario {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bandeja_id")
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


    public List<Resultado> verMensajes(){
        return bandejaDeEntrada.mostrarMensajes();
    }
    public Revisor(){}
    public String toString(){return "revisor";}

}
