package com.example.XindusEcommerce.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name="item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Column( nullable = false)
    private  String itemName;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user ;

    public WishListItem(Integer itemId, String itemName)
    {
        this.itemId=itemId;
        this.itemName=itemName;
    }

}