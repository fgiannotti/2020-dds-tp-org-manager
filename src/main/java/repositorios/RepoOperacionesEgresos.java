package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Operaciones.Presupuesto;
import entidades.Operaciones.Proveedor;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.Organizacion;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RepoOperacionesEgresos {
    private List<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
    private RepoOrganizaciones repoOrg;

    public RepoOperacionesEgresos() {
        operaciones = new ArrayList<OperacionEgreso>();
        this.repoOrg = new RepoOrganizaciones();
    }

    public ArrayList<OperacionEgreso> getAll() {
        String query = "from OperacionEgreso";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> {
            operaciones.add((OperacionEgreso) a);
        });
        return operaciones;
    }

    public ArrayList<OperacionEgreso> getAllByOrg(Organizacion org) {
        String query = "from OperacionEgreso WHERE organizacion_id = '" + org.getId() + "'";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> {
            operaciones.add((OperacionEgreso) a);
        });
        return operaciones;
    }

    public OperacionEgreso find(int id) {
        String query = "from OperacionEgreso";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        EntityManagerHelper.createQuery(query).getResultList().forEach((a) -> {
            operaciones.add((OperacionEgreso) a);
        });
        for (OperacionEgreso operacion : operaciones) {
            if (operacion.getId() == id) {
                return operacion;
            }
        }
        throw new RuntimeException(("Entidad no encontrada. id: ").concat(String.valueOf(id)));
    }

    public void agregar(OperacionEgreso nuevoEgreso) {
        this.operaciones.add(nuevoEgreso);
        EntityManagerHelper.beginTransaction();


        try {
            for (Proveedor p : nuevoEgreso.getProveedores()) {
                EntityManagerHelper.getEntityManager().merge(p);
            }
            EntityManagerHelper.getEntityManager().merge(nuevoEgreso);
        } catch (Exception e) {
            System.err.println("FAILED MERGING. TRYING TO PRESIST." + e.getMessage() + "\n");

            try {
                EntityManagerHelper.getEntityManager().merge(nuevoEgreso.getMedioDePago());
                for (Proveedor p : nuevoEgreso.getProveedores()) {
                    EntityManagerHelper.getEntityManager().merge(p);
                }
                for (Presupuesto p : nuevoEgreso.getPresupuestosPreliminares()) {
                    EntityManagerHelper.getEntityManager().merge(p);
                }
            } catch (Exception e2) {
                System.err.println("FAIL MERGEANDO MPAGO, PROVS Y PRESUS" + e2.getMessage());
            }

            EntityManagerHelper.getEntityManager().persist(nuevoEgreso);
        }
        EntityManagerHelper.commit();

    }

    public OperacionEgreso get(int id) {
        String query = "from OperacionEgreso where id = " + id;
        return (OperacionEgreso) EntityManagerHelper.createQuery(query).getSingleResult();
    }

    public void asociarIngreso(OperacionEgreso egreso, OperacionIngreso ingreso) {
        int orgID = repoOrg.findOrgID(egreso.getOrganizacion().getNombreFicticio());
        egreso.setIngreso(ingreso);
        egreso.getOrganizacion().setId(orgID);
        ingreso.getOperacionesEgreso().add(egreso);
        try {
            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().merge(egreso);
            EntityManagerHelper.commit();
        } catch (Exception e) {
            System.err.println("ERROR asociando egreso con ingreso: " + e.getMessage());
            throw (e);
        }
        /*EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(ingreso);
        EntityManagerHelper.commit();*/
        //no es necesario creo, ingreso deberia traerte su lista sin necesidad de persistirlo
    }

    public void asociarCategorias(OperacionEgreso egreso, ArrayList<Categoria> categorias) {
        int orgID = repoOrg.findOrgID(egreso.getOrganizacion().getNombreFicticio());
        egreso.getOrganizacion().setId(orgID);

        List<Categoria> categoriasTotales = egreso.getCategorias();
        categoriasTotales.addAll(categorias);
        egreso.setCategorias(categoriasTotales);
        try {
            for (Categoria c : categoriasTotales) {
                EntityManagerHelper.getEntityManager().merge(c);
                EntityManagerHelper.getEntityManager().merge(c.getCriterio());
            }

        } catch (Exception e) {
            System.err.println("manqueada merge categorias a asociar" + e.getMessage() + "\n");
        }

        try {

            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().persist(egreso);
            EntityManagerHelper.commit();

        } catch (Exception e) {
            System.err.println("[egreso con categorias] ERROR persistiendo, intentando merge: " + e.getMessage());
            try {
                for (Categoria cat : categorias) {
                    EntityManagerHelper.getEntityManager().persist(cat);
                    EntityManagerHelper.getEntityManager().persist(cat.getCriterio());
                }

                EntityManagerHelper.getEntityManager().merge(egreso);
            } catch (Exception e1) {
                System.err.println("[egreso con categorias] ERROR merge: " + e1.getMessage());
                throw (e1);
            }
            System.err.println("merge OK.");

        }
    }
}
