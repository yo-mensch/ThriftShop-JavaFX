package javaSample.sample.hibController;

import javaSample.sample.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserHibController {
    EntityManagerFactory emf = null;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);

    public UserHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createUser(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(user));
            em.getTransaction().commit();
            shoppingCartHibController.createShoppingCart(user.getShoppingCart());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateUser(User user){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            user = entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteUser(int id){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            User user = null;
            try{
                user = entityManager.getReference(User.class, id);
                user.getProductsForSale().clear();
                user.getOrders().clear();
                //user.getShoppingCart()
                //user.getAddress().getId()
            }catch(Exception e){
                e.printStackTrace();
            }
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<User> getUserList(){
        return getUserList(true, -1, -1);
    }

    public List<User> getUserList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(User.class));
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

    public User getUserByUsername(String username) {
        for(User user: getUserList()){
            if(user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for(User user: getUserList()){
            if(user.getEmail().equals(email)) return user;
        }
        return null;
    }
}
