package entidades.Usuarios;

import entidades.Organizaciones.CriterioDeEmpresa;

public class Admin implements ClaseUsuario{

    public void otorgarJerarquia(CriterioDeEmpresa padre, CriterioDeEmpresa hijo){
        padre.agregarCriterio(hijo);
    }
}
