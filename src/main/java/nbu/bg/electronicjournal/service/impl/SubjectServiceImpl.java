package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.dto.SubjectDto;
import nbu.bg.electronicjournal.model.entity.Subject;
import nbu.bg.electronicjournal.repository.SubjectRepository;
import nbu.bg.electronicjournal.service.SubjectService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    @Override
    public SubjectDto getSubjectDto(String signature) {
        Subject existingSubject = getSubject(signature);
        return new SubjectDto(existingSubject.getSignature(), existingSubject.getName());
    }

    @Override
    public Subject getSubject(String signature) {
        return subjectRepository.findBySignature(signature).orElseThrow(
                () -> new EntityNotFoundException("Subject with this signature doesn't exist"));
    }

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public boolean deleteSubject(String signature) {
        Subject existingSubject = subjectRepository.findBySignature(signature).orElseThrow(
                () -> new EntityNotFoundException("Subject with this signature doesn't exist"));
        subjectRepository.delete(existingSubject);
        return true;
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

}
