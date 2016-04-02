/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package car.evaluation;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author USER
 */
public class PredictionModel {
    
    public void predictTheCar(String buying,String maint,int doors,int persons,String lug_boot,String safety,JLabel prediction,JLabel probability) throws SQLException{
        
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url="jdbc:oracle:thin:@localhost:1521:orcl";

        Properties prop=new Properties();
        prop.setProperty("user", "dmuser");
        prop.setProperty("password", "pabodha91");
        java.sql.Connection conn= DriverManager.getConnection(url,prop);

        String sql ="select prediction(CLAS_NB_1_4 using '"+buying+"' as BUYING, '"+maint+"' as MAINT, '"+doors+"' as DOORS, '"+persons+"' as PERSONS, '"+lug_boot+"' as LUG_BOOT, '"+safety+"' as SAFETY), prediction_probability(CLAS_NB_1_4 using '"+buying+"' as BUYING, '"+maint+"' as MAINT, '"+doors+"' as DOORS, '"+persons+"' as PERSONS, '"+lug_boot+"' as LUG_BOOT, '"+safety+"' as SAFETY) from CAR_RESULTS where rownum < 2";
        PreparedStatement preStatement = conn.prepareStatement(sql);

        ResultSet result = preStatement.executeQuery();
        while (result.next()){
            int preditResult = result.getShort(1);
            double prob = result.getDouble(2);
            String predict = Integer.toString(preditResult);
            String probabi = Double.toString(prob);
            prediction.setText(predict);
            probability.setText(probabi);
            System.out.println("Result : "+preditResult);
            System.out.println("Probability : "+prob);
        };
        
    }
}
