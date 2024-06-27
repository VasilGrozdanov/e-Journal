package nbu.bg.electronicjournal.service;

import nbu.bg.electronicjournal.model.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TeacherService {
    Teacher getTeacher(Long id);

    Teacher getTeacherWithQualifications(Long id);

    List<Teacher> getAll();

    boolean addMark(Long teacherId, Long studentId, Long subjectId, int mark);
    boolean deleteMark(Long evaluationId);
    boolean editMark(Long evaluationId, int newMark);
    boolean addAbsence(Long teacherId, Long studentId, Long subjectId, AbsenceType type, LocalDateTime date);
    boolean editAbsence(Long absenceId, AbsenceType newType, LocalDateTime newDate);
    boolean removeAbsence(Long absenceId);
    boolean addParentToStudent(Long studentId, Long parentId);
    boolean removeParentFromStudent(Long studentId, Long parentId);
    boolean editParentOfStudent(Long studentId, Long newParentId);

}
