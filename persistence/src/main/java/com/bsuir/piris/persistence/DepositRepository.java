package com.bsuir.piris.persistence;

import com.bsuir.piris.model.domain.Deposit;
import com.bsuir.piris.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Page<Deposit> findAllByIsOpenIsTrueOrderByStartDateDesc(Pageable pageable);

    Boolean existsByUserAndIsOpenIsTrue(User user);
}
