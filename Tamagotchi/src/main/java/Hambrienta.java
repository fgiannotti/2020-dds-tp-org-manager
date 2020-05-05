public class Hambrienta implements Estado {
    public void jugar(Mascota mascota){
        return; //excepcion
    }
    public void comer(Mascota mascota){
       mascota.cambiarEstado(new Contenta());
    }
    public Boolean estoyHambrienta(){
        return true;
    }
    public Boolean estoyMalHumor(){
        return false;
    }
    public Boolean estoyContenta(){
        return false;
    }
    public Boolean estoyAburrida(){
        return false;
    }
}
