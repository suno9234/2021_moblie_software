package com.example.project;

public class Word {
    private String word;
    private String mean;
    private String theme;
    private int grade;

    public Word(String word, String mean, String theme, int grade) {
        this.word = word;
        this.mean = mean;
        this.theme = theme;
        this.grade = grade;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
