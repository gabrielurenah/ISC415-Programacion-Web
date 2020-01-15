package edu.pucmm.Models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Recommendation implements Serializable {
    @EmbeddedId
    private RecommendationId recommendationId;
    private boolean isLike;

    public Recommendation(RecommendationId recommendationId, boolean isLike) {
        this.recommendationId = recommendationId;
        this.isLike = isLike;
    }

    public RecommendationId getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(RecommendationId recommendationId) {
        this.recommendationId = recommendationId;
    }

    public boolean getLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public Recommendation() { }
}
