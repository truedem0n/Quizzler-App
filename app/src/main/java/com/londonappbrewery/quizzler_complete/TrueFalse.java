package com.londonappbrewery.quizzler_complete;

// This is the model class that represents a single quiz question.
public class TrueFalse {

    // These are the placeholders for the question resource id and the correct answer
    private int mQuestionID;
    private String Tanswer;
    private String a1,a2,a3;

    public String getA1() {
        return a1;
    }

    public String getA2() {
        return a2;
    }

    public String getA3() {
        return a3;
    }

    // This is the constructor that will be called when a new quiz question is created.
    public TrueFalse(int questionResourceID, String TrueAnswer,String answer,String answer1,String answer2) {
        mQuestionID = questionResourceID;
        Tanswer = TrueAnswer;
        a1=answer;
        a2=answer1;
        a3=answer2;
    }

    // This method gives us access to info stored in the (private) question id.
    public int getQuestionID() {
        return mQuestionID;
    }

    // This method gives us access to info stored in the (private) mAnswer.
    public String getAnswer() {
        return Tanswer;
    }
}
