package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.EssayQuestion;

public interface EssayRepository extends CrudRepository<EssayQuestion, Integer>{

}
