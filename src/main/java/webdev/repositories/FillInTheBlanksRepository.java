package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.FillInTheBlanksQuestion;
import webdev.models.MultipleChoiceQuestion;

public interface FillInTheBlanksRepository extends CrudRepository<FillInTheBlanksQuestion, Integer>{

}
