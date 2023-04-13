package javaSample.sample.hibController;

import javaSample.sample.model.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ProductHibController {
    EntityManagerFactory emf = null;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private ShoppingCartHibController shoppingCartHibController = new ShoppingCartHibController(entityManagerFactory);

    public ProductHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createProduct(Product product) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(em.merge(product));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateProduct(Product product){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.flush();
            product = entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteProduct(int id){
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Product product = null;
            try{
                product = entityManager.getReference(Product.class, id);
            }catch(Exception e){
                e.printStackTrace();
            }
            entityManager.remove(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Product> getProductList(){
        return getProductList(true, -1, -1);
    }

    public List<Product> getProductList(boolean all, int maxRes, int firstRes) {

        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Product.class));
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

    public Product getProductById(int productId) {
        for(Product product: getProductList()){
            if(product.getId()==productId) return product;
        }
        return null;
    }

    public List<Product> getProductsByAuthorId(int authorId){
        List<Product> foundProducts = new ArrayList<>();
        for(Product product: getProductList()){
            if(product.getAuthor().getId()==authorId) foundProducts.add(product);
        }

        return foundProducts;
    }

    public List<Product> getAvailableProducts(int authorId){
        List<Product> foundProducts = new ArrayList<>();
        for(Product product: getProductList()){
            if(product.getAuthor().getId()!=authorId&&product.getOrder()==null&&product.getShoppingCart()==null)
                foundProducts.add(product);
        }

        return foundProducts;
    }
}
