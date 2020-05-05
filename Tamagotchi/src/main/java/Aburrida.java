public class Aburrida implements Estado {

    public void jugar(Mascota mascota){
        mascota.cambiarEstado(new Contenta());
    }
    public void comer(Mascota mascota){
       if(mascota.getTiempoAburrida() > 80)
           mascota.cambiarEstado(new Contenta());
    }
    public Boolean estoyHambrienta(){
        return false;
    }
    public Boolean estoyMalHumor(){
        return false;
    }
    public Boolean estoyContenta(){
        return false;
    }
    public Boolean estoyAburrida(){
        return true;
    }
}
