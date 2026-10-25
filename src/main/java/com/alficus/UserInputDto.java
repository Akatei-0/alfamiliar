package com.alficus;

public class UserInputDto {
    private String step;
    private String choice;
    private String businessRegistered;
    private String entityType;
    private String goal;
    private String creditHistory;

    // Getters and Setters
    public String getStep() { return step; }
    public void setStep(String step) { this.step = step; }

    public String getChoice() { return choice; }
    public void setChoice(String choice) { this.choice = choice; }

    public String getBusinessRegistered() { return businessRegistered; }
    public void setBusinessRegistered(String businessRegistered) { this.businessRegistered = businessRegistered; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public String getCreditHistory() { return creditHistory; }
    public void setCreditHistory(String creditHistory) { this.creditHistory = creditHistory; }
}