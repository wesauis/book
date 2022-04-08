package controller;

import mixins.Loggable;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wesley.isotton
 */
public abstract class Controller implements Loggable {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public void begin(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void end() {
        request = null;
        response = null;
    }

    public HttpServletRequest request() {
        return this.request;
    }

    public HttpServletResponse response() {
        return this.response;
    }

    public PrintWriter out() throws IOException {
        return response.getWriter();
    }

    public RequestDispatcher dispatcher(String view) {
        return request.getRequestDispatcher(view);
    }
    
    public void render(String view) throws IOException, ServletException {
        RequestDispatcher rd = dispatcher("META-INF/pages/" + view);
        rd.forward(request, response);
    }
    
    public <T> void set(String key, T value) {
        request.setAttribute(key, value);
    }

    public void index() throws Exception {
    }

    public void create() throws Exception {
    }

    public void store() throws Exception {
    }

    public void view() throws Exception {
    }

    public void show() throws Exception {
    }

    public void update() throws Exception {
    }

    public void delete() throws Exception {
    }
}
