package com.bsuir.piris.service.impl;

import com.bsuir.piris.model.domain.ImaginaryTime;
import com.bsuir.piris.persistence.ImaginaryTimeRepository;
import com.bsuir.piris.service.ImaginaryTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImaginaryTimeServiceImpl implements ImaginaryTimeService {
    private static final Integer FIRST_DAY_OF_MONTH = 1;
    private static final Integer CREATED_TIME_ORDER = 0;
    private final ImaginaryTimeRepository imaginaryTimeRepository;

    @Override
    public ImaginaryTime findLastDate() {
        List<ImaginaryTime> all = imaginaryTimeRepository.findAll();
        if (CollectionUtils.isEmpty(all)) {
            ImaginaryTime time = new ImaginaryTime();
            time.setCurrentDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
                    FIRST_DAY_OF_MONTH));
            return imaginaryTimeRepository.save(time);
        }
        return all.get(CREATED_TIME_ORDER);
    }
}
