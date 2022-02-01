package javaSample.sample.helpers.validator;

import javaSample.sample.hibController.ReviewHibController;
import javaSample.sample.model.Review;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ReviewValidator implements Validator{
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    static ReviewHibController reviewHibController = new ReviewHibController(entityManagerFactory);

    public static boolean validateRatingRange(int rating){
        if(rating > 0 && rating <= 10){
            return true;
        }
        return false;
    }
}
