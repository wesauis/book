package servlet;

import controller.Controller;
import mixins.Loggable;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wesley.isotton
 * @param <C> Controller
 */
public abstract class Servlet<C extends Controller> extends HttpServlet implements Loggable {

    private final C controller;

    public Servlet(C controller) {
        this.controller = controller;
    }

    protected void sendError(HttpServletRequest request, HttpServletResponse response, int status) throws IOException {
        if (response.isCommitted()) {
            return;
        }

        response.sendRedirect(request.getContextPath() + "/errors/" + status + ".jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.controller.begin(request, response);

        String method = request.getParameter("__method");
        if (method != null) {
            switch (method) {
                case "POST":
                    doPost(request, response);
                    return;
                case "PUT":
                    doPut(request, response);
                    return;
                case "DELETE":
                    doDelete(request, response);
                    return;
            }
        }
        
        String action = request.getParameter("action");

        try {
            if (action == null) {
                this.controller.index();
            } else {
                if ("new".equals(action)) {
                    this.controller.create();
                } else if ("edit".equals(action)) {
                    this.controller.view();
                }
            }
        } catch (IOException ioerr) {
            throw ioerr;
        } catch (Exception err) {
            this.controller.end();
            this.onError(request, response, err);
        }

        this.controller.end();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.controller.begin(request, response);

        String method = request.getParameter("__method");
        if (method != null) {
            switch (method) {
                case "POST":
                    doPost(request, response);
                    return;
                case "PUT":
                    doPut(request, response);
                    return;
                case "DELETE":
                    doDelete(request, response);
                    return;
            }
        }
        
        try {
            this.controller.store();
        } catch (IOException ioerr) {
            throw ioerr;
        } catch (Exception err) {
            this.controller.end();
            this.onError(request, response, err);
        }

        this.controller.end();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.controller.begin(request, response);

        try {
            this.controller.update();
        } catch (IOException ioerr) {
            throw ioerr;
        } catch (Exception err) {
            this.controller.end();
            this.onError(request, response, err);
        }

        this.controller.end();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.controller.begin(request, response);

        try {
            this.controller.delete();
        } catch (IOException ioerr) {
            throw ioerr;
        } catch (Exception err) {
            this.controller.end();
            this.onError(request, response, err);
        }

        this.controller.end();
    }

    protected void onError(HttpServletRequest request, HttpServletResponse response, Exception err) throws IOException {
        logger().log(Level.SEVERE, null, err);
    }

}
