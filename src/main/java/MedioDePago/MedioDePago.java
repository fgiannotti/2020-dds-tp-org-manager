package MedioDePago;

import java.util.Objects;

public abstract class MedioDePago {

   protected String medio;
   protected int numero;

   public String getMedio() {
      return medio;
   }

   public int getNumero() {
      return numero;
   }

   public MedioDePago(String medio, int numero) {
      this.medio = Objects.requireNonNull(medio, "El medio no puede ser nulo");
      this.numero = Objects.requireNonNull(numero, "El numero no puede ser nulo");
   }
}
