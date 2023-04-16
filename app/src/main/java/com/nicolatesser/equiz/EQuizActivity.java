package com.nicolatesser.equiz;

import android.os.Bundle;

import com.nicolatesser.androidquiztemplate.activity.QuizActivity;
import com.nicolatesser.androidquiztemplate.quiz.Answer;
import com.nicolatesser.androidquiztemplate.quiz.EduGame;
import com.nicolatesser.androidquiztemplate.quiz.Game;
import com.nicolatesser.androidquiztemplate.quiz.GameHolder;
import com.nicolatesser.androidquiztemplate.quiz.Question;
import com.nicolatesser.androidquiztemplate.quiz.QuestionDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EQuizActivity extends QuizActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        QuestionDatabase questionDatabase = new QuestionDatabase();
        questionDatabase.prepare(loadQuestions());
        Game game = new EduGame(questionDatabase, new ArrayList<>());

        GameHolder.setInstance(game);

        loadSession();
        super.onCreate(savedInstanceState);
    }

    public List<Question> loadQuestions(){
        List<Question> questions = new ArrayList<>();

        for (int e=0;e<11;e++){
            for (int y=0;y<11;y++){
                questions.add(createSimpleMultiplyMathQuestion(e,y));
            }
        }


        return questions;
    }


    public Question createQuestion(String questionText, String correctAnswer, String...wrongAnswers){
        List<Answer> answers = new LinkedList<Answer>();
        answers.add(new Answer(correctAnswer, true));
        for (String wrongAnswer:wrongAnswers){
            answers.add(new Answer(wrongAnswer, false));
        }
        return new Question(questionText, answers, new ArrayList<>());
    }


    public Question createSimpleMultiplyMathQuestion(int a, int b){


        int res = a*b;


        List<Answer> answers = new LinkedList<Answer>();


        answers.add(new Answer(Integer.toString(res), true));


        while (answers.size()<4){
            addIfNewWrongAnswer(wrongAnswerPlusMinuxDelta(res),answers);
            addIfNewWrongAnswer(wrongAnswerMultiplyDelta(a,b),answers);
            addIfNewWrongAnswer(wrongAnswerMultiplyDelta(a,b),answers);
        }


        return new Question(a+" x "+b+ " = ", answers, new ArrayList<>());
    }

    private void addIfNewWrongAnswer(Answer answerToAdd,List<Answer> answers) {
        for (Answer answer:answers){
            if (answerToAdd.getText().equals(answer.getText())){
                return;
            }
        }
        answers.add(answerToAdd);
    }


    private Answer wrongAnswerPlusMinuxDelta(int res) {
        Random rn = new Random();
        int delta = rn.nextInt() % 2 +1;
        if (rn.nextBoolean()){
           return new Answer(Integer.toString(res +delta), false);
        }
        else {
            return new Answer(Integer.toString(res -delta), false);
        }
    }

    private Answer wrongAnswerMultiplyDelta(int a, int b) {
        Random rn = new Random();
        int delta = rn.nextInt() % 2 +1;
        boolean first = rn.nextBoolean();
        boolean plus = rn.nextBoolean();

        if (first == true && plus==true){
            return new Answer(Integer.toString((a +delta)*b), false);
        }
        else if (first == true && plus==false){
            return new Answer(Integer.toString((a -delta)*b), false);
        }
        else if (first == false && plus==true){
            return new Answer(Integer.toString(a*(b+delta)), false);
        }
        else{
            return new Answer(Integer.toString(a*(b-delta)), false);
        }
    }
}