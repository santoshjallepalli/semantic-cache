package com.semantic.cache.repository;

import com.semantic.cache.entity.SemanticChatCache;

import java.util.Optional;

public interface SemanticSearchRepository {

    Optional<SemanticChatCache> findBestMatch(float[] embedding,
                                              double threshold);

}
