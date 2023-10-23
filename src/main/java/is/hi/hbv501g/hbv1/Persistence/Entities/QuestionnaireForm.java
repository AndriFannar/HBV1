package is.hi.hbv501g.hbv1.Persistence.Entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Patient health questionnaire.
 *
 * @author  Andri Fannar Kristjánsson, afk6@hi.is.
 * @since   2023-09-28
 * @version 1.0
 */

public class QuestionnaireForm
{
    private List<Question> questions;

    /**
     * Create a new empty questionnaire.
     */
    public QuestionnaireForm()
    {

    }

    public QuestionnaireForm(List<Question> questions)
    {
        this.questions = questions;
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
