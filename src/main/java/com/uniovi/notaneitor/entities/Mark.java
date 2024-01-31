package com.uniovi.notaneitor.entities;

public class Mark {

    private Long id;
    private String description;
    private Double score;
    public Mark() {
    }

    public Mark(Long id, String description, Double score) {
        this.id = id;
        this.description = description;
        this.score = score;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Mark{" + "id=" + id + ", description='" + description + '\'' + ", score=" + score + '}';
    }

}
