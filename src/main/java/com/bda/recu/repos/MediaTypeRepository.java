package com.bda.recu.repos;

import com.bda.recu.models.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Integer> {
}
