package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Teacher;
import nbu.bg.electronicjournal.repository.TeacherRepository;
import nbu.bg.electronicjournal.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;

    @Override
    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Teacher with this id doesn't exist"));
    }

    @Override
    public Teacher getTeacherWithQualifications(Long id) {
        return teacherRepository.findByIdWithQualifications(id)
                                .orElseThrow(() -> new EntityNotFoundException("Teacher with this id doesn't exist"));
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }
}
