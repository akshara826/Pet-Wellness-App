package com.petcare.petwellness.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.petcare.petwellness.Domain.Entity.Product;
import com.petcare.petwellness.Enums.ProductStatus;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByStatusIn(List<ProductStatus> statuses, Pageable pageable);
}
