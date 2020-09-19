package MedioDePago;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("debito")
public class Debito extends MedioDePago {

    public Debito(String medio, int numero) {
    }
}
