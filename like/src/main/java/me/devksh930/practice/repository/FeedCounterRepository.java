package me.devksh930.practice.repository;

import java.util.Optional;
import me.devksh930.practice.domain.FeedCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedCounterRepository extends JpaRepository<FeedCounter, Long> {

    @Query("""
        SELECT fc 
        FROM FeedCounter fc
         INNER JOIN Feed f on f.id=fc.id
         AND f.id = :feedId
        """)
    Optional<FeedCounter> findByFeedId(@Param("feedId") final Long feedId);
}
