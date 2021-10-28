package javaSample.sample.hibController;

import javaSample.sample.model.Order;
import javaSample.sample.model.ShoppingCart;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class OrderHibController {
    private EntityManagerFactory emf = null;

    public OrderHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() { return emf.createEntityManager(); }

    public void createOrder(Order order){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(order));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Order> getOrdersList(){
        return getOrdersList(true, -1, -1);
    }

    public List<Order> getOrdersList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Order.class));
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

    public void updateOrder(Order order){
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            order = entityManager.merge(order);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteOrder(int id){
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Order order = null;
            try {
                order = entityManager.getReference(Order.class, id);
            } catch (Exception e){
                e.printStackTrace();
            }
            entityManager.remove(order);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Order> getOrdersByUserId(int userId){
        List<Order> foundOrders = new ArrayList<>();
        for(Order order: getOrdersList()){
            if(order.getBuyer().getId()==userId){
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }
}
