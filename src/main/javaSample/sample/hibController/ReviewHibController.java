package javaSample.sample.hibController;

import javaSample.sample.model.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ReviewHibController {
    EntityManagerFactory emf = null;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);

    public ReviewHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createReview(Review review) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(review));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateReview(Review review){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            review = entityManager.merge(review);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteReview(int id){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Review review = null;
            try{
                review = entityManager.getReference(Review.class, id);
            }catch(Exception e){
                e.printStackTrace();
            }
            entityManager.remove(review);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Review> getReviewList(){
        return getReviewList(true, -1, -1);
    }

    public List<Review> getReviewList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Review.class));
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

    public List<Review> getReviewsByProductId(int productID){
        List<Review> reviews = new ArrayList<>();
        for(Review review: getReviewList()){
            if(review.getProduct().getId() == productID){
                reviews.add(review);
            }
        }
        return reviews;
    }
}
