package controller;

import helper.DB;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wesley.isotton
 */
public class TodosController extends Controller {
    public static PrintWriter out;

    @Override
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        
        DB db = DB.instance();
        out.println(db);
        out.println(db.connection());
        
        out.write("YEY");
        out.flush();
    }

}
