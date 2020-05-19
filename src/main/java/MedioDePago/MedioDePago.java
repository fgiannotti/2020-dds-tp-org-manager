package MedioDePago;

public abstract class MedioDePago {
   private String medio;
   private int numero;

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
}
