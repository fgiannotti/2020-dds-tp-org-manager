
public class Mascota {
    private String nombre;
    private Estado estado;
    private int tiempoAburrida;
    private int nivelFelicidad;
    private int vecesQueJugo;

    public void comer() {
        estado.comer(this);
    }

    //returns true on success
    public void jugar() {
        estado.jugar(this);
        this.vecesQueJugo++;
    }

    public boolean puedeJugar(){
        return this.estado.estoyContenta() || this.estado.estoyAburrida();
    }

    public Estado getEstado(){
        return this.estado;
    }

    public void cambiarEstado(Estado nuevoEstado){
        this.estado = nuevoEstado;
    }

    public int getNivelFelicidad(){
        return this.nivelFelicidad;
    }

    public void aumentarFelicidad(int masFelicidad){
        this.nivelFelicidad += masFelicidad;
    }

    public int getVecesQueJugo(){
        return this.vecesQueJugo;
    }

    public int getTiempoAburrida(){
        return this.tiempoAburrida;
    }
}