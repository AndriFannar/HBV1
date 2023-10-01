package is.hi.hbv501g.hbv1.Persistence.Repositories;

import is.hi.hbv501g.hbv1.Persistence.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<Question> findAll();


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
     * @param listID ID of the questionnaire.
     * @return       List of Question objects that have a matching list ID.
     */
    @Query("SELECT q FROM Question q WHERE q.listID LIKE %?1%")
    List<Question> findByListIDContaining(String listID);
}
