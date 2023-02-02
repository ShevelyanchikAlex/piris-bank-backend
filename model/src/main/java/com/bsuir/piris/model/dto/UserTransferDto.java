package com.bsuir.piris.model.dto;

import com.bsuir.piris.model.domain.City;
import com.bsuir.piris.model.domain.Disability;
import com.bsuir.piris.model.domain.FamilyStatus;
import com.bsuir.piris.model.domain.Nationality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransferDto {
    private City city;
    private FamilyStatus familyStatus;
    private Nationality nationality;
    private Disability disability;
}