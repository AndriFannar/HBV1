package is.hi.hbv501g.hbv1.persistence.repositories;

import is.hi.hbv501g.hbv1.persistence.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
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
    @NonNull
    Question save(@NonNull Question question);


    /**
     * Delete Question with corresponding ID.
     *
     * @param id must not be {@literal null}.
     */
    void deleteById(@NonNull Long id);


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
}
