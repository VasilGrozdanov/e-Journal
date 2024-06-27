package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.model.entity.Teacher;
import nbu.bg.electronicjournal.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacher(id));
    }

    @GetMapping("/qualifications/{id}")
    public ResponseEntity<Teacher> getTeacherWithQualifications(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherWithQualifications(id));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAll());
    }

    @PostMapping("/add-mark")
    public ResponseEntity<Boolean> addMark(@RequestParam Long teacherId, @RequestParam Long studentId, @RequestParam Long subjectId, @RequestParam int mark) {
        return ResponseEntity.ok(teacherService.addMark(teacherId, studentId, subjectId, mark));
    }

    @DeleteMapping("/delete-mark/{evaluationId}")
    public ResponseEntity<Boolean> deleteMark(@PathVariable Long evaluationId) {
        return ResponseEntity.ok(teacherService.deleteMark(evaluationId));
    }
    @PutMapping("/edit-mark")
    public ResponseEntity<Boolean> editMark(@RequestParam Long evaluationId, @RequestParam int newMark) {
        return ResponseEntity.ok(teacherService.editMark(evaluationId, newMark));
    }

    @PostMapping("/add-parent-to-student")
    public ResponseEntity<Boolean> addParentToStudent(Authentication authentication, @RequestParam Long studentId, @RequestParam Long parentId) {


        return ResponseEntity.ok(teacherService.addParentToStudent(studentId, parentId));
    }

    @DeleteMapping("/remove-parent-from-student")
    public ResponseEntity<Boolean> removeParentFromStudent(@RequestParam Long studentId, @RequestParam Long parentId) {
        return ResponseEntity.ok(teacherService.removeParentFromStudent(studentId, parentId));
    }

    @PutMapping("/edit-parent-of-student")
    public ResponseEntity<Boolean> editParentOfStudent(@RequestParam Long studentId, @RequestParam Long newParentId) {
        return ResponseEntity.ok(teacherService.editParentOfStudent(studentId, newParentId));
    }
}