package com.trek.core.admin.service;

import com.trek.core.constants.SluggableType;
import com.trek.core.entity.SlugHistory;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.SlugHistoryRepository;
import com.trek.core.util.SlugUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

/**
 * @author udaya
 */

@Service
@AllArgsConstructor
public class SlugAssignmentService {

    private final SlugHistoryRepository slugHistoryRepository;

    public String ensureUniqueSlug(String base, Predicate<String> existsBySlug) {
        String baseSlug = SlugUtil.slugify(base);
        if (baseSlug.isBlank()) {
            baseSlug = "item";
        }
        String candidate = baseSlug;
        int suffix = 2;
        while (existsBySlug.test(candidate)) {
            candidate = baseSlug + "-" + suffix;
            suffix++;
        }
        return candidate;
    }

    @Transactional
    public void archiveOldSlug(SluggableType ownerType, Long ownerId, String oldSlug) {
        if (oldSlug == null || oldSlug.isBlank()) {
            return;
        }
        SlugHistory history = new SlugHistory();
        history.setOwnerType(ownerType);
        history.setOwnerId(ownerId);
        history.setOldSlug(oldSlug);
        slugHistoryRepository.save(history);
    }

    /**
     * Resolves the slug to persist on an update: honors an explicit admin-supplied slug
     * (archiving the previous one for 301 redirects), or self-heals a legacy null slug,
     * or leaves the existing slug untouched.
     */
    @Transactional
    public String resolveSlugOnUpdate(SluggableType ownerType, Long ownerId, String currentSlug,
                                       String incomingSlug, String fallbackBase, Predicate<String> existsBySlug) {
        if (incomingSlug != null && !incomingSlug.isBlank()) {
            String candidate = SlugUtil.slugify(incomingSlug);
            if (candidate.equals(currentSlug)) {
                return currentSlug;
            }
            if (existsBySlug.test(candidate)) {
                throw new RequestValidationException("Slug '" + candidate + "' is already in use");
            }
            archiveOldSlug(ownerType, ownerId, currentSlug);
            return candidate;
        }
        if (currentSlug == null || currentSlug.isBlank()) {
            return ensureUniqueSlug(fallbackBase, existsBySlug);
        }
        return currentSlug;
    }
}
