package servlet;

import controller.Controller;
import helper.Loggable;
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

    protected String getKey(HttpServletRequest request) {
        String path = request.getPathInfo();

        if (path == null) {
            return null;
        }

        String[] parts = path.split("/");
        return parts.length > 0 ? parts[0] : null;
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
        String key = getKey(request);

        try {
            if (key == null) {
                this.controller.index();
            } else {
                if (key.equals("new")) {
                    this.controller.create();
                } else if ("application/json".equalsIgnoreCase(request.getHeader("Content-Type"))) {
                    this.controller.show(key);
                } else {
                    this.controller.view(key);
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
        String key = getKey(request);

        try {
            if (key != null) {
                this.controller.update(key);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.controller.begin(request, response);
        String key = getKey(request);

        try {
            if (key != null) {
                this.controller.delete(key);
            }
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
