package org.top.hairsalonapp.service.order;

import org.springframework.stereotype.Service;

@Service
public interface OrderStatusService {
    //Установить статус заказа в "Отменен"-CANCELED-2
    void statusToCanceled(Integer id);

    //Установить статус заказа в "Оплачено"-DONE
    void statusToDone(Integer id);


}
