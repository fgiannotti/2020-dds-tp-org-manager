package Organizaciones;

public abstract class Juridica extends Organizacion {
    protected String razonSocial;
    protected int cuit;
    protected int dirPostal;
    protected int codigo;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getCuit() {
        return cuit;
    }

    public void setCuit(int cuit) {
        this.cuit = cuit;
    }

    public int getDirPostal() {
        return dirPostal;
    }

    public void setDirPostal(int dirPostal) {
        this.dirPostal = dirPostal;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
