package controller;

import helper.Loggable;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
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

    /**
     * json
     */
    public void index() throws Exception {
    }

    /**
     * html
     */
    public void create() throws Exception {
    }

    /**
     * json
     */
    public void store() throws Exception {
    }

    /**
     * html
     */
    public void view(String key) throws Exception {
    }

    /**
     * json
     */
    public void show(String key) throws Exception {
    }

    /**
     * json
     */
    public void update(String key) throws Exception {
    }

    /**
     * json
     */
    public void delete(String key) throws Exception {
    }
}
