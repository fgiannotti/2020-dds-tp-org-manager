package Seguridad;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

public class Autenticador {
    public Boolean controlDePassword(String password) {
        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);
        return (strength.getScore()>2 && password.length()>8);
    }

    public Autenticador () {
    }
}
