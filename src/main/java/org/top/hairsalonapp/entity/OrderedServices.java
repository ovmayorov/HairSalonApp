package org.top.hairsalonapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "ordered_services_t")
public class OrderedServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    //@OneToMany(mappedBy = "orderedServices")
    @ManyToOne
    @JoinColumn(name = "hair_service_id", nullable = false)
    private HairService hairService;
    //private Set<HairService> hairServices;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    //багофикс, переопределение  equals и hashCode чтобы вывести коллекцию услуг в заказе.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedServices that = (OrderedServices) o;
        return Objects.equals(id, that.id) && Objects.equals(hairService, that.hairService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hairService);
    }
}
