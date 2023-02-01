package com.bsuir.piris.persistence;

import com.bsuir.piris.model.domain.FamilyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyStatusRepository extends JpaRepository<FamilyStatus, Long> {
}
