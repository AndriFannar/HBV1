package is.hi.hbv501g.hbv1.Persistence.Repositories;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import is.hi.hbv501g.hbv1.Persistence.Entities.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository class for Question objects.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-29
 * @version 1.0
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>
{
    /**
     * Save a Question object to database.
     *
     * @param question must not be {@literal null}.
     * @return         Saved object.
     */
    Question save(Question question);


    /**
     * Delete Question with corresponding ID.
     *
     * @param id must not be {@literal null}.
     */
    void deleteById(Long id);


    /**
     * Find all Question objects.
     *
     * @return List of all Question objects.
     */
    List<Question> getAllByOrderByQuestionStringAsc();


    /**
     * Finds Question by unique ID.
     *
     * @param id Unique ID of the Question.
     * @return   Question with matching id, if any.
     */
    Question getQuestionById(Long id);


    /**
     * Returns list of Question with matching list IDs.
     *
     * @param questionnaire Questionnaire the question belongs to.
     * @return              List of Question objects that have a matching list ID.
     */
    List<Question> findAllByQuestionnaires(Questionnaire questionnaire);
}
