package controller;

import helper.Loggable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wesley.isotton
 */
public abstract class Controller implements Loggable {

    /** json */
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }
    
    /** html */
    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }
    
    /** json */
    public void store(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }
    
    /** html */
    public void view(HttpServletRequest request, HttpServletResponse response, String key) throws Exception {
    }
    
    /** json */
    public void show(HttpServletRequest request, HttpServletResponse response, String key) throws Exception {
    }
    
    /** json */
    public void update(HttpServletRequest request, HttpServletResponse response, String key) throws Exception {
    }
    
    /** json */
    public void delete(HttpServletRequest request, HttpServletResponse response, String key) throws Exception {
    }
}
