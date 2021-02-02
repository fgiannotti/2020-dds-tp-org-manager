package entidades.Operaciones;

import entidades.Items.Item;
import db.Converters.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jdk.nashorn.internal.objects.NativeArray.length;

@Entity
@Table(name = "comprobantes")
public class Comprobante extends EntidadPersistente {

    @Column(name = "numero_comprobante")
    private String numeroComprobante;

    @Column(name = "tipo_comprobante")
    private String tipoComprobante;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    private List<Item> items = new ArrayList<Item>();

    public Comprobante(String numeroComprobante, String tipoComprobante, List<Item> items) {
        this.numeroComprobante = numeroComprobante;
        this.tipoComprobante = tipoComprobante;
        this.items = items;
    }

    public Comprobante(List<Item> items) {
        this.numeroComprobante = String.valueOf(this.hashCode());
        this.items = items;
    }

    public Comprobante() {
        this.numeroComprobante = String.valueOf(this.hashCode());
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Comprobante{" +
                "numeroComprobante=" + numeroComprobante +
                ", items=" + items.size() +
                '}';
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comprobante)) return false;
        Comprobante that = (Comprobante) o;
        return getNumeroComprobante().equals(that.getNumeroComprobante()) &&
                getTipoComprobante().equals(that.getTipoComprobante());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroComprobante(), getTipoComprobante());
    }
}



