package javaSample.sample.hibController;

import javaSample.sample.model.Address;
import javaSample.sample.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AddressHibController {
    EntityManagerFactory emf = null;

    public AddressHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createAddress(Address address) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(address));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateAddress(Address address){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            address = entityManager.merge(address);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteAddress(int id){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Address address = null;
            try{
                address = entityManager.getReference(Address.class, id);
            }catch(Exception e){
                e.printStackTrace();
            }
            entityManager.remove(address);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Address> getAddressList(){
        return getAddressList(true, -1, -1);
    }

    public List<Address> getAddressList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Address.class));
            Query query = entityManager.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return null;
    }

    public Address getAddressByUserId(int userId) {
        for(Address address: getAddressList()){
            if(address.getUser().getId()==userId) return address;
        }
        return null;
    }
}
