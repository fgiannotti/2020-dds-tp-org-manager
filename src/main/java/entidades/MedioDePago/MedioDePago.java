package entidades.MedioDePago;

import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "medios_de_pago")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedioDePago extends EntidadPersistente {

   @Column(name= "medio" , nullable = false)
   protected String medio;

   @Column(name="numero")
   protected String numero;

   public String getMedio() {
      return medio;
   }

   public String getNumero() {
      return numero;
   }

   public MedioDePago() {
   }

   public MedioDePago(String medio, String numero) {
      this.medio = Objects.requireNonNull(medio, "El medio no puede ser nulo");
      this.numero = numero;
   }

}
