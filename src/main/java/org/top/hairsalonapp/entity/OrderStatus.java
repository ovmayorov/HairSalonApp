package org.top.hairsalonapp.entity;

public enum OrderStatus {
    NEW("Новый"),
    DONE("Оплачен"),
    CANCELED("Отменен");

    public final String statusOfOrder;

    private OrderStatus(String statusOfOrder){
        this.statusOfOrder = statusOfOrder;
    }
}
