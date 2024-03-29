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

import webdev.models.EssayQuestion;
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
public class EssayQuestionService {
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
	
	
	@GetMapping("/api/essay/{questionId}")
	public EssayQuestion essayQuestionById(@PathVariable("questionId") int questionId) {
		Optional<EssayQuestion> optional = essayRepository.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	@PostMapping("/api/exam/{eid}/essay")
	public EssayQuestion createEssayQuestion(@PathVariable("eid") int eid, @RequestBody EssayQuestion newEssayQuestion) {
//		System.out.println("reached createAssignmnet");
		Optional<Exam> data1 = examRepository.findById(eid);
		Exam exam = null;
		if(data1.isPresent()) {
			exam = data1.get();
		}
		newEssayQuestion.setExam(exam);
		newEssayQuestion.setType("Essay");
		
		return essayRepository.save(newEssayQuestion);
		
	}
	@PutMapping("/api/exam/{examId}/update/essay")
	public EssayQuestion updateEssayQuestion(@PathVariable("examId") int id, @RequestBody EssayQuestion newQuestion) {
		System.out.println("in here update true false");
		Optional<EssayQuestion> data = essayRepository.findById(id);
//		System.out.println("assignmentId:"+id);
		if(data.isPresent()) {
			EssayQuestion question = data.get();
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
//			assignment.setText(newAssignment.getText());
			question.setTitle(newQuestion.getTitle());
//			module.setCourse(newModule.getCourse());
			
			essayRepository.save(question);	
			return question;
		}	
		else 
			return null;
	}
	@DeleteMapping("/api/exam/essay/{questionId}")
	public void deleteEssayQuestion(@PathVariable("questionId")int questionId) {
		essayRepository.deleteById(questionId);
	}
}
