package nbu.bg.logisticscompany.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.logisticscompany.model.dto.SubjectDto;
import nbu.bg.logisticscompany.model.entity.Subject;
import nbu.bg.logisticscompany.repository.SubjectRepository;
import nbu.bg.logisticscompany.service.SubjectService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    @Override
    public SubjectDto getSubject(String signature) {
        Subject existingSubject = subjectRepository.findBySignature(signature).orElseThrow(
                () -> new EntityNotFoundException("Subject with this signature doesn't exist"));
        return new SubjectDto(existingSubject.getSignature(), existingSubject.getName());
    }

    @Override
    public boolean deleteSubject(String signature) {
        Subject existingSubject = subjectRepository.findBySignature(signature).orElseThrow(
                () -> new EntityNotFoundException("Subject with this signature doesn't exist"));
        subjectRepository.delete(existingSubject);
        return true;
    }

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }
    
}
