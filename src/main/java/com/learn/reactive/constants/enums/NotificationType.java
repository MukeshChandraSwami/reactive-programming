package com.learn.reactive.constants.enums;

public enum NotificationType {

    MSG("Massage"), EMAIL("E-Mail"), PUSH("Push");

    private final String notificationType;

    NotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
