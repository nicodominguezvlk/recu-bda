package com.bda.recu.repos;

import com.bda.recu.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
}
