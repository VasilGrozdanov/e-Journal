package nbu.bg.electronicjournal.service.impl;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.dto.StudentDto;
import nbu.bg.electronicjournal.model.entity.*;
import nbu.bg.electronicjournal.repository.*;
import nbu.bg.electronicjournal.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;
    private EvaluatesRepository evaluatesRepository;
    private SubjectRepository subjectRepository;
    private AbsenceRepository absenceRepository;
    private ParentRepository parentRepository;
    private ProgramRepository programRepository;


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

    public boolean addMark(Long teacherId, Long studentId, Long subjectId, int mark) {
        Teacher teacher = getTeacher(teacherId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with this id doesn't exist"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject with this id doesn't exist"));

        Evaluates evaluation = new Evaluates();
        evaluation.setTeacher(teacher);
        evaluation.setStudent(student);
        evaluation.setSubject(subject);
        evaluation.setMark(mark);
        evaluation.setSystemDate(LocalDateTime.now());

        evaluatesRepository.save(evaluation);
        return true;
    }

    public boolean deleteMark(Long evaluationId) {
        Evaluates evaluation = evaluatesRepository.findById(evaluationId)
                .orElseThrow(() -> new EntityNotFoundException("Evaluation with this id doesn't exist"));
        evaluatesRepository.delete(evaluation);
        return true;
    }

    public boolean editMark(Long evaluationId, int newMark) {
        Evaluates evaluation = evaluatesRepository.findById(evaluationId)
                .orElseThrow(() -> new EntityNotFoundException("Evaluation with this id doesn't exist"));
        evaluation.setMark(newMark);
        evaluatesRepository.save(evaluation);
        return true;
    }

    public boolean addAbsence(Long teacherId, Long studentId, Long subjectId, AbsenceType type, LocalDateTime date) {
        Teacher teacher = getTeacher(teacherId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with this id doesn't exist"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject with this id doesn't exist"));

        Absence absence = new Absence();
        absence.setTeacher(teacher);
        absence.setStudent(student);
        absence.setSubject(subject);
        absence.setType(type);
        absence.setEntryDate(date);

        absenceRepository.save(absence);
        return true;
    }

    public boolean editAbsence(Long absenceId, AbsenceType newType, LocalDateTime newDate) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new EntityNotFoundException("Absence with this id doesn't exist"));
        absence.setType(newType);
        absence.setEntryDate(newDate);
        absenceRepository.save(absence);
        return true;
    }

    public boolean removeAbsence(Long absenceId) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new EntityNotFoundException("Absence with this id doesn't exist"));
        absenceRepository.delete(absence);
        return true;
    }

    public boolean addParentToStudent(Long studentId, Long parentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with this id doesn't exist"));
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent with this id doesn't exist"));

        parent.getKids().add(student);
        parentRepository.save(parent);  // Save parent to update both sides of the relationship
        return true;
    }

    public boolean removeParentFromStudent(Long studentId, Long parentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with this id doesn't exist"));
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent with this id doesn't exist"));

        parent.getKids().remove(student);
        parentRepository.save(parent);  // Save parent to update both sides of the relationship
        return true;
    }

    public boolean editParentOfStudent(Long studentId, Long newParentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with this id doesn't exist"));
        Parent newParent = parentRepository.findById(newParentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent with this id doesn't exist"));

        // Find all parents that have this student and remove the student from their kids set
        for (Parent parent : parentRepository.findAll()) {
            if (parent.getKids().remove(student)) {
                parentRepository.save(parent);
            }
        }

        // Add the student to the new parent
        newParent.getKids().add(student);
        parentRepository.save(newParent);

        return true;
    }

    public List<StudentDto> getMyStudents(Long teacherId){
        List<Student> studentList = new ArrayList<>();
        List<Program> programs = programRepository.findAll();

        programs = programs.stream()
                .filter(parent -> parent.getSubjectsTeached().stream()
                        .anyMatch(child -> teacherId.equals(child.getTeacher().getId())))
                .collect(Collectors.toList());
        if(programs !=null && !programs.isEmpty()){
            programs.stream().forEach(program -> {
                studentList.addAll(program.getGrade().getStudents());
            });
        }

        List<StudentDto> studentListDto = new ArrayList<>();

        if(studentList !=null && !studentList.isEmpty()){
            studentListDto = studentList.stream()
                    .map(student -> {
                                List<Parent> parents =  parentRepository.findAll().stream().filter(parent ->
                                        parent.getKids().contains(student)).collect(Collectors.toList());

                                return new StudentDto(student.getGrade().getLetter(),student.getId(), student.getNumber(), student.getFullName(),
                                        parents !=null && !parents.isEmpty() ?parents.get(0).getFullName():null);
                            }
                    )
                    .collect(Collectors.toList());
        }

        return studentListDto;
    }


}
