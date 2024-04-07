package com.pingme.ping.daos;

import com.pingme.ping.daos.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  List<Category> findByName(String name);
}
