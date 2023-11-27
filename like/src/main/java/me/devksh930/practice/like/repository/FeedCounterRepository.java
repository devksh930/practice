package me.devksh930.practice.like.repository;

import java.util.Optional;
import me.devksh930.practice.like.domain.FeedCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FeedCounterRepository extends JpaRepository<FeedCounter, Long> {

    @Query("""
        SELECT fc 
        FROM FeedCounter fc
         INNER JOIN Feed f on f.feedCounter.id=fc.id
         AND f.id = :feedId
        """)
    Optional<FeedCounter> findByFeedId(@Param("feedId") final Long feedId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE FeedCounter fc 
        SET fc.likeCount = fc.likeCount+1 
        WHERE fc.id = (SELECT f.feedCounter.id FROM Feed f WHERE f.id=:feedId)
        """)
    int updateLikeIncrease(@Param("feedId")final Long feedId);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE FeedCounter fc
        SET fc.likeCount = 0L
        WHERE fc.id = :id
        """)
    void resetCount(@Param("id")final Long id);
}
