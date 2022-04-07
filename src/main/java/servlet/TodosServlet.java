package servlet;

import controller.TodosController;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author wesley.isotton
 */
@WebServlet(name = "Todos", urlPatterns = {"/todos/*"})
public class TodosServlet extends Servlet<TodosController> {
    
    public TodosServlet() {
        super(new TodosController());
    }
    
}
