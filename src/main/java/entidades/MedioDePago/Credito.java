package entidades.MedioDePago;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("credito")
public class Credito extends MedioDePago{

    public Credito(String medio, String numero) {
        super(medio,numero);
    }

    public Credito() {
    }
}
