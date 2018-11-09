package com.londonappbrewery.quizzler_complete;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class MainActivity extends Activity {


    // Member variables accessible in all the methods of the MainActivity:
    Button next,prev,finish;
    boolean aa,bb,cc,dd;
    TextView mScoreTextView;
    int currentQuestion;
    TextView t;
    TextView mQuestionTextView;
    CountDownTimer timer;
    Animation anim;
    int fakeAnswerCount;
    ProgressBar scoreBar;
    int mIndex;
    int mScore;
    int timeController=100;
    int mQuestion;
    Toast mToastMessage; // For keeping track if a Toast message is being shown.

    // Create question bank using the TrueFalse class for each item in the array
    @NonNull
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, "Sigmund Freud","Absolute threshold","Action potential","Aggression"),
            new TrueFalse(R.string.question_2, "Counseling Psychology","Anxiety","Anxiety disorders","Associationism"),
            new TrueFalse(R.string.question_3, "Monochromatic Light","Attachment","Attitude","Attribution theory"),
            new TrueFalse(R.string.question_4, "Little Albert","Avoidance learning","Behavior","Binocular depth cues"),
            new TrueFalse(R.string.question_5, "Social-cultural","Central nervous system","Cerebellum:","Cerebral cortex"),
            new TrueFalse(R.string.question_6, "Charles Darwin","Cerebral hemispheres","Classical conditioning","Cognitive development"),
            new TrueFalse(R.string.question_7, "Control","Cognitive dissonance theory","Conditioned stimulus","Conditioned reflex"),
            new TrueFalse(R.string.question_8, "Split brain","Conformity",
                    "Consciousness","Contrast"),
            new TrueFalse(R.string.question_9, "Naturalistic Observation","Control group","Correlation coefficient","Correlational method"),
            new TrueFalse(R.string.question_10, "Neuron","Dendrite","Deoxyribonucleic acid","Dependent variable"),
            new TrueFalse(R.string.question_11, "Structuralism","Depression","Depth perception","Determinism:"),
            new TrueFalse(R.string.question_12, "Frontal lobes","Developmental stages:","Distance cues","Electroencephalograph"),
            new TrueFalse(R.string.question_13,"Humanistic Psychology","Empiricism","Evolution","Extinction")
    };

    // Declaring constants here. Rather than a fixed number, choosing to make it a function
    // of the length of the question bank (the number of questions)
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // In API 26 'casting' is no longer needed.
        // For API 25 and lower: mTrueButton = (Button) findViewById(R.id.true_button);
        // For API 26 and higher can use: mTrueButton = findViewById(R.id.true_button);
        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        final Random rand=new Random();


        t=(TextView)findViewById(R.id.timer);

        //mScoreTextView = findViewById(R.id.score);
        RadioButton a = (RadioButton) findViewById(R.id.A);RadioButton b=(RadioButton)findViewById(R.id.B);RadioButton c=(RadioButton)findViewById(R.id.C);RadioButton d=(RadioButton)findViewById(R.id.D);
        final RadioButton[] options={a,b,c,d};

        timer=new CountDownTimer(timeController*1000,1000) {
            @Override
            public void onTick(long time) {
                t.setText(""+time/1000);
                if((time/1000)<=5){
                    t.setTextColor(Color.RED);
                    t.startAnimation(anim);
                }else{
                    t.setTextColor(Color.WHITE);
                    t.clearAnimation();
                }
            }

            @Override
            public void onFinish() {
                t.setText("0");

            }
        }.start();

        next=(Button)findViewById(R.id.submit);
        // Restores the 'state' of the app upon screen rotation:
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
        } else {
            mScore = 0;
            mIndex = 0;
        }

        final RadioGroup group=(RadioGroup)findViewById(R.id.radioGroup);
        //mQuestion = mQuestionBank[mIndex].getQuestionID();

        scoreBar=(ProgressBar)findViewById(R.id.progressBar);
        scoreBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);

        // setting question and options
        currentQuestion=0;
        final int[] correctAnswerCount = {0};
        final int[] memory=new int[mQuestionBank.length];
        for(int i=0;i<mQuestionBank.length;i++){
            memory[i]=5;
        }
        final int[] finalAnswers={0,1,2,3,0,1,2,3,0,1,2,3};
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        final boolean[] startFromStart = {false};
        final boolean[] endFromStart = {false};

        finish=(Button)findViewById(R.id.finish_id);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                for(int i=0; i<4; i++)
                {
                    if(options[i].isChecked()){
                        memory[currentQuestion]=i;
                    }
                }
                try{
                    options[memory[currentQuestion]].setChecked(false);
                    options[memory[currentQuestion]].setChecked(false);
                    options[memory[currentQuestion]].setChecked(false);
                }catch (Exception e){

                }
                if(currentQuestion==12){
                    currentQuestion=-1;
                    next.setText("Next");
                }
                if(currentQuestion<12){
                    currentQuestion++;
                    mQuestionTextView.setText(mQuestionBank[currentQuestion].getQuestionID());
                    options[currentQuestion%4].setText(mQuestionBank[currentQuestion].getAnswer());
                    options[(currentQuestion+1)%4].setText(mQuestionBank[currentQuestion].getA1());
                    options[(currentQuestion+2)%4].setText(mQuestionBank[currentQuestion].getA2());
                    options[(currentQuestion+3)%4].setText(mQuestionBank[currentQuestion].getA3());
                    if(currentQuestion==12){
                        next.setText("Review");
                        finish.setVisibility(View.VISIBLE);

                    }
                }
                }
        });
        final int[] scored = {1};
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(startFromStart[0]){
                    mQuestionTextView.setText("");
                    for (int i=0;i<memory.length;i++){
                        try{
                            if(scored[0] ==1&&memory[i]==finalAnswers[i]){
                                mScore++;
                            }
                        }catch (Exception e){
                            Log.d("Exception", ""+e);
                        }

                    }
                    scored[0] =0;
                    group.setVisibility(View.INVISIBLE);
                    t.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                    finish.setText("Exit");
                    mQuestionTextView.setText("Score: "+mScore);
                }
                else if(!startFromStart[0] &&!endFromStart[0]){
                    finish.setText("Finish");
                    finish.setVisibility(View.INVISIBLE);
                    mQuestionTextView.setTextSize(30);
                    next.setVisibility(View.VISIBLE);
                    group.setVisibility(View.VISIBLE);
                    t.setVisibility(View.VISIBLE);
                    mQuestionTextView.setText(mQuestionBank[0].getQuestionID());
                    options[0].setText(mQuestionBank[0].getAnswer());
                    options[1].setText(mQuestionBank[0].getA1());
                    options[2].setText(mQuestionBank[0].getA2());
                    options[3].setText(mQuestionBank[0].getA3());
                    timeController=101;
                    endFromStart[0] =true;
                    startFromStart[0] =true;

                }

            }
        });
    }
}



