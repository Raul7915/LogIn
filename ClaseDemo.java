import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClaseDemo {

    public static void main(String[] args) throws ParserConfigurationException {

        ManagerCursuri cursuri = new ManagerCursuri();


        cursuri.AddStudentDupaAn();
        cursuri.AdaugaNota();
       //cursuri.afiseazaCursuriLaConsola();



       cursuri.LogIn();


    }
}


