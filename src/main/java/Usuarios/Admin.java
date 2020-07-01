package Usuarios;

import Organizaciones.CriterioDeEmpresa;

public class Admin implements ClaseUsuario{

    public void otorgarJerarquia(CriterioDeEmpresa padre, CriterioDeEmpresa hijo){
        padre.agregarCriterio(hijo);
    }
}
