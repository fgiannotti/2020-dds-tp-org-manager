public interface Estado {

    public void jugar(Mascota mascota);
    public void comer(Mascota mascota);
    public Boolean estoyHambrienta();
    public Boolean estoyMalHumor();
    public Boolean estoyContenta();
    public Boolean estoyAburrida();

}
