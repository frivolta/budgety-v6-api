package com.budgety.api.exceptions;

public class NotAllowedException extends IllegalAccessException{
    private String resourceName;
    private String userId;

    public NotAllowedException(String resourceName, String userId) {
        super(String.format("You are not allowed to edit %s with ", resourceName, userId));
        this.resourceName = resourceName;
        this.userId = userId;
    }

}
