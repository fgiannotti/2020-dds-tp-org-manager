package Builders;

import Organizaciones.*;

import static java.util.Objects.isNull;

public class EmpresaBuilder {

    private String nombreFicticio;
    private String razonSocial;
    private Long cuit;
    private Integer dirPostal;
    private Integer codigoInscripcion;
    private Integer cantidadPersonal;
    private Actividad actividad;
    private Float promedioVentas;
    private TipoEmpresa tipo;
    private Categorizador categorizador;
    private Empresa empresa;

    public EmpresaBuilder(){
        this.empresa = new Empresa();
    }

    /**
     * Getters and setters
     */

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public Integer getDirPostal() {
        return dirPostal;
    }

    public void setDirPostal(Integer dirPostal) {
        this.dirPostal = dirPostal;
    }

    public Integer getCodigoInscripcion() {
        return codigoInscripcion;
    }

    public void setCodigoInscripcion(Integer codigoInscripcion) {
        this.codigoInscripcion = codigoInscripcion;
    }

    public Integer getCantidadPersonal() {
        return cantidadPersonal;
    }

    public void setCantidadPersonal(Integer cantidadPersonal) {
        this.cantidadPersonal = cantidadPersonal;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Float getPromedioVentas() {
        return promedioVentas;
    }

    public void setPromedioVentas(Float promedioVentas) {
        this.promedioVentas = promedioVentas;
    }

    public TipoEmpresa getTipo() {
        return tipo;
    }

    public void setTipo(TipoEmpresa tipo) {
        this.tipo = tipo;
    }

    public Categorizador getCategorizador() {
        return categorizador;
    }

    public void setCategorizador(Categorizador categorizador) {
        this.categorizador = categorizador;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /*
    Methods
     */

    public EmpresaBuilder agregarNombre(String nombre){
        this.empresa.setNombreFicticio(nombre);
        return this;
    }

    public EmpresaBuilder agregarRazonSocial(String razonSocial){
        this.empresa.setRazonSocial(razonSocial);
        return this;
    }

    public EmpresaBuilder agregarCuit(Long cuit){
        this.empresa.setCuit(cuit);
        return this;
    }

    public EmpresaBuilder agregarCodigoPostal(Integer dirPostal){
        this.empresa.setDirPostal(dirPostal);
        return this;
    }

    public EmpresaBuilder agregarCodigoDeInscripcion(Integer codigoInscripcion){
        this.empresa.setCodigoInscripcion(codigoInscripcion);
        return this;
    }

    public EmpresaBuilder agregarCantidadDePersonal(Integer cantidadDePersonal){
        this.empresa.setCantidadPersonal(cantidadDePersonal);
        return this;
    }

    public EmpresaBuilder agregarActividad(Actividad actividad){
        this.empresa.setActividad(actividad);
        return this;
    }

    public EmpresaBuilder agregarPromedioDeVentas(Float promedioVentas){
        this.empresa.setPromedioVentas(promedioVentas);
        return this;
    }

    public EmpresaBuilder agregarEntidadesHijas(Base... base){
        this.empresa.addEntidadHija(base);
        return this;
    }

    public Empresa build() throws Exception {
        if (isNull(this.empresa.getNombreFicticio())) {
            throw new Exception("No se asigno el nombre");
        }

        if (isNull(this.empresa.getRazonSocial())) {
            throw new Exception("No se asigno razon social");
        }

        if (isNull(this.empresa.getCuit())) {
            throw new Exception("No se un asigno numero de CUIT");
        }

        if (isNull(this.empresa.getDirPostal())) {
            throw new Exception("No se un asigno direccion postal");
        }

        if (isNull(this.empresa.getCantidadPersonal())) {
            throw new Exception("No se asigno cantidad de personal");
        }

        if (isNull(this.empresa.getActividad())) {
            throw new Exception("No se asigno Actividad");
        }

        if (isNull(this.empresa.getPromedioVentas())) {
            throw new Exception("No se asigno promedio de ventas");
        }

        if (isNull(this.empresa.getTipo())) {
            throw new Exception("No se asigno el tipo de empresa");
        }

        if(!this.cumpleVentasTotales()) {
            throw new Exception("La cantidad de ventas anuales debe ser un valor entre 9_900_000 y 676_810_000, el valor actual es:" + this.empresa.getPromedioVentas());
        }

        if(!this.cumpleCantidadPersonal()) {
            throw new Exception("La cantidad de personal debe ser un valor entre 12 y 215, el valor actual es:" + this.empresa.getCantidadPersonal());
        }

        if(this.categorizador == null) {
            this.categorizador = new Categorizador();
        }

        this.tipo = this.categorizador.categorizar(this.cantidadPersonal, this.actividad, this.promedioVentas);

        return this.empresa;

    }
    private Boolean cumpleVentasTotales(){
        return ((this.empresa.getPromedioVentas() > 9900000) && (this.empresa.getPromedioVentas() < 676810000));
    }

    private Boolean cumpleCantidadPersonal(){
        return ((this.empresa.getCantidadPersonal() > 12) && (this.empresa.getCantidadPersonal() < 215));
    }
    }