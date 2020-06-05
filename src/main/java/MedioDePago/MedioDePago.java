package MedioDePago;

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
      this.medio = medio;
      this.numero = numero;
   }
}
