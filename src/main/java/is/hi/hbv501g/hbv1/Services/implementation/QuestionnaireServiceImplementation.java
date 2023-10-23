package is.hi.hbv501g.hbv1.Services.implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.QuestionnaireForm;
import is.hi.hbv501g.hbv1.Persistence.Repositories.QuestionRepository;
import is.hi.hbv501g.hbv1.Services.QuestionnaireService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


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
    QuestionRepository questionRepository;


    /**
     * Constructs a new QuestionnaireServiceImplementation.
     *
     * @param qR QuestionRepository linked to service.
     */
    @Autowired
    public QuestionnaireServiceImplementation(QuestionRepository qR)
    {
        this.questionRepository = qR;
    }


    /**
     * Get a Questionnaire object.
     *
     * @param listID The ID of the questionnaire to fetch.
     * @return       Questionnaire that holds Question objects with corresponding list ID.
     */
    @Override
    public QuestionnaireForm getQuestionnaire(Integer listID)
    {
        return new QuestionnaireForm(questionRepository.findAllByListIDIs(listID));
    }


    /**
     * Saves a new Question to database.
     *
     * @param question Question to save.
     * @return         Saved Question.
     */
    @Override
    public Question addQuestion(Question question)
    {
        return questionRepository.save(question);
    }


    /**
     * Update a matching Question.
     *
     * @param questionID     ID of the question to update.
     * @param questionString Updated question, if any.
     * @param weight         Updated weight, if any.
     * @param listID         Updated Questionnaire ID, if any.
     */
    @Override
    @Transactional
    public void updateQuestion(Long questionID, String questionString, double weight,  int numberOfAnswers, List<Integer> listID)
    {
        Question question = questionRepository.getQuestionById(questionID);
        if (question != null)
        {
            if (questionString != null && !Objects.equals(question.getQuestionString(), questionString)) question.setQuestionString(questionString);
            if (!Objects.equals(question.getWeight(), weight)) question.setWeight(weight);
            if (listID != null && !Objects.equals(question.getListID(), listID)) question.setListID(listID);
            if (!Objects.equals(question.getNumberOfAnswers(), numberOfAnswers)) question.setNumberOfAnswers(numberOfAnswers);
        }
    }


    /**
     * Deletes a Question with a corresponding id.
     *
     * @param questionID ID of the Question to delete.
     */
    @Override
    public void deleteQuestionById(Long questionID)
    {
        questionRepository.deleteById(questionID);
    }


    /**
     * Gets all Question objects in database.
     *
     * @return List of all Question objects in database, if any.
     */
    @Override
    public List<Question> getQuestions()
    {
        return questionRepository.findAll();
    }


    /**
     * Gets Question object with matching ID from database.
     *
     * @return Question object with matching ID in database, if any.
     */
    public Question getQuestionById(Long questionID)
    {
        return questionRepository.getQuestionById(questionID);
    }
}
