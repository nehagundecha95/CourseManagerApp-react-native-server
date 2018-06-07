package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Exam;
import webdev.models.FillInTheBlanksQuestion;
import webdev.models.MultipleChoiceQuestion;
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
	@PutMapping("/api/exam/{examId}/update/blanks")
	public FillInTheBlanksQuestion updateFillInTheBlanksQuestion(@PathVariable("examId") int id, @RequestBody FillInTheBlanksQuestion newQuestion) {
		System.out.println("in here update true false");
		Optional<FillInTheBlanksQuestion> data = fillInTheBlanksRepository.findById(id);
//		System.out.println("assignmentId:"+id);
		if(data.isPresent()) {
			FillInTheBlanksQuestion question = data.get();
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
//			assignment.setText(newAssignment.getText());
			question.setTitle(newQuestion.getTitle());
			question.setVariables(newQuestion.getVariables());
//			module.setCourse(newModule.getCourse());
			
			fillInTheBlanksRepository.save(question);	
			return question;
		}	
		else 
			return null;
	}
	@DeleteMapping("/api/exam/blanks/{questionId}")
	public void deleteFillInTheBlanksQuestion(@PathVariable("questionId")int questionId) {
		fillInTheBlanksRepository.deleteById(questionId);
	}
}
