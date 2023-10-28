package org.top.hairsalonapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


//Сущность HairService - каталог услуг парикмахерской
@Data
@Entity
@Table(name="hair_service_t")
public class HairService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="hair_service_name_f", nullable = false)
    private String hairServiceName;

    @Column(name="short_description_f", nullable = false)
    private String shortDescription;

    @Column(name="description_f", length = 1000)
    private String description;

    @Column(name="price")
    private double price;

    //связь с заказами: много услуг - один заказ.

    @OneToMany(mappedBy = "hairService")
    private Set<OrderedServices> orderedServices;

}
