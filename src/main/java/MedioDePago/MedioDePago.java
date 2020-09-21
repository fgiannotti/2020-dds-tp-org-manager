package MedioDePago;

import converters.EntidadPersistente;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "medios_de_pago")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedioDePago extends EntidadPersistente {

   @Column(name= "medio" , nullable = false)
   protected String medio;

   @Column(name="numero")
   protected int numero;

   public String getMedio() {
      return medio;
   }

   public int getNumero() {
      return numero;
   }

   public MedioDePago() {
   }

   public MedioDePago(String medio, int numero) {
      this.medio = Objects.requireNonNull(medio, "El medio no puede ser nulo");
      this.numero = numero;
   }

}
