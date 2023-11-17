package me.devksh930.practice.repository;

public interface LikeRepository extends org.springframework.data.jpa.repository.JpaRepository<me.devksh930.practice.domain.Like, java.lang.Long> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<me.devksh930.practice.domain.Like> {
}