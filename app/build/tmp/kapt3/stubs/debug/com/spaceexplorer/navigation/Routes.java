package com.spaceexplorer.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/spaceexplorer/navigation/Routes;", "", "()V", "BROWSE", "", "GALLERY", "LEADERBOARD", "LESSON", "QUIZ", "QUIZ_RESULT", "WELCOME", "lesson", "id", "", "quiz", "quizResult", "score", "quizId", "app_debug"})
public final class Routes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WELCOME = "welcome";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BROWSE = "browse";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LEADERBOARD = "leaderboard";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GALLERY = "gallery";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LESSON = "lesson/{lessonId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String QUIZ = "quiz/{quizId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String QUIZ_RESULT = "quiz_result/{score}/{quizId}";
    @org.jetbrains.annotations.NotNull()
    public static final com.spaceexplorer.navigation.Routes INSTANCE = null;
    
    private Routes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String lesson(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String quiz(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String quizResult(int score, int quizId) {
        return null;
    }
}