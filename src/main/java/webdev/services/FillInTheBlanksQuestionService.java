package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Exam;
import webdev.models.FillInTheBlanksQuestion;
import webdev.repositories.EssayRepository;
import webdev.repositories.ExamRepository;
import webdev.repositories.FillInTheBlanksRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.MultipleChoicesQuestionRepository;
import webdev.repositories.TrueFalseQuestionRepository;

@RestController
@CrossOrigin(origins = "*")
public class FillInTheBlanksQuestionService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	TrueFalseQuestionRepository trueFalseRepository;
	@Autowired
	MultipleChoicesQuestionRepository mutiRepo;
	@Autowired
	FillInTheBlanksRepository fillInTheBlanksRepository;
	@Autowired
	EssayRepository essayRepository;
	@Autowired
	LessonRepository lessonRepository;
	
	@GetMapping("/api/blanks/{questionId}")
	public FillInTheBlanksQuestion findFillInTheBlanksQuestionById(@PathVariable("questionId") int questionId) {
		Optional<FillInTheBlanksQuestion> optional = fillInTheBlanksRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eid}/blanks")
	public FillInTheBlanksQuestion createFillInTheBlanksQuestion(@PathVariable("eid") int eid, @RequestBody FillInTheBlanksQuestion newFillInTheBlanksQuestion) {
//		System.out.println("reached createAssignmnet");
		Optional<Exam> data1 = examRepository.findById(eid);
		Exam exam = null;
		if(data1.isPresent()) {
			exam = data1.get();
		}
		newFillInTheBlanksQuestion.setExam(exam);
		newFillInTheBlanksQuestion.setType("FillInTheBlanks");
		
		return fillInTheBlanksRepository.save(newFillInTheBlanksQuestion);
		
	}
}
