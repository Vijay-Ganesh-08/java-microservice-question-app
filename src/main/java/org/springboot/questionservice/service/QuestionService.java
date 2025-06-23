package org.springboot.questionservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springboot.questionservice.model.QuestionWrapper;
import org.springboot.questionservice.model.Questions;
import org.springboot.questionservice.model.QuizResponse;
import org.springboot.questionservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<List<Questions>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Exception in getAllQuestions: {}", e.getMessage());
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Questions>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findAllByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Exception in getQuestionsByCategory: {}", e.getMessage());
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestions(Questions questions) {
        questionRepository.save(questions);
        return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestions(Questions questions) {
        questionRepository.delete(questions);
        return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);

    }

    public ResponseEntity<String> updateQuestion(Questions questions) {
        questionRepository.save(questions);
        return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);

    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer limit) {
        List<Questions> questions = questionRepository.findRandomQuestionsByCategory(category, limit);

        List<Integer> questionIds = new ArrayList<>();
        for (Questions question : questions) {
            questionIds.add(question.getId());
        }
        return new ResponseEntity<>(questionIds, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getAllQuestionsForIDs(List<Integer> ids) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Questions> questions = new ArrayList<>();
        for (Integer id : ids) {
            questions.add(questionRepository.findById(id));
        }
        for (Questions question : questions) {
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            questionWrappers.add(questionWrapper);
        }

        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<QuizResponse> quizResponses) {
        int correctAnswers = 0;

        for (QuizResponse quizResponse : quizResponses) {
            Questions questions = questionRepository.findById(quizResponse.getId());
            if (quizResponse.getResponse().equals(questions.getRightAnswer())) {
                correctAnswers++;
            }
        }

        return new ResponseEntity<>(correctAnswers, HttpStatus.OK);
    }
}
