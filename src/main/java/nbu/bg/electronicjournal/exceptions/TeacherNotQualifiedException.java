package nbu.bg.electronicjournal.exceptions;

public class TeacherNotQualifiedException extends RuntimeException {
    public TeacherNotQualifiedException(String message) {
        super(message);
    }

    public TeacherNotQualifiedException(String teacher, String subject) {
        super(String.format("Teacher %s doesn't have qualification for %s", teacher, subject));
    }
}
