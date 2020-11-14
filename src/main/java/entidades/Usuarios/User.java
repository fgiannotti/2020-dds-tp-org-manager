package entidades.Usuarios;

import entidades.Organizaciones.Organizacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("basico")
public class User extends Usuario {

    public User (String nombre, String password, Organizacion organizacion) {
        super(nombre,password,organizacion);
    }
    public User(){}
    public String toString(){return "basico";}

}
