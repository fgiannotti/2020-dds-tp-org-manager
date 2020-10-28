package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.Operacion;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;

import java.util.ArrayList;
import java.util.List;

public class RepoOperaciones {
    private List<Operacion> operaciones = new ArrayList<Operacion>();

    public RepoOperaciones () {
        operaciones = new ArrayList<Operacion>();
    }

    public List<Operacion> mostrarTodos() {
        EntityManagerHelper.createQuery("from OperacionIngreso").getResultList().forEach((a) -> { operaciones.add((OperacionIngreso)a); });
        EntityManagerHelper.createQuery("from OperacionEgreso").getResultList().forEach((a) -> { operaciones.add((OperacionEgreso)a); });
        return operaciones;
    }
}
