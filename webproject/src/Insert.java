
/**
 * @file InsertSwapp.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Insert")
public class Insert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Insert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String stockName = request.getParameter("stockName");
      String buyPrice = request.getParameter("buyPrice");
      String quantity = request.getParameter("quantity");
      String sellPrice = request.getParameter("sellPrice"); 
      String estReturn = request.getParameter("estReturn");
      
      double bPrice = Double.parseDouble(buyPrice);

      int count = Integer.parseInt(quantity);

      double sPrice = Double.parseDouble(sellPrice);
      
      Connection connection = null;
      String insertSql = " INSERT INTO testTable (id, NAME, BUY_PRICE, QUANTITY, SELL_PRICE, EST_RETURN) values (default, ?, ?, ?, ?, ?)";

      try {
         DatabaseConnection.getDBConnection();
         connection = DatabaseConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, stockName);
         preparedStmt.setString(2, buyPrice);
         preparedStmt.setString(3, quantity);
         preparedStmt.setString(4, sellPrice);
         preparedStmt.setString(5, String.valueOf((sPrice - bPrice)));
                  
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Stock Data to Database";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Stock name</b>: " + stockName + "\n" + //
            "  <li><b>Purchase Price</b>: " + buyPrice + "\n" + //
            "  <li><b>Quantity</b>: " + quantity + "\n" + //
            "  <li><b>Sell Price</b>: " + sellPrice + "\n" + //
            "  <li><b>Estimated Return of Investment</b>: " + (sPrice - bPrice) + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject/insert.html>Insert Another Stock</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
