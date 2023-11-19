package com.bda.recu.repos;

import com.bda.recu.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
