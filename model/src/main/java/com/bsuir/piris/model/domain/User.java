package com.bsuir.piris.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "fathers_name")
    private String fathersName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "sex")
    private Character sex;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_id")
    private String passportId;

    @Column(name = "passport_issued")
    private String passportIssued;

    @Column(name = "passport_issued_date")
    private Date passportIssuedDate;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(name = "address")
    private String address;

    @Column(name = "home_number")
    private String homeNumber;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address_of_residence")
    private String addressOfResidence;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "family_status_id", referencedColumnName = "id")
    private FamilyStatus familyStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nationality_id", referencedColumnName = "id")
    private Nationality nationality;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disability_id", referencedColumnName = "id")
    private Disability disability;

    @Column(name = "is_pensioner")
    private Boolean isPensioner;

    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;
}