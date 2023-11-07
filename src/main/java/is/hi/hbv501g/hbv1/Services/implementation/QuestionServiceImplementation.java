package is.hi.hbv501g.hbv1.Services.Implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
import is.hi.hbv501g.hbv1.Persistence.Repositories.QuestionRepository;
import is.hi.hbv501g.hbv1.Services.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * Service class implementation for Question objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-29
 * @version 1.0
 */
@Service
public class QuestionServiceImplementation implements QuestionService
{
    // Variables.
    QuestionRepository questionRepository;


    /**
     * Constructs a new QuestionnaireServiceImplementation.
     *
     * @param qR QuestionRepository linked to service.
     */
    @Autowired
    public QuestionServiceImplementation(QuestionRepository qR)
    {
        this.questionRepository = qR;
    }


    /**
     * Gets the Questionnaire with the corresponding ID.
     *
     * @param questionID The ID of the question to fetch.
     * @return           Question with corresponding question ID.
     */
    @Override
    public Question getQuestionById(Long questionID)
    {
        return questionRepository.getQuestionById(questionID);
    }


    /**
     * Saves a new Question to database.
     *
     * @param question Question to save.
     * @return         Saved Question.
     */
    @Override
    public Question saveQuestion(Question question)
    {
        return questionRepository.save(question);
    }


    /**
     * Update a matching Question.
     *
     * @param questionID             ID of the question to update.
     * @param questionString         Updated question, if any.
     * @param weight                 Updated weight, if any.
     * @param questionnaire          Questionnaire to add to list, if any.
     */
    @Override
    @Transactional
    public void updateQuestion(Long questionID, String questionString, double weight,  int numberOfAnswers, Questionnaire questionnaire)
    {
        Question question = questionRepository.getQuestionById(questionID);
        if (question != null)
        {
            if (questionString != null && !Objects.equals(question.getQuestionString(), questionString)) question.setQuestionString(questionString);
            if (!Objects.equals(question.getWeight(), weight)) question.setWeight(weight);
            if (questionnaire != null && !Objects.equals(question.getQuestionnaires().get(0), questionnaire)) question.addQuestionnaire(questionnaire);
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
}
