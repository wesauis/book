package servlet;

import controller.Controller;
import helper.Loggable;
import java.io.IOException;
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

    protected String getKey(HttpServletRequest request) {
        String path = request.getPathInfo();

        if (path == null) {
            return null;
        }

        return path.split("/")[0];
    }

    protected void sendError(HttpServletRequest request, HttpServletResponse response, int status) throws IOException {
        if (response.isCommitted()) {
            return;
        }

        response.sendRedirect(request.getContextPath() + "/errors/" + status + ".jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = getKey(request);

        try {
            if (key == null) {
                this.controller.index(request, response);
            } else {
                if (key.equals("new")) {
                    this.controller.create(request, response);
                } else if (request.getHeader("Content-Type").equalsIgnoreCase("application/json")) {
                    this.controller.show(request, response, key);
                } else {
                    this.controller.view(request, response, key);
                }
            }
        } catch (Exception err) {
            this.onError(request, response, err);
        }

        sendError(request, response, 404);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.controller.store(request, response);
        } catch (Exception err) {
            this.onError(request, response, err);
        }

        sendError(request, response, 404);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = getKey(request);

        try {
            if (key != null) {
                this.controller.update(request, response, key);
            }
        } catch (Exception err) {
            this.onError(request, response, err);
        }

        sendError(request, response, 404);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = getKey(request);

        try {
            if (key != null) {
                this.controller.delete(request, response, key);
            }
        } catch (Exception err) {
            this.onError(request, response, err);
        }

        sendError(request, response, 404);
    }

    protected void onError(HttpServletRequest request, HttpServletResponse response, Exception err) throws IOException {
        sendError(request, response, 404);
    }

}
