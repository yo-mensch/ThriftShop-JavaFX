package javaSample.sample.hibController;

import javaSample.sample.model.ShoppingCart;
import javaSample.sample.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ShoppingCartHibController {
    private EntityManagerFactory emf = null;

    public ShoppingCartHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createShoppingCart(ShoppingCart shoppingCart) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(shoppingCart));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ShoppingCart> getShoppingCartsList(){
        return getShoppingCartsList(true, -1, -1);
    }

    public List<ShoppingCart> getShoppingCartsList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(ShoppingCart.class));
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

    public ShoppingCart getCartByUserId(int userId){
        for(ShoppingCart shoppingCart: getShoppingCartsList()){
            if(shoppingCart.getOwner().getId()==userId) return shoppingCart;
        }
        return null;
    }

    public void updateShoppingCart(ShoppingCart shoppingCart){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            shoppingCart = entityManager.merge(shoppingCart);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
