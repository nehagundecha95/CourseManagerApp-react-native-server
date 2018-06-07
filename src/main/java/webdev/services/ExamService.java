package webdev.services;

import java.util.ArrayList;
import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import webdev.models.Assignment;
import webdev.models.EssayQuestion;
import webdev.models.Exam;
import webdev.models.FillInTheBlanksQuestion;
import webdev.models.Lesson;
import webdev.models.MultipleChoiceQuestion;
import webdev.models.Question;
import webdev.models.TrueFalseQuestion;
import webdev.models.Widget;
import webdev.repositories.EssayRepository;
import webdev.repositories.ExamRepository;
import webdev.repositories.FillInTheBlanksRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.MultipleChoicesQuestionRepository;
import webdev.repositories.TrueFalseQuestionRepository;
import webdev.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*")
public class ExamService {
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

	@GetMapping("/api/exam/{examId}/question")
	public List<Question> findAllQuestionsForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<Question> questions = exam.getQuestions();
			int count = questions.size();
			return questions;
		}
		return null;
	}
	
	@GetMapping("api/exam")
	public Iterable<Exam> findAllExams() {
		return examRepository.findAll();
	}
	
	@GetMapping("api/exam/{id}")
    public Exam findExamById(@PathVariable("id") int id){
    		Optional<Exam> data = examRepository.findById(id);
    		if(data.isPresent())
    			return data.get();
    		else 
    			return null;
    }
	
	@GetMapping("/api/lesson/{lessonId}/exam")
	public List<Widget> findAllExamsForLesson(@PathVariable("lessonId") int lessonId) {
		Optional<Lesson> data =lessonRepository.findById(lessonId);
		Iterable<Widget> widgets=null;
		if(data.isPresent()) {
			Lesson lesson = data.get();
			widgets = lesson.getWidgets();
			//need to extract assignments from widgets
			System.out.println("widgets:"+widgets);
		}
		List<Widget> exams=new ArrayList<>();
		for(Widget w: widgets) {
			System.out.println(w.getWidgetType());
			if(w.getWidgetType().equals("Exam")) {
				System.out.println("got one");
				System.out.println(w.getId());
				exams.add(w);
			}
		}
		return exams;
				
	}
	
	@PostMapping("/api/exam/{lid}/exam")
	public Widget createExam(@PathVariable("lid") int lessonId, @RequestBody Exam newExam) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		Lesson lesson=null;
		if(data.isPresent()) {
			lesson = data.get();
			
			
		}
//		return null;
		newExam.setLesson(lesson);
		newExam.setWidgetType("Exam");
		return examRepository.save(newExam);
		
	}
	
	@PutMapping("/api/exam/{examId}/update")
	public Exam updateExam(@PathVariable("examId") int id, @RequestBody Exam newExam) {
		System.out.println("in here update true false");
		Optional<Exam> data = examRepository.findById(id);
		if(data.isPresent()) {
			Exam exam = data.get();
			exam.setDescription(newExam.getDescription());
			exam.setTitle(newExam.getTitle());
			examRepository.save(exam);	
			return exam;
		}	
		else 
			return null;
	}
	
	@DeleteMapping("/api/exam/{eid}")
	public void deleteExam(@PathVariable("eid")int examId) {
		examRepository.deleteById(examId);
	}
	
	
	
	
	
	
	
	
	
}
