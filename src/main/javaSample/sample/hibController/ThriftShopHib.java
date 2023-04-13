package javaSample.sample.hibController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ThriftShopHib {

    EntityManagerFactory entityManagerFactory = null;

    public ThriftShopHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
