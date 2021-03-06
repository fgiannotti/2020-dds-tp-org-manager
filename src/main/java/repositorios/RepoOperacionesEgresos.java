package repositorios;

import db.EntityManagerHelper;
import entidades.Items.Articulo;
import entidades.Items.Item;
import entidades.MedioDePago.MedioDePago;
import entidades.Operaciones.*;
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
            System.err.println("persist fall??. rollbackeo mergeo todo: " + e.getMessage());

            try {
                em.getTransaction().rollback();
            }catch (Exception e2){
                System.err.println("excepcion rollbackeando: "+ e2.getMessage());
            }

            em.getTransaction().begin();

            MedioDePago mpMergeado = em.merge(nuevoEgreso.getMedioDePago());
            nuevoEgreso.getMedioDePago().setId(mpMergeado.getId());

            if (nuevoEgreso.getComprobante() != null) {
                Comprobante mergedEntity = em.merge(nuevoEgreso.getComprobante());
                //---DEEP COPY COMPROBANTE ITEMS-----
                ArrayList<Item> itemsClonados = new ArrayList<>();
                for (Item it: mergedEntity.getItems()){
                    ArrayList<Articulo> artClonados = new ArrayList<>();
                    for (Articulo art: it.getArticulos()){
                        Articulo clon2 = new Articulo(art.getNombre(),art.getPrecioTotal(),art.getDescripcion());
                        artClonados.add(clon2);
                    }
                    Item clon = new Item(it.getDescripcion(),it.getNombre(),artClonados);
                    itemsClonados.add(clon);
                }

                mergedEntity.setItems(itemsClonados);
                nuevoEgreso.setComprobante(mergedEntity);
            }

            for (int i = 0; i < nuevoEgreso.getPresupuestosPreliminares().size(); i++) {
                Presupuesto mergedEntity = em.merge(nuevoEgreso.getPresupuestosPreliminares().get(i));
                nuevoEgreso.getPresupuestosPreliminares().get(i).setId(mergedEntity.getId());
            }
            for (int i = 0; i < nuevoEgreso.getCategorias().size(); i++) {
                if (nuevoEgreso.getCategorias().get(i).getId()<0 || nuevoEgreso.getCategorias().get(i).getId()>9000){nuevoEgreso.getCategorias().get(i).setId(0);};
                Categoria mergedEntity = em.merge(nuevoEgreso.getCategorias().get(i));
                nuevoEgreso.getCategorias().remove(i);
                nuevoEgreso.getCategorias().add(i,  mergedEntity);
            }
            //em.getTransaction().commit();
            //em.getTransaction().begin();
            em.merge(nuevoEgreso);
            em.getTransaction().commit();


        }

    }

    public OperacionEgreso get(int id) throws NoResultException {
        OperacionEgreso eg = null;
        try {
            eg = em.find(OperacionEgreso.class, id);

        } catch (Exception e) {
            String query = "from OperacionEgreso where id = " + id;
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

    public void asociarCategorias(OperacionEgreso egreso, List<Categoria> categorias) {
        int orgID = repoOrg.findOrgID(egreso.getOrganizacion().getNombreFicticio());
        egreso.getOrganizacion().setId(orgID);

        List<Categoria> categoriasTotales = egreso.getCategorias();
        categoriasTotales.addAll(categorias);
        egreso.setCategorias(categoriasTotales);
        agregar(egreso);
    }
}
