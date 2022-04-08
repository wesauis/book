package model;

import exeptions.ValidationError;
import java.sql.Date;
import lombok.Data;
import mixins.Loggable;

/**
 *
 * @author w2gam
 */
@Data
public class Book implements Loggable {

    private String id;
    private String title;
    private Integer stars;
    private String summary;
    private Date release_date;
    private String author;
    private String publisher;
    private Integer page_count;

    public void validate() throws ValidationError {
        if (title == null || title.length() < 1) {
            throw new ValidationError("Título é obrigatório");
        } else if (stars != null && (stars < 0 || stars > 10)) {
            throw new ValidationError("Estrelas deve estar entre 0 e 10");
        } else if (page_count != null && page_count <= 0) {
            throw new ValidationError("O livro deve possuir ao menos 1 página");
        }
    }
}
