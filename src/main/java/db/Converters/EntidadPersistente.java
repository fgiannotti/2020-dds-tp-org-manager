package db.Converters;

import javax.persistence.*;

@MappedSuperclass
public class EntidadPersistente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Transient
    private String idCacheado;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getIdCacheado() {
        return idCacheado;
    }

    public void setIdCacheado(String idCacheado) {
        this.idCacheado = idCacheado;
    }
}
