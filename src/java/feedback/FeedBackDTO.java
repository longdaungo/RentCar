/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedback;

/**
 *
 * @author IT
 */
public class FeedBackDTO {
    private String feedbackID;
    private String carID;
    private String comment;
    private int rating;
    private String name;

    public FeedBackDTO(String feedbackID, String carID, String comment, int rating, String name) {
        this.feedbackID = feedbackID;
        this.carID = carID;
        this.comment = comment;
        this.rating = rating;
        this.name = name;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
