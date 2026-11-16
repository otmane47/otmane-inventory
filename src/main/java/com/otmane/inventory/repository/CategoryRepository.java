package com.otmane.inventory.repository;
import com.otmane.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, Long> {
}