package repositorios;

import db.EntityManagerHelper;
import entidades.Operaciones.OperacionEgreso;
import entidades.Operaciones.OperacionIngreso;
import entidades.Operaciones.Presupuesto;
import entidades.Operaciones.Proveedor;
import entidades.Organizaciones.Categoria;
import entidades.Organizaciones.Organizacion;
import org.hibernate.PersistentObjectException;
import org.hibernate.metamodel.source.annotations.entity.EntityClass;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class RepoOperacionesEgresos {
    private List<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
    private RepoOrganizaciones repoOrg;
    EntityManager em = EntityManagerHelper.getEntityManager();

    public RepoOperacionesEgresos() {
        operaciones = new ArrayList<OperacionEgreso>();
        this.repoOrg = new RepoOrganizaciones();
    }

    public ArrayList<OperacionEgreso> getAll() {
        String query = "from OperacionEgreso";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        em.createQuery(query).getResultList().forEach((a) -> {
            operaciones.add((OperacionEgreso) a);
        });
        return operaciones;
    }

    public ArrayList<OperacionEgreso> getAllByOrg(Organizacion org) {
        String query = "from OperacionEgreso WHERE organizacion_id = '" + org.getId() + "'";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        em.createQuery(query).getResultList().forEach((a) -> {
            operaciones.add((OperacionEgreso) a);
        });
        return operaciones;
    }

    public OperacionEgreso find(int id) {
        String query = "from OperacionEgreso";
        ArrayList<OperacionEgreso> operaciones = new ArrayList<OperacionEgreso>();
        em.createQuery(query).getResultList().forEach((a) -> {
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
        try {
            em.getTransaction().begin();
            em.persist(nuevoEgreso);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("persist falló. Detached entity, mergeo todo: " + e.getMessage());
            em.getTransaction().begin();
            em.merge(nuevoEgreso.getMedioDePago());
            if (nuevoEgreso.getComprobante() != null) {
                em.merge(nuevoEgreso.getComprobante());
            }
            for (int i = 0; i < nuevoEgreso.getPresupuestosPreliminares().size(); i++) {
                Presupuesto mergedEntity = em.merge(nuevoEgreso.getPresupuestosPreliminares().get(i));
                nuevoEgreso.getPresupuestosPreliminares().get(i).setId(mergedEntity.getId());
            }
            for (int i = 0; i < nuevoEgreso.getCategorias().size(); i++) {
                Categoria mergedEntity = em.merge(nuevoEgreso.getCategorias().get(i));
                nuevoEgreso.getCategorias().get(i).setId(mergedEntity.getId());
            }

            em.getTransaction().commit();

            em.getTransaction().begin();
            em.merge(nuevoEgreso);
            em.flush();
            em.getTransaction().commit();


        }

    }

    public OperacionEgreso get(int id) throws NoResultException {
        String query = "from OperacionEgreso where id = " + id;
        OperacionEgreso eg = null;
        try {
            eg = em.find(OperacionEgreso.class, id);

        } catch (Exception e) {
            eg = (OperacionEgreso) em.createQuery(query).getSingleResult();
        }
        return eg;
    }

    public void asociarIngreso(OperacionEgreso egreso, OperacionIngreso ingreso) {
        int orgID = repoOrg.findOrgID(egreso.getOrganizacion().getNombreFicticio());
        egreso.setIngreso(ingreso);
        egreso.getOrganizacion().setId(orgID);
        ingreso.getOperacionesEgreso().add(egreso);
        try {
            em.getTransaction().begin();
            em.merge(egreso);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("ERROR asociando egreso con ingreso: " + e.getMessage());
            throw (e);
        }
        /*em.beginTransaction();
        em.persist(ingreso);
        em.commit();*/
        //no es necesario creo, ingreso deberia traerte su lista sin necesidad de persistirlo
    }

    public void asociarCategorias(OperacionEgreso egreso, ArrayList<Categoria> categorias) {
        int orgID = repoOrg.findOrgID(egreso.getOrganizacion().getNombreFicticio());
        egreso.getOrganizacion().setId(orgID);

        List<Categoria> categoriasTotales = egreso.getCategorias();
        categoriasTotales.addAll(categorias);
        egreso.setCategorias(categoriasTotales);
        em.getTransaction().begin();

        try {
            for (Categoria c : categoriasTotales) {
                em.merge(c);
                em.merge(c.getCriterio());
            }
            em.merge(egreso);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.err.println("[egreso con categorias] ERROR merge todo, intentando persist: " + e.getMessage());
            em.getTransaction().rollback();

            try {
                em.getTransaction().begin();
                for (Categoria cat : categorias) {
                    em.persist(cat);
                    em.persist(cat.getCriterio());
                }
                em.merge(egreso);
                em.getTransaction().commit();

            } catch (Exception e1) {
                em.getTransaction().rollback();
                System.err.println("[egreso con categorias] ERROR merge: " + e1.getMessage());
                throw (e1);
            }
            System.err.println("merge OK.");

        }
    }
}
