package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Ratio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatioRepository extends CrudRepository<Ratio, Long> {
    List<Ratio> findTop25ByTorrentIdOrderByIdDesc(Integer torrentId);

    long deleteByTorrentId(Integer torrentId);
}
