package com.bsuir.piris.persistence;

import com.bsuir.piris.model.domain.Disability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisabilityRepository extends JpaRepository<Disability, Long> {
}
