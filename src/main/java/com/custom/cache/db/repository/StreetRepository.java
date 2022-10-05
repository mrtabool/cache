package com.custom.cache.db.repository;

import java.time.LocalDateTime;
import java.util.UUID;
import com.custom.cache.db.entity.Street;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@EnableCaching
public interface StreetRepository extends JpaRepository<Street, Long> {

    Street findByName(String name);

    @Transactional
    @Modifying
    @Query("update Street s set s.lastRequest = :lastRequest WHERE s.id = :streetId")
    void setLastRequest(@Param("streetId") Long id, @Param("lastRequest") LocalDateTime lastRequest);
}
