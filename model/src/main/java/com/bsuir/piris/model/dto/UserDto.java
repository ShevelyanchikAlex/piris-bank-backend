package com.bsuir.piris.model.dto;

import com.bsuir.piris.model.domain.City;
import com.bsuir.piris.model.domain.Disability;
import com.bsuir.piris.model.domain.FamilyStatus;
import com.bsuir.piris.model.domain.Nationality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String fathersName;
    private Date birthday;
    private Character sex;
    private String passportSeries;
    private String passportNumber;
    private String passportId;
    private String passportIssued;
    private Date passportIssuedDate;
    private String placeOfBirth;
    private City city;
    private String address;
    private String homeNumber;
    private String mobileNumber;
    private String email;
    private String addressOfResidence;
    private FamilyStatus familyStatus;
    private Nationality nationality;
    private Disability disability;
    private Boolean isPensioner;
    private BigDecimal monthlyIncome;
}
