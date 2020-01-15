package edu.pucmm.utils;

import edu.pucmm.Models.*;
import edu.pucmm.services.Recommendations;
import edu.pucmm.services.Tags;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Utils {
    public static void likeDislike(Boolean likeDislike, Article article, User user) {
        Recommendation recommendation = Recommendations.getInstance().find(new RecommendationId(article, user));
        RecommendationId recommendationId = new RecommendationId(article, user);
        if (recommendation == null) {
            recommendation = new Recommendation(recommendationId, likeDislike);
            Recommendations.getInstance().create(recommendation);
        } else if (recommendation.getLike() == !likeDislike) {
            recommendation = new Recommendation(recommendationId, likeDislike);
            Recommendations.getInstance().update(recommendation);
        } else Recommendations.getInstance().delete(recommendationId);
    }

    public static Set<Tag> arrayToTagsSet(String[] tags) {
        Set<Tag> tagList = new HashSet<>();
        Tag newTag;
        for (String tag : tags) {
            if (!tag.equalsIgnoreCase("")) {
                Tag myTag = Tags.getInstance().findByName(tag);
                if (myTag != null) {
                    tagList.add(myTag);
                } else {
                    newTag = new Tag(UUID.randomUUID().toString(), tag);
                    Tags.getInstance().create(newTag);
                    tagList.add(newTag);
                }
            }
        }
        return tagList;
    }
}
