package MedioDePago;

public abstract class MedioDePago {
   protected String medio;
   protected int numero;

   public String getMedio() {
      return medio;
   }

   public void setMedio(String medio) {
      this.medio = medio;
   }

   public int getNumero() {
      return numero;
   }

   public void setNumero(int numero) {
      this.numero = numero;
   }

   public MedioDePago(String medio, int numero) {
      this.medio = medio;
      this.numero = numero;
   }

   @Override
   public String toString() {
      return "MedioDePago{" +
              "medio='" + medio + '\'' +
              ", numero=" + numero +
              '}';
   }
}
