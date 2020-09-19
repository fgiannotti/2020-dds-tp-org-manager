package MedioDePago;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("credito")
public class Credito extends MedioDePago{

    public Credito(String medio, int numero) {
    }

    public Credito() {
    }
}
