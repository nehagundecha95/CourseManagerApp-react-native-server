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
import org.springframework.web.bind.annotation.RestController;


import webdev.models.Assignment;
import webdev.models.Exam;
import webdev.models.Lesson;
import webdev.models.Widget;
import webdev.repositories.AssignmentRepository;
import webdev.repositories.LessonRepository;
import webdev.repositories.WidgetRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssignmentService {
	@Autowired
	AssignmentRepository assignmentRepository;
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	WidgetRepository widgetRepository;
	
	@GetMapping("api/assignment")
	public Iterable<Assignment> findAllAssignments() {
			
		return assignmentRepository.findAll();
	}
	
	@GetMapping("api/assignment/{id}")
    public Widget findAssignmentById(@PathVariable("id") int id){
    		Optional<Assignment> data = assignmentRepository.findById(id);
    		if(data.isPresent())
    			return data.get();
    		else 
    			return null;
    }
	
	@GetMapping("api/lesson/{lid}/assignment")
	public Iterable<Widget> findAllAssignmentsForLesson(@PathVariable("lid") int lid) {
		
		Optional<Lesson> data =lessonRepository.findById(lid);
		Iterable<Widget> widgets=null;
		if(data.isPresent()) {
			Lesson lesson = data.get();
			widgets = lesson.getWidgets();
			//need to extract assignments from widgets
			System.out.println("widgets:"+widgets);
		}
		List<Widget> assignments=new ArrayList<>();
		for(Widget w: widgets) {
			System.out.println(w.getWidgetType());
			if(w.getWidgetType().equals("Assignment")) {
				System.out.println("got one");
				System.out.println(w.getId());
				assignments.add(w);
			}
		}
		return assignments;
	}
	
	@PostMapping("/api/lesson/{lid}/assignment")
	public Assignment createAssignment(@PathVariable("lid") int lid, @RequestBody Assignment newAssignment) {
		System.out.println("reached createAssignmnet");
		Lesson lesson = null;
		Optional<Lesson> data1 =lessonRepository.findById(lid);
		if(data1.isPresent()) {
			lesson = data1.get();
		
		}
		newAssignment.setWidgetType("Assignment");
		newAssignment.setLesson(lesson);
		
		return assignmentRepository.save(newAssignment);
		
	}
	
	@DeleteMapping("api/assignment/{id}")
	public void deleteAssignment(@PathVariable("id") int id) {
		assignmentRepository.deleteById(id);
	}
	
	@PutMapping("api/assignment/{assignmentId}/update")
	public Assignment updateAssignment(@PathVariable("assignmentId") int id, @RequestBody Assignment newAssignment) {
		System.out.println("in here updateAssignment");
		Optional<Assignment> data = assignmentRepository.findById(id);
		System.out.println("assignmentId:"+id);
		if(data.isPresent()) {
			Assignment assignment = data.get();
			assignment.setDescription(newAssignment.getDescription());
			assignment.setPoints(newAssignment.getPoints());
//			assignment.setText(newAssignment.getText());
			assignment.setTitle(newAssignment.getTitle());
			assignment.setWidgetType("Assignment");
//			module.setCourse(newModule.getCourse());
			
			assignmentRepository.save(assignment);	
			return assignment;
		}	
		else 
			return null;
	}

	
	
	
	
}
