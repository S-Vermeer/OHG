package com.example.navigationarrow;

import java.util.ArrayList;

public class Assignment {
    private int Id;
    private String Question;
    private ArrayList<String> AnswerOptions;
    private int AnswerId;
    private ArrayList<Integer> AnswerIdsList;
    private int Mistakes;

    public Assignment(int id, String question){
        Id = id;
        Question = question;
    }

    public Assignment(int id, String question, ArrayList<String> answerOptions){
        Id = id;
        Question = question;
        AnswerOptions = answerOptions;
    }

    public Assignment(int id, String question, ArrayList<String> answerOptions, int answerId){
        Id = id;
        Question = question;
        AnswerOptions = answerOptions;
        AnswerId = answerId;
    }

    public Assignment(int id, String question, ArrayList<String> answerOptions, ArrayList<Integer> answerIds){
        Id = id;
        Question = question;
        AnswerOptions = answerOptions;
        AnswerIdsList = answerIds;
    }

    public void setId(int id){
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getQuestion(){
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public void addAnswerOption(String answer){
        AnswerOptions.add(answer);
    }

    public void setAnswerOptions(ArrayList<String> answerOptions){
        AnswerOptions = answerOptions;
    }

    public ArrayList<String> getAnswerOptions(){
        return AnswerOptions;
    }

    public void setAnswerId(int answerId) {
        AnswerId = answerId;
    }

    public int getAnswerId() {
        return AnswerId;
    }

    public Boolean checkAnswer(int id){
        if(id == AnswerId){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkAnswer(ArrayList<Integer> ids){
        int mistakes = 0;
        for(int i = 0; i < AnswerOptions.size(); i++){
            if(AnswerIdsList.contains(i) && ids.contains(i)){
                mistakes++;
            } else if(!AnswerIdsList.contains(i) && ids.contains(i)){
                mistakes++;
            }
        }

        Mistakes = mistakes;

        if(mistakes == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public void setMistakes(int id){
        Mistakes = id;
    }

    public int getMistakes(){
        return Mistakes;
    }

}
