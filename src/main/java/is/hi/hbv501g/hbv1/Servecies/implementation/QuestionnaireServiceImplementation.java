package is.hi.hbv501g.hbv1.Servecies.implementation;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
import is.hi.hbv501g.hbv1.Persistence.Entities.WaitingListRequest;
import is.hi.hbv501g.hbv1.Persistence.Repositories.QuestionRepository;
import is.hi.hbv501g.hbv1.Servecies.QuestionnaireService;
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
    public Questionnaire getQuestionnaire(int listID)
    {
        List<Question> qList = questionRepository.getQuestionByListID(listID);
        return new Questionnaire(qList);
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
    public void updateQuestion(Long questionID, String questionString, double weight, List<Integer> listID)
    {
        Question question = questionRepository.getQuestionById(questionID);
        if (question != null)
        {
            if (questionString != null && !Objects.equals(question.getQuestion(), questionString)) question.setQuestion(questionString);
            if (!Objects.equals(question.getWeight(), weight)) question.setWeight(weight);
            if (listID != null && !Objects.equals(question.getListID(), listID)) question.setListID(listID);
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
}
