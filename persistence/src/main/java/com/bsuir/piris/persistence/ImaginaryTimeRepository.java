package com.bsuir.piris.persistence;

import com.bsuir.piris.model.domain.ImaginaryTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImaginaryTimeRepository extends JpaRepository<ImaginaryTime, Long> {
}
