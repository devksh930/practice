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
         INNER JOIN Feed f on f.feedCounter.id=fc.id
         AND f.id = :feedId
        """)
    Optional<FeedCounter> findByFeedId(@Param("feedId") final Long feedId);

    @Query("""
        UPDATE FeedCounter fc 
        SET fc.likeCount = fc.likeCount+1 
        WHERE fc.id = (SELECT f.id FROM Feed f WHERE f.id=:feedId)
        """)
    int updateLikeIncrease(@Param("feedId")final Long feedId);
}
