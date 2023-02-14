package com.bsuir.piris.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "imaginary_time")
@NoArgsConstructor
public class ImaginaryTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_date_time")
    private LocalDate currentDate;
}
