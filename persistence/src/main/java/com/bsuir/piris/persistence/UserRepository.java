package com.bsuir.piris.persistence;

import com.bsuir.piris.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, Long userId);

    boolean existsByPassportId(String passportId);

    boolean existsByPassportNumber(String passportNumber);

    boolean existsByPassportIdAndIdIsNot(String passportId, Long userId);

    boolean existsByPassportNumberAndIdIsNot(String passportNumber, Long userId);
}