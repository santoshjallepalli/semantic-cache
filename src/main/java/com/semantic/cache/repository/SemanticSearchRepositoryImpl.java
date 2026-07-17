package com.semantic.cache.repository;

import com.pgvector.PGvector;
import com.semantic.cache.entity.SemanticChatCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SemanticSearchRepositoryImpl implements SemanticSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SemanticChatCache> findBestMatch(float[] embedding, double threshold) {

        String sql = """
                SELECT *
                FROM semantic_cache.semantic_chat_cache_new
                WHERE embedding <=> CAST(? AS semantic_cache.vector) <= (1 - ?)
                ORDER BY embedding <=> CAST(? AS semantic_cache.vector)
                LIMIT 1
                """;

        List<SemanticChatCache> results = entityManager
                .createNativeQuery(sql, SemanticChatCache.class)
                .setParameter(1, Arrays.toString(embedding).replace(" ", ""))
                .setParameter(2, threshold)
                .setParameter(3, Arrays.toString(embedding).replace(" ", ""))
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.getFirst());
    }
}
