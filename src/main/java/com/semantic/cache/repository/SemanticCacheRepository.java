package com.semantic.cache.repository;

import com.semantic.cache.entity.SemanticChatCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemanticCacheRepository extends JpaRepository<SemanticChatCache, Long> {
}
