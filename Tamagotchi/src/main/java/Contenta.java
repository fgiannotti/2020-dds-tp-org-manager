public class Contenta implements Estado {

    public void jugar(Mascota mascota){
        if(mascota.getVecesQueJugo() <= 5)
            mascota.aumentarFelicidad(2);
        else
            mascota.cambiarEstado(new Hambrienta());
    }

    public void comer(Mascota mascota){
        mascota.aumentarFelicidad(1);
    }

    public Boolean estoyHambrienta(){
        return false;
    }

    public Boolean estoyMalHumor(){
        return false;
    }

    public Boolean estoyContenta(){
        return true;
    }

    public Boolean estoyAburrida(){
        return false;
    }

}
