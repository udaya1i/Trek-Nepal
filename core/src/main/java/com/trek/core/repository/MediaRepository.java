package com.trek.core.repository;

import com.trek.core.entity.Media;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends AbstractRepository<Media, Long> {
    public Optional<Media> findByImagePublicUrl(String url);

    public Optional<Media> findByImageIdentifier(String url);

    public List<Media> findAllByImagePublicUrlIn(List<String> url);

    public List<Media> findAllByImageIdentifierIn(List<String> key);

    public List<Media> findAllByImageIdentifier(String url);

}
