package entidades.MedioDePago;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("accmoney")
public class AccountMoney extends MedioDePago {

    public AccountMoney() {
    }

    public AccountMoney(String medio, int numero) {
    }
}
