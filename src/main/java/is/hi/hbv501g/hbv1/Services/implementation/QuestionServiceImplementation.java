package is.hi.hbv501g.hbv1.Services.implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Repositories.QuestionRepository;
import is.hi.hbv501g.hbv1.Services.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
     * @param questionRepository QuestionRepository linked to service.
     */
    @Autowired
    public QuestionServiceImplementation(QuestionRepository questionRepository)
    {
        this.questionRepository = questionRepository;
    }


    /**
     * Saves a new Question to database.
     *
     * @param question Question to save.
     * @return         Saved Question.
     */
    @Override
    public Question saveNewQuestion(Question question)
    {
        return questionRepository.save(question);
    }


    /**
     * Gets all Question objects in database.
     *
     * @return List of all Question objects in database, if any.
     */
    @Override
    public List<Question> getAllQuestions()
    {
        return questionRepository.getAllByOrderByQuestionStringAsc();
    }


    /**
     * Gets the Question with the corresponding ID.
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
     * Update a matching Question.
     *
     * @param questionID             ID of the question to update.
     * @param updatedQuestion        Updated question.
     */
    @Override
    @Transactional
    public void updateQuestion(Long questionID, Question updatedQuestion)
    {
        Question question = questionRepository.getQuestionById(questionID);
        if (question != null)
        {
            if (updatedQuestion.getQuestionString() != null) question.setQuestionString(updatedQuestion.getQuestionString());
            if (updatedQuestion.getWeight() != null) question.setWeight(updatedQuestion.getWeight());
            if (!updatedQuestion.getQuestionnaires().isEmpty()) question.setQuestionnaires(question.getQuestionnaires());
        }
    }


    /**
     * Deletes a Question with a corresponding ID.
     *
     * @param questionID ID of the Question to delete.
     */
    @Override
    public void deleteQuestionById(Long questionID)
    {
        questionRepository.deleteById(questionID);
    }
}
