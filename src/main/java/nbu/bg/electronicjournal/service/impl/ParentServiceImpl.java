package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Student;
import nbu.bg.electronicjournal.repository.ParentRepository;
import nbu.bg.electronicjournal.service.ParentService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ParentServiceImpl implements ParentService {
    private ParentRepository parentRepository;

    @Override
    public Set<Student> getKids(Long parentId) {
        return parentRepository.findByIdWithKids(parentId)
                               .orElseThrow(() -> new EntityNotFoundException("Parent with this id doesn't exist"))
                               .getKids();
    }

}