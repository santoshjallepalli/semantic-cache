package com.semantic.cache.repository;

import com.semantic.cache.entity.SemanticChatCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
                FROM semantic_cache
                ORDER BY embedding <=> CAST(:embedding AS vector)
                LIMIT 1
                """;

        List<SemanticChatCache> results = entityManager
                .createNativeQuery(sql, SemanticChatCache.class)
                .setParameter("embedding", toVector(embedding)).getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.getFirst());
    }

    private String toVector(float[] vector) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < vector.length; i++) {
            sb.append(vector[i]);
            if (i < vector.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
