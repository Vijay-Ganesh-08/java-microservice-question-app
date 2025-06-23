package org.springboot.questionservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springboot.questionservice.model.QuestionWrapper;
import org.springboot.questionservice.model.Questions;
import org.springboot.questionservice.model.QuizResponse;
import org.springboot.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("getAllQuestions")
    public ResponseEntity<List<Questions>> getAllQuestions() {
        log.info("getAllQuestions");
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Questions>> getQuestionsByCategory(@PathVariable String category) {
        log.info("getQuestionsByCategory");
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping ("/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Questions questions) {
        log.info("addQuestion");
        return questionService.addQuestions(questions);
    }

    @DeleteMapping ("/deleteQuestion")
    public ResponseEntity<String> deleteQuestion(@RequestBody Questions questions) {
        log.info("deleteQuestion");
        return questionService.deleteQuestions(questions);
    }

    @PutMapping("/updateQuestion")
    public ResponseEntity<String> updateQuestion(@RequestBody Questions questions) {
        log.info("updateQuestion");
        return questionService.updateQuestion(questions);
    }

    @GetMapping("/getQuestionsIdsForQuiz")
    public ResponseEntity<List<Integer>> generateQuestions(@RequestParam String category, @RequestParam Integer limit) {
        log.info("generateQuestions");
        return questionService.getQuestionsForQuiz(category,limit);
    }

    @PostMapping("/getQuestionsForIds")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsForIds(@RequestBody List<Integer> questionIds) {
        log.info("getQuestionsForIds");
        return questionService.getAllQuestionsForIDs(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> generateScore(@RequestBody List<QuizResponse> quizResponses) {
        log.info("generateScore");
        return questionService.getScore(quizResponses);
    }

}
