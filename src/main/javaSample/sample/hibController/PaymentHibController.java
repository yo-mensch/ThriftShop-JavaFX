package javaSample.sample.hibController;

import javaSample.sample.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PaymentHibController {
    private EntityManagerFactory emf = null;

    public PaymentHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() { return emf.createEntityManager(); }

    public void createPayment(Payment payment){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(payment));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updatePayment(Payment payment){
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            payment = entityManager.merge(payment);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deletePayment(int id){
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Payment payment = null;
            try {
                payment = entityManager.getReference(Payment.class, id);
            } catch (Exception e){
                e.printStackTrace();
            }
            entityManager.remove(payment);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
