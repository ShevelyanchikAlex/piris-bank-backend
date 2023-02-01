package com.bsuir.piris.persistence;

import com.bsuir.piris.model.domain.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long> {
}
