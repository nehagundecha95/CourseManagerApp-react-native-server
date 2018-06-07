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

import webdev.models.Assignment;
import webdev.models.Exam;
import webdev.models.TrueFalseQuestion;
import webdev.repositories.EssayRepository;
import webdev.repositories.ExamRepository;
import webdev.repositories.FillInTheBlanksRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.MultipleChoicesQuestionRepository;
import webdev.repositories.TrueFalseQuestionRepository;

@RestController
@CrossOrigin(origins = "*")
public class TrueFalseQuestionService {
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
	
	@GetMapping("/api/truefalse/{questionId}")
	public TrueFalseQuestion findTrueFalseQuestionById(@PathVariable("questionId") int questionId) {
		Optional<TrueFalseQuestion> optional = trueFalseRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	@PostMapping("/api/exam/{eid}/truefalse")
	public TrueFalseQuestion createTrueFalseQuestion(@PathVariable("eid") int eid, @RequestBody TrueFalseQuestion newTrueFalseQuestion) {
//		System.out.println("reached createAssignmnet");
		Optional<Exam> data1 = examRepository.findById(eid);
		Exam exam = null;
		if(data1.isPresent()) {
			exam = data1.get();
		}
		System.out.println("exam widget:"+newTrueFalseQuestion);
		System.out.println("exam widget title:"+newTrueFalseQuestion.getTitle());
		System.out.println("exam widget subtitile:"+newTrueFalseQuestion.getSubtitle());
		System.out.println("exam widget points:"+newTrueFalseQuestion.getPoints());
		newTrueFalseQuestion.setExam(exam);
		newTrueFalseQuestion.setType("TrueFalse");
		
		return trueFalseRepository.save(newTrueFalseQuestion);
		
	}
	
	@PutMapping("/api/exam/{examId}/update/truefalse")
	public TrueFalseQuestion updateTrueFalseQuestion(@PathVariable("examId") int id, @RequestBody TrueFalseQuestion newQuestion) {
		System.out.println("in here update true false");
		Optional<TrueFalseQuestion> data = trueFalseRepository.findById(id);
		System.out.println("assignmentId:"+id);
		if(data.isPresent()) {
			TrueFalseQuestion question = data.get();
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
//			assignment.setText(newAssignment.getText());
			question.setTitle(newQuestion.getTitle());
			question.setTrue(newQuestion.isTrue());
//			module.setCourse(newModule.getCourse());
			
			trueFalseRepository.save(question);	
			return question;
		}	
		else 
			return null;
	}
	@DeleteMapping("/api/exam/truefalse/{questionId}")
	public void deleteTrueFalseQuestion(@PathVariable("questionId")int questionId) {
		trueFalseRepository.deleteById(questionId);
	}
}
