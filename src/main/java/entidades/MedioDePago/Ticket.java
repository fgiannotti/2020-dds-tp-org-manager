package entidades.MedioDePago;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ticket")
public class Ticket extends MedioDePago {
    public Ticket(String medio, String numero) {
        super(medio,numero);
    }

    public Ticket() {
    }
}
