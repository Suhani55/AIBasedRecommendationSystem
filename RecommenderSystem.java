import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class RecommenderSystem {
    public static void main(String[] args) {
        try {
            // Load user rating data
            DataModel model = new FileDataModel(new File("ratings.csv"));

            // Define similarity metric
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define user neighborhood
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build the recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend top 3 items for user with ID 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // Display results
            System.out.println("Recommendations for user 1:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.printf("Item: %d, Score: %.2f%n", recommendation.getItemID(), recommendation.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
