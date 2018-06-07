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
import webdev.models.MultipleChoiceQuestion;
import webdev.models.TrueFalseQuestion;
import webdev.repositories.EssayRepository;
import webdev.repositories.ExamRepository;
import webdev.repositories.FillInTheBlanksRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.MultipleChoicesQuestionRepository;
import webdev.repositories.TrueFalseQuestionRepository;

@RestController
@CrossOrigin(origins = "*")
public class MultipleChoiceQuestionService {
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
	
	@GetMapping("/api/multi/{questionId}")
	public MultipleChoiceQuestion findMultiQuestionById(@PathVariable("questionId") int questionId) {
		Optional<MultipleChoiceQuestion> optional = mutiRepo.findById(questionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eid}/choice")
	public MultipleChoiceQuestion createMultipleChoiceQuestion(@PathVariable("eid") int eid, @RequestBody MultipleChoiceQuestion newMultipleChoiceQuestion) {
//		System.out.println("reached createAssignmnet");
		Optional<Exam> data1 = examRepository.findById(eid);
		Exam exam = null;
		if(data1.isPresent()) {
			exam = data1.get();
		}
		newMultipleChoiceQuestion.setExam(exam);
		newMultipleChoiceQuestion.setType("MultipleChoice");
		
		return mutiRepo.save(newMultipleChoiceQuestion);
		
	}
	@PutMapping("/api/exam/{examId}/update/multi")
	public MultipleChoiceQuestion updateMultipleChoiceQuestion(@PathVariable("examId") int id, @RequestBody MultipleChoiceQuestion newQuestion) {
		System.out.println("in here update true false");
		Optional<MultipleChoiceQuestion> data = mutiRepo.findById(id);
//		System.out.println("assignmentId:"+id);
		if(data.isPresent()) {
			MultipleChoiceQuestion question = data.get();
			question.setDescription(newQuestion.getDescription());
			question.setPoints(newQuestion.getPoints());
//			assignment.setText(newAssignment.getText());
			question.setTitle(newQuestion.getTitle());
			question.setOptions(newQuestion.getOptions());
			question.setCorrectOption(newQuestion.getCorrectOption());
//			module.setCourse(newModule.getCourse());
			
			mutiRepo.save(question);	
			return question;
		}	
		else 
			return null;
	}
	@DeleteMapping("/api/exam/multi/{questionId}")
	public void deleteMultipleChoiceQuestion(@PathVariable("questionId")int questionId) {
		mutiRepo.deleteById(questionId);
	}
}
