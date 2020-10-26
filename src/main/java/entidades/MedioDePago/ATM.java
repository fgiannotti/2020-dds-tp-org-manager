package entidades.MedioDePago;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("atm")
public class ATM extends MedioDePago {
    public ATM() {
    }

    public ATM(String medio, int numero) {
    }
}
