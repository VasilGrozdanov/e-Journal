package nbu.bg.electronicjournal.controller;

import lombok.AllArgsConstructor;
import nbu.bg.electronicjournal.annotation.security.isTeacher;
import nbu.bg.electronicjournal.model.dto.StudentDto;
import nbu.bg.electronicjournal.model.dto.UserDetailsImpl;
import nbu.bg.electronicjournal.model.entity.*;
import nbu.bg.electronicjournal.repository.*;
import nbu.bg.electronicjournal.service.StudentService;
import nbu.bg.electronicjournal.service.TeacherService;
import nbu.bg.electronicjournal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private UserService userService;
    private ParentRepository parentRepository;
    SubjectTeachedByRepository subjectTeachedByRepository;
    SubjectRepository subjectRepository;
    private EvaluatesRepository evaluatesRepository;
    private AbsenceRepository absenceRepository;


    @ModelAttribute("teachers")
    public List<Teacher> getTeachers() {
        return teacherService.getAll();
    }

    @isTeacher
    @GetMapping("/teacher/{id}")
    public String showTeacherPage(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacher(id);
        model.addAttribute("teacher", teacher);
        return "teacher-details";
    }

    @isTeacher
    @GetMapping("/teacher/qualifications/{id}")
    public String showTeacherQualificationsPage(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherWithQualifications(id);
        model.addAttribute("teacher", teacher);
        return "teacher-qualifications";
    }

    @isTeacher
    @PostMapping("/teacher/mark/add")
    public String addMark(@RequestParam Long teacherId,
                          @RequestParam Long studentId,
                          @RequestParam Long subjectId,
                          @RequestParam int mark,
                          Model model) {
        try {
            teacherService.addMark(teacherId, studentId, subjectId, mark);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "redirect:/error";
        }
        return "redirect:/teacher/" + teacherId;
    }

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

    @PutMapping("/add-mark/{studentId}")
    public String addMark(@RequestParam Long teacherId, @RequestParam Long studentId, @RequestParam Long subjectId, @RequestParam int mark, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        try {

            Student student = studentService.getStudent(studentId);
            Teacher teacher = student.getGrade().getHeadTeacher();

        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "students";
    }

    @GetMapping("/add-mark/{studentId}")
    public String addMark(@PathVariable Long studentId, Authentication authentication, Model model){

        if (authentication == null) {
            throw new RuntimeException();
        }
        try {

            Student student = studentService.getStudent(studentId);
            model.addAttribute("student", student);

        }
        catch (Exception e) {
            return "redirect:/404";
        }

        return "student-addmark";
    }

    @DeleteMapping("/delete-mark/{evaluationId}")
    public ResponseEntity<Boolean> deleteMark(@PathVariable Long evaluationId) {
        return ResponseEntity.ok(teacherService.deleteMark(evaluationId));
    }
    @PutMapping("/edit-mark")
    public ResponseEntity<Boolean> editMark(@RequestParam Long evaluationId, @RequestParam int newMark) {
        return ResponseEntity.ok(teacherService.editMark(evaluationId, newMark));
    }

    @GetMapping("/students-parents")
    public String addParentToStudent(Authentication authentication, Model model) {

        if (authentication == null) {
            throw new RuntimeException();
        }
        try {

            List<Student> students = studentService.getStudents().stream()
                    .filter(student -> student.getGrade().getHeadTeacher().getUsername() == authentication.getName()).collect(Collectors.toList());

            model.addAttribute("students", students);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "students-parents";
    }

    @DeleteMapping("/remove-parent-from-student")
    public ResponseEntity<Boolean> removeParentFromStudent(@RequestParam Long studentId, @RequestParam Long parentId) {
        return ResponseEntity.ok(teacherService.removeParentFromStudent(studentId, parentId));
    }

    @PutMapping("/edit-parent-of-student")
    public ResponseEntity<Boolean> editParentOfStudent(@RequestParam Long studentId, @RequestParam Long newParentId) {
        return ResponseEntity.ok(teacherService.editParentOfStudent(studentId, newParentId));
    }





    //====================== Teacher related operations =========================

    @GetMapping("/my-students")
    public String showMysStudents(Authentication authentication, Model model) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        Long teacherId = userService.getUserIdByUsername(authentication.getName());
        try {

            List<StudentDto> students = teacherService.getMyStudents(teacherId);
            model.addAttribute("students", students);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "my-students";
    }

    @GetMapping("/students/update/studentName={studentName}&studentId={studentId}")
    public String updateParent(
            @PathVariable(value = "studentName", required = false) String studentName,
            @PathVariable(value = "studentId", required = false) Long studentId,
            Model model){
        try {

            List<Parent> parentList = parentRepository.findAll();
            model.addAttribute("parents", parentList);
            model.addAttribute("studentName", studentName);
            model.addAttribute("studentId", studentId);

            List<Parent> parents = parentRepository.findAll();
            parents = parents.stream()
                    .filter(parent -> parent.getKids().stream()
                            .anyMatch(child -> studentId.equals(child.getId())))
                    .collect(Collectors.toList());
            if(parents !=null && !parents.isEmpty()){
                model.addAttribute("parentId", parents.get(0).getId());
            }

        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "update-parent";
    }

    @PostMapping("/students/update-parent")
    public String updateKidsParent(@RequestParam("studentId") Long studentId,
                                   @RequestParam("parentId") Long parentId){

        try {
            if(parentId == null || parentId == 0){
                List<Parent> parents = parentRepository.findAll();
                parents = parents.stream()
                        .filter(parent -> parent.getKids().stream()
                                .anyMatch(child -> studentId.equals(child.getId())))
                        .collect(Collectors.toList());
                if(parents !=null && !parents.isEmpty()){
                    parents.stream().forEach(parent -> {
                        Set<Student> kids = parent.getKids().stream().filter(kid-> !Objects.equals(kid.getId(), studentId)).collect(Collectors.toSet());
                        parent.setKids(kids);
                        parentRepository.save(parent);
                    });
                }

            }else{
                Optional<Parent> parent = parentRepository.findById(parentId);
                if(parent.isPresent()){
                    List<Student> kids = new ArrayList<>(parent.get().getKids());
                    if(kids !=null && !kids.isEmpty()){
                        kids.add(studentService.getStudent(studentId));
                    }else{
                        kids = List.of(studentService.getStudent(studentId));
                    }
                    parent.get().setKids(new HashSet<>(kids));
                    parentRepository.save(parent.get());
                }
            }
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/my-students";
    }

    //========================== Student Marks related APIs ===================================
    @GetMapping("/my-students-marks")
    public String showMysStudentsMarks(Authentication authentication, Model model) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        Long teacherId = userService.getUserIdByUsername(authentication.getName());
        try {
            List<StudentDto> students = teacherService.getMyStudents(teacherId);
            List<Subject> subjects = new ArrayList<>();
            List<SubjectTeachedBy> subjectTeachedByList = subjectTeachedByRepository.findAll();
            subjectTeachedByList = subjectTeachedByList.stream()
                    .filter(parent -> parent.getTeacher().getId().equals(teacherId))
                    .collect(Collectors.toList());
            subjectTeachedByList.forEach(subjectTeachedBy -> subjects.add(subjectTeachedBy.getSubject()));

            List<Evaluates> evaluates = evaluatesRepository.findAll();
            evaluates = evaluates.stream()
                    .filter(item -> item.getTeacher().getId().equals(teacherId))
                    .collect(Collectors.toList());

            model.addAttribute("students", students);
            model.addAttribute("subjects", subjects);
            model.addAttribute("evaluates", evaluates);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "add-student-marks";
    }

    @PostMapping("/students/add-student-marks")
    public String addStudentMarks(Authentication authentication,
                                  @RequestParam("studentId") Long studentId,
                                  @RequestParam("subjectId") String subjectId,
                                  @RequestParam("mark") int mark){
        try {
            Evaluates evaluate = Evaluates.builder().teacher(teacherService.getTeacher(userService.getUserIdByUsername(authentication.getName()))).
                    subject(subjectRepository.findBySignature(subjectId).get()).
                    student(studentService.getStudent(studentId))
                    .systemDate(LocalDateTime.now()).entryDate(LocalDateTime.now()).mark(mark).build();
            evaluatesRepository.save(evaluate);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/my-students-marks";
    }

    @GetMapping("/students/edit-students-marks/studentId={studentId}&subjectId={subjectId}&systemDate={systemDate}&mark={mark}")
    public String editMysStudentsMarks(Authentication authentication, Model model,
                                       @PathVariable("studentId") Long studentId,
                                       @PathVariable("subjectId") String subjectId,
                                       @PathVariable("systemDate") String systemDate,
                                       @PathVariable("mark") int mark) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        Long teacherId = userService.getUserIdByUsername(authentication.getName());
        try {
            List<StudentDto> students = teacherService.getMyStudents(teacherId);
            List<Subject> subjects = new ArrayList<>();
            List<SubjectTeachedBy> subjectTeachedByList = subjectTeachedByRepository.findAll();
            subjectTeachedByList = subjectTeachedByList.stream()
                    .filter(parent -> parent.getTeacher().getId().equals(teacherId))
                    .collect(Collectors.toList());
            subjectTeachedByList.forEach(subjectTeachedBy -> subjects.add(subjectTeachedBy.getSubject()));

            model.addAttribute("students", students);
            model.addAttribute("subjects", subjects);
            model.addAttribute("studentId", studentId);
            model.addAttribute("subjectId", subjectId);
            model.addAttribute("systemDate", LocalDateTime.parse(systemDate));
            model.addAttribute("mark", mark);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "edit-student-marks";
    }


    @PutMapping("/students/update-student-marks")
    public String updateStudentMarks(Authentication authentication,
                                     @RequestParam("studentId") Long studentId,
                                     @RequestParam("subjectId") String subjectId,
                                     @RequestParam("systemDate") String systemDate,
                                     @RequestParam("mark") int mark){
        try {
            Evaluates evaluate = Evaluates.builder().teacher(teacherService.getTeacher(userService.getUserIdByUsername(authentication.getName()))).
                    subject(subjectRepository.findBySignature(subjectId).get()).
                    student(studentService.getStudent(studentId))
                    .systemDate(LocalDateTime.parse(systemDate)).entryDate(LocalDateTime.now()).mark(mark).build();
            evaluatesRepository.save(evaluate);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/my-students-marks";
    }

    @DeleteMapping("/students/student-mark/studentId={studentId}&subjectId={subjectId}&systemDate={systemDate}")
    public String removeStudentMarks(Authentication authentication,
                                     @PathVariable("studentId") Long studentId,
                                     @PathVariable("subjectId") String subjectId,
                                     @PathVariable("systemDate") String systemDate){
        try {
            Long teacherId = userService.getUserIdByUsername(authentication.getName());
            Subject subject  = subjectRepository.findBySignature(subjectId).get();
            Student student = studentService.getStudent(studentId);
            Teacher teacher = teacherService.getTeacher(teacherId);
            Evaluates evaluates = new Evaluates(teacher, subject, student, LocalDateTime.parse(systemDate));
            evaluatesRepository.delete(evaluates);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/my-students-marks";
    }


    //========================== Student Absence related APIs ===================================
    @GetMapping("/my-students-absences")
    public String showStudentsAbsence(Authentication authentication, Model model) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        Long teacherId = userService.getUserIdByUsername(authentication.getName());
        try {
            List<StudentDto> students = teacherService.getMyStudents(teacherId);
            List<Subject> subjects = new ArrayList<>();
            List<SubjectTeachedBy> subjectTeachedByList = subjectTeachedByRepository.findAll();
            subjectTeachedByList = subjectTeachedByList.stream()
                    .filter(parent -> parent.getTeacher().getId().equals(teacherId))
                    .collect(Collectors.toList());
            subjectTeachedByList.forEach(subjectTeachedBy -> subjects.add(subjectTeachedBy.getSubject()));

            List<Absence> absences = absenceRepository.findAll();
            absences = absences.stream()
                    .filter(absence -> absence.getTeacher().getId().equals(teacherId))
                    .collect(Collectors.toList());
            model.addAttribute("students", students);
            model.addAttribute("subjects", subjects);
            model.addAttribute("absences", absences);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "add-student-absence";
    }

    @PostMapping("/students/add-student-absence")
    public String addStudentAbsence(Authentication authentication,
                                    @RequestParam("studentId") Long studentId,
                                    @RequestParam("subjectId") String subjectId,
                                    @RequestParam("absenceType") AbsenceType absenceType){
        try {
            Absence absence = Absence.builder().teacher(teacherService.getTeacher(userService.getUserIdByUsername(authentication.getName()))).
                    subject(subjectRepository.findBySignature(subjectId).get()).
                    student(studentService.getStudent(studentId))
                    .systemDate(LocalDateTime.now()).entryDate(LocalDateTime.now()).type(absenceType)
                    .build();
            absenceRepository.save(absence);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/my-students-absences";
    }

    @DeleteMapping("/students/student-absence/studentId={studentId}&subjectId={subjectId}&systemDate={systemDate}")
    public String removeStudentAbsence(Authentication authentication,
                                       @PathVariable("studentId") Long studentId,
                                       @PathVariable("subjectId") String subjectId,
                                       @PathVariable("systemDate") String systemDate){
        try {
            Long teacherId = userService.getUserIdByUsername(authentication.getName());
            Subject subject  = subjectRepository.findBySignature(subjectId).get();
            Student student = studentService.getStudent(studentId);
            Teacher teacher = teacherService.getTeacher(teacherId);
            Absence absence = new Absence(teacher, subject, student, LocalDateTime.parse(systemDate));
            absenceRepository.delete(absence);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/my-students-absences";
    }


    @GetMapping("/students/edit-students-absence/studentId={studentId}&subjectId={subjectId}&systemDate={systemDate}&absenceType={absenceType}")
    public String editMysStudentsAbsence(Authentication authentication, Model model,
                                         @PathVariable("studentId") Long studentId,
                                         @PathVariable("subjectId") String subjectId,
                                         @PathVariable("systemDate") String systemDate,
                                         @PathVariable("absenceType") String absenceType) {
        if (authentication == null) {
            throw new RuntimeException();
        }
        Long teacherId = userService.getUserIdByUsername(authentication.getName());
        try {
            List<StudentDto> students = teacherService.getMyStudents(teacherId);
            List<Subject> subjects = new ArrayList<>();
            List<SubjectTeachedBy> subjectTeachedByList = subjectTeachedByRepository.findAll();
            subjectTeachedByList = subjectTeachedByList.stream()
                    .filter(parent -> parent.getTeacher().getId().equals(teacherId))
                    .collect(Collectors.toList());
            subjectTeachedByList.forEach(subjectTeachedBy -> subjects.add(subjectTeachedBy.getSubject()));

            model.addAttribute("students", students);
            model.addAttribute("subjects", subjects);
            model.addAttribute("studentId", studentId);
            model.addAttribute("subjectId", subjectId);
            model.addAttribute("systemDate", LocalDateTime.parse(systemDate));
            model.addAttribute("absenceType", absenceType);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "edit-student-absence";
    }


    @PutMapping("/students/update-student-absence")
    public String updateStudentAbsence(Authentication authentication,
                                       @RequestParam("studentId") Long studentId,
                                       @RequestParam("subjectId") String subjectId,
                                       @RequestParam("systemDate") String systemDate,
                                       @RequestParam("absenceType") AbsenceType absenceType){
        try {
            Absence absence = Absence.builder().teacher(teacherService.getTeacher(userService.getUserIdByUsername(authentication.getName()))).
                    subject(subjectRepository.findBySignature(subjectId).get()).
                    student(studentService.getStudent(studentId))
                    .systemDate(LocalDateTime.parse(systemDate)).entryDate(LocalDateTime.now()).type(absenceType)
                    .build();
            absenceRepository.save(absence);
        }
        catch (Exception e) {
            return "redirect:/404";
        }
        return "redirect:/my-students-absences";
    }
}