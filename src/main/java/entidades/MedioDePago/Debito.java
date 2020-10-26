package entidades.MedioDePago;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("debito")
public class Debito extends MedioDePago {

    public Debito() {
    }

    public Debito(String medio, int numero) {
        this.medio = medio;
        this.numero = numero;
    }
}
