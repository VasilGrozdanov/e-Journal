package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.dto.QualificationDto;
import nbu.bg.electronicjournal.model.dto.StudentEnrollDto;
import nbu.bg.electronicjournal.model.dto.SubjectDto;
import nbu.bg.electronicjournal.model.entity.*;
import nbu.bg.electronicjournal.repository.*;
import nbu.bg.electronicjournal.service.DirectorService;
import nbu.bg.electronicjournal.service.SubjectService;
import nbu.bg.electronicjournal.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class DirectorServiceImpl implements DirectorService {

    private StudentRepository studentRepository;
    private GradeRepository gradeRepository;
    private SubjectRepository subjectRepository;
    private SubjectService subjectService;
    private TeacherService teacherService;
    private SchoolRepository schoolRepository;
    private QualificationRepository qualificationRepository;
    private DirectorRepository directorRepository;

    @Override
    public boolean enrollStudent(StudentEnrollDto studentEnrollDto) {
        Student student = studentRepository.findById(studentEnrollDto.getStudentId()).orElseThrow(
                () -> new EntityNotFoundException("Student with this id doesn't exist"));
        Grade newGrade = gradeRepository.findById(studentEnrollDto.getGradeId()).orElseThrow(
                () -> new EntityNotFoundException("Grade with this id doesn't exist"));
        Grade currentGrade = student.getGrade();

        if (currentGrade != null) {
            currentGrade.getStudents().remove(student);
            gradeRepository.save(currentGrade);
        }
        student.setGrade(newGrade);
        newGrade.getStudents().add(student);

        studentRepository.save(student);
        gradeRepository.save(newGrade);
        return true;
    }

    @Override
    public boolean createSubject(SubjectDto newSubjectDto) {
        if (subjectRepository.existsBySignature(newSubjectDto.getSignature())) {
            throw new EntityExistsException("Subject with this signature already exists");
        }
        subjectRepository.save(
                Subject.builder().signature(newSubjectDto.getSignature()).name(newSubjectDto.getName()).build());
        return true;
    }

    @Override
    public boolean updateSubject(SubjectDto updatedSubject) {
        Subject existingSubject = subjectService.getSubject(updatedSubject.getSignature());
        ;
        existingSubject.setName(updatedSubject.getName());
        subjectRepository.save(existingSubject);
        return true;
    }

    @Override
    public boolean removeSubject(String subjectSignature) {
        Subject existingSubject = subjectService.getSubject(subjectSignature);
        ;
        subjectRepository.delete(existingSubject);
        return true;
    }

    @Override
    public boolean addQualification(QualificationDto newQualificationDto, Long schoolId) {
        Teacher existingTeacher = teacherService.getTeacherWithQualifications(newQualificationDto.getTeacherId());
        Subject existingSubject = subjectService.getSubject(newQualificationDto.getSubjectSignature());
        Qualification existingQualification = existingTeacher.getQualifications().stream()
                                                             .filter(qualification -> qualification.getSchool().getId()
                                                                                                   .equals(schoolId))
                                                             .findFirst()
                                                             .orElse(new Qualification(existingTeacher, new HashSet<>(),
                                                                     schoolRepository.findById(schoolId)
                                                                                     .orElseThrow()));
        existingQualification.getSubjects().add(existingSubject);
        qualificationRepository.save(existingQualification);
        return true;
    }

    @Override
    public boolean updateQualification(Long teacherId, String oldSubjectSignature, String newSubjectSignature,
            Long schoolId) {
        Teacher existingTeacher = teacherService.getTeacherWithQualifications(teacherId);
        Subject oldSubject = subjectService.getSubject(oldSubjectSignature);
        Subject newSubject = subjectService.getSubject(newSubjectSignature);
        Qualification existingQualification = existingTeacher.getQualifications().stream()
                                                             .filter(qualification -> qualification.getSchool().getId()
                                                                                                   .equals(schoolId))
                                                             .findFirst().orElseThrow();
        existingQualification.getSubjects().remove(oldSubject);
        existingQualification.getSubjects().add(newSubject);
        qualificationRepository.save(existingQualification);
        return true;
    }

    @Override
    public boolean removeQualification(Long teacherId, String subjectSignature, Long schoolId) {
        Teacher existingTeacher = teacherService.getTeacherWithQualifications(teacherId);
        Subject existingSubject = subjectService.getSubject(subjectSignature);
        Qualification existingQualification = existingTeacher.getQualifications().stream()
                                                             .filter(qualification -> qualification.getSchool().getId()
                                                                                                   .equals(schoolId))
                                                             .findFirst().orElseThrow();
        existingQualification.getSubjects().remove(existingSubject);
        qualificationRepository.save(existingQualification);
        if (existingQualification.getSubjects().isEmpty()) {
            existingTeacher.getQualifications().remove(existingQualification);
            qualificationRepository.delete(existingQualification);
        }
        return true;
    }

    @Override
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Director getDirector(Long id) {
        return directorRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException("Director with this id doesn't exist"));
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

}
