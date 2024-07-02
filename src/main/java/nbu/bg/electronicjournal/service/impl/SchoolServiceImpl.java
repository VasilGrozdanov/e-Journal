package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.School;
import nbu.bg.electronicjournal.repository.SchoolRepository;
import nbu.bg.electronicjournal.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;

    @Override
    public School getSchool(Long id) {
        return schoolRepository.findById(id)
                               .orElseThrow(() -> new EntityNotFoundException("School with this id doesn't exist"));
    }

    @Override
    public List<School> getSchools() {
        return schoolRepository.findAll();
    }
}
