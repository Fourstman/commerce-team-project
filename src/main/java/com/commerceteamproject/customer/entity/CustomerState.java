package com.commerceteamproject.customer.entity;

public enum CustomerState {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지");

    private final String description;

    CustomerState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public static CustomerState fromDescription(String description) {
        for (CustomerState state : values()) {
            if (state.description.equals(description)) {
                return state;
            }
        }
        throw new IllegalStateException("잘못된 상태값입니다.");
    }
}
