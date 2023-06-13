package com.company.lndprotips.QuestionModel;

public class DbModel {
    private String id;
    private String quizTitle;
    private String correct;
    private String incorrect;
    private String totalQuestion;
    private String percentage;

    // table name and table column
    public static final String TABLE_QUIZ = "QuizTable";
    public static final String KEY_ID = "Id";
    public static final String KEY_QUIZ_TITLE = "QuizTitle";
    public static final String KEY_CORRECT = "Correct";
    public static final String KEY_INCORRECT = "Incorrect";
    public static final String KEY_TOTAL_QUESTION = "TotalQuestion";
    public static final String KEY_PERCENTAGE = "Percentage";

    // create, drop and select table
    public static final String CREATE_QUIZ_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s)", TABLE_QUIZ, KEY_ID, KEY_QUIZ_TITLE, KEY_CORRECT, KEY_INCORRECT, KEY_TOTAL_QUESTION, KEY_PERCENTAGE);
    public static final String DROP_QUIZ_TABLE = "DROP TABLE IF EXISTS " + TABLE_QUIZ;
    public static final String SELECT_QUIZ_TABLE = "SELECT * FROM " + TABLE_QUIZ;

    // constructor of the class
    public DbModel() {
    }
    public DbModel(String quizTitle, String correct, String incorrect, String totalQuestion, String percentage) {
        this.quizTitle = quizTitle;
        this.correct = correct;
        this.incorrect = incorrect;
        this.totalQuestion = totalQuestion;
        this.percentage = percentage;
    }
    public DbModel(String id, String quizTitle, String correct, String incorrect, String totalQuestion, String percentage) {
        this.id = id;
        this.quizTitle = quizTitle;
        this.correct = correct;
        this.incorrect = incorrect;
        this.totalQuestion = totalQuestion;
        this.percentage = percentage;
    }


    // getter and setter of all the data member

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(String incorrect) {
        this.incorrect = incorrect;
    }

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

}
