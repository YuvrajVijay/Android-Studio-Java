package com.yuvrajvi.trivia.data;

import com.yuvrajvi.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processfinished(ArrayList<Question> questionArrayList);
}
