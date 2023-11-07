package is.hi.hbv501g.hbv1.Services.Implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
import is.hi.hbv501g.hbv1.Persistence.Repositories.QuestionnaireRepository;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class implementation for Questionnaire objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-29
 * @version 1.0
 */
@Service
public class QuestionnaireServiceImplementation implements QuestionnaireService
{
    // Variables.
    QuestionnaireRepository questionnaireRepository;


    /**
     * Constructs a new QuestionnaireServiceImplementation.
     *
     * @param qR QuestionnaireRepository linked to service.
     */
    @Autowired
    public QuestionnaireServiceImplementation(QuestionnaireRepository qR)
    {
        this.questionnaireRepository = qR;
    }


    /**
     * Gets the Questionnaire with the corresponding ID.
     *
     * @param questionnaireID The ID of the questionnaire to fetch.
     * @return                Questionnaire that holds Question objects with corresponding list ID.
     */
    @Override
    public Questionnaire getQuestionnaire(Long questionnaireID)
    {
        return questionnaireRepository.getQuestionnaireById(questionnaireID);
    }


    /**
     * Adds a Question to questionnaire.
     *
     * @param question Question to add.
     */
    @Override
    public void addQuestionToList(Question question, Questionnaire questionnaire)
    {
        questionnaire.addQuestion(question);
    }


    /**
     * Deletes a Questionnaire with a corresponding id.
     *
     * @param questionnaireID ID of the Questionnaire to delete.
     */
    @Override
    public void deleteQuestionnaireById(Long questionnaireID)
    {
        questionnaireRepository.deleteById(questionnaireID);
    }


    /**
     * Gets all Questionnaire objects in database.
     *
     * @return List of all Questionnaire objects in database, if any.
     */
    @Override
    public List<Questionnaire> getAllQuestionnaire()
    {
        return questionnaireRepository.findAll();
    }
}
