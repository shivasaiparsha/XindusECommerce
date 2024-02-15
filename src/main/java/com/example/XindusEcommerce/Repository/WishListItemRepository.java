package com.example.XindusEcommerce.Repository;

import com.example.XindusEcommerce.Models.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface WishListItemRepository extends JpaRepository<WishListItem, Integer> {
}
