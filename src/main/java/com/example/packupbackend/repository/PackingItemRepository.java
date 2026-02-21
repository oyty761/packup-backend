package com.example.packupbackend.repository;

import com.example.packupbackend.entity.PackingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PackingItemRepository extends JpaRepository<PackingItem, Long> {
    List<PackingItem> findByTripId(Long tripId);
    List<PackingItem> findByTripIdAndIsPacked(Long tripId, Boolean isPacked);
    List<PackingItem> findByTripIdAndCategory(Long tripId, String category);
    long countByTripIdAndIsPacked(Long tripId, Boolean isPacked);
}
