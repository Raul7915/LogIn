import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class ManagerCursuri {
    ArrayList<Curs> cursuri = new ArrayList<>();
    ArrayList<Student> studenti = new ArrayList<>();
    ArrayList<Profesor> profesori = new ArrayList<>();
    ArrayList<Student> studentiXML = new ArrayList<>();
    ArrayList<Profesor> profesoriXML = new ArrayList<>();

    private boolean readFromXML = false;


    public ManagerCursuri() throws ParserConfigurationException {
        cursuri.addAll(List.of(StaticDatabase.cursuri));
        if (readFromXML == false)                   // incarca datele din StaticDatabase
        {
            studenti.addAll(List.of(StaticDatabase.studenti));
            profesori.addAll(List.of(StaticDatabase.profesori));
        } else {                                   // inacarca datele din fisierele XML pentru studenti si profesori
            ReadXMLfileStudents();
            studenti.addAll(studentiXML);
            ReadXMLfileTeachers();
            profesori.addAll(profesoriXML);
        }
    }

    int indexOf(Curs curs) {
        for (int index = 0; index < cursuri.size(); index++) {
            if (cursuri.get(index).nume == curs.nume) {
                return index;
            }
        }
        return -1;
    }

    void replaceStudent(Curs fromCurs, Student oldStudent, Student withNewStudent) {
        Curs curs = cursuri.get(this.indexOf(fromCurs));
        curs.studenti[curs.indexOf(oldStudent)] = withNewStudent;
    }

    public void modifica(Curs cursExistent, Curs newCurs) {
        int index = this.indexOf(cursExistent);
        this.cursuri.set(index, newCurs);
    }

    public void afiseazaCursuriLaConsola() {
        for (Curs c : cursuri) {

            System.out.println(c);
            System.out.println("__________________________________________________________________________");
        }

    }

    public void reportNote() {
        for (Curs c : cursuri) {
            c.reportNote();
        }
    }

    public void reportStudents(Curs curs) {
        this.cursuri.get(indexOf(curs)).reportStudents();

    }

    public void reportNote(Curs curs) {
        this.cursuri.get(indexOf(curs)).reportNote();

    }

    public float reportMediaNotelor(Profesor profesor) {
        float mediaNotelor = 0.0f;
        int count = 0;
        for (Curs c : cursuri) {
            if (c.profu.nume.equals(profesor.nume) && c.profu.prenume.equals(profesor.prenume)) {
                mediaNotelor += c.mediaNotelor();
                count++;
            }
        }
        return mediaNotelor / count;
    }

    public void AddStudentDupaAn() {

        for (Curs curs : cursuri)
            for (Student student : studenti)
                if (curs.an == student.grupa % 10)
                    curs.addStudent(student);
    }

    public int NotaRandom() {
        Random rn = new Random();
        int answer = rn.nextInt(10) + 1;
        return (int) answer;
    }

    public void noteStudent(Student student) {
        System.out.println("\n Notele tale la cursuri sunt: ");
        for (Curs curs : cursuri) {
            for (int k = 0; k < curs.note.length; k++)
                if (student.grupa % 10 == curs.an) {
                    System.out.println(" | " + curs.nume + " | " + "- " + curs.note[k]);
                    break;
                }
        }
    }

    public void AdaugaNota() {
        for (Curs curs : cursuri) {
            for (Student student : studenti) {

                for (int i = 0; i < cursuri.size(); i++)
                    for (int j = 0; j < studenti.size(); j++)
                        if (student.grupa % 10 == curs.an)
                            curs.noteaza(student, NotaRandom());

            }
        }
    }

    public float MediaStudent(Student student) {
        System.out.println("\n Media ta finala este: ");
        float media;
        float nr_note = 0;
        int suma = 0;
        for (Curs curs : cursuri) {
            for (int k = 0; k < curs.note.length; k++) {
                if (student.grupa % 10 == curs.an) {
                    suma += curs.note[k];
                    nr_note++;
                    break;
                }
            }
        }
        media = suma / nr_note;
        return media;

    }

    public void Restante(Student student) {
        System.out.println("\n Ai restanta la: ");
        for (Curs curs : cursuri) {
            for (int k = 0; k < curs.note.length; k++)
                if (student.grupa % 10 == curs.an) {
                    if (curs.note[k] < 5) {
                        System.out.println("| " + curs.nume + " | ");
                    }
                    break;
                }
        }
    }

    public void noteProfesor(Profesor profesor) {
        System.out.println("\nNotele la cursuri:");
        int nr_note = 0;
        try {
            for (Curs curs : cursuri) {
                int k = 0;
                while (k < curs.note.length) {
                    if (Objects.equals(curs.profu.nume, profesor.nume)) {
                        System.out.println(curs.nume + " " + curs.note[k]);
                        nr_note++;
                    }
                    k++;
                }
            }
            if (nr_note == 0)
                throw new Exception("Nu preda cursuri!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void medieNoteLaCurs(Profesor profesor) {
        float media;
        float suma = 0;
        int nr_note = 0;
        for (Curs curs : cursuri) {
            for (int k = 0; k < curs.note.length; k++)
                if (Objects.equals(curs.profu.nume, profesor.nume)) {
                    suma = suma + curs.note[k];
                    nr_note++;

                }

        }
        media = suma / nr_note;
        System.out.println("\nMedia totala pe cursuri este: " + media);
    }

    public void profLaCurs(Profesor profesor) {
        System.out.print("\nPreda urmatoarele cursuri: ");
        for (Curs curs : cursuri)
            if (Objects.equals(curs.profu.nume, profesor.nume))
                System.out.print("| " + curs.nume + " | ");
        System.out.println("\n");

    }

    public void sortareAn_Grupa() {
        int x = 1;

        for (int k = 0; k < studenti.size(); k++) {
            if(x < 5) {
                System.out.println("\n ----- Studentii de anul " + x + " sunt: -----");

                for (Student student : studenti) {
                    if (student.grupa % 10 == x)
                    { if((student.grupa / 10) % 10 == 1)
                    {
                        System.out.print(" * Grupa 1: ");
                        System.out.println("| " + student.nume + " " + student.prenume + " | ");
                    }
                    else if ((student.grupa / 10) % 10 == 2) {
                        System.out.print(" * Grupa 2: ");
                        System.out.println("| " + student.nume + " " + student.prenume + " | ");
                    }

                    }
                }
                System.out.println("\n");
            }
            x++;
        }
    }

    public void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue... ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void CLOSE_CONTINUE() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" *** Do you want to continue? *** \n    [ Y / N ] \n ");
        String ans = scanner.next();
        String close = "n";
        String contin = "y";
        if(close.equalsIgnoreCase(ans))
        {
            System.out.println("\n\n  The program has closed!");
            System.exit(0);
        }
        else if (contin.equalsIgnoreCase(ans))
            promptEnterKey();

        else
             {
            System.out.println("\n  Error...");
                 System.out.println(" The program will close automatically!!");
            System.exit(0);
             }

    }



    public void LogInAdmin() {

        boolean LOOPadmin = true;

        System.out.println("\n***LOGIN SUCCESSFUL! You have successfully signed into your account.***");
        System.out.println(" LogIn details: ***ADMIN***");

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("""
                    \n*** Admin's options: ***
                    1) Genereaza baza de fisiere pentru studenti.
                    2) Adauga student.
                    3) Genereaza baza de date pentru profesori.
                    4) Adauga profesor.
                    5) Grupeaza studentii dupa grupa.
                    6) Ordoneaza automat studentii dupa grupa . 
                    7) Sorteaza cursurile.
                    8)       EXIT.      """);
            while (LOOPadmin) {
                System.out.print("\n *Optiunea ta:  ");
                String x = scanner.next();
                switch (x) {
                    case "1" -> CreateXMLfileStudents();
                    case "2" -> addStudentToFile();
                    case "3" -> CreateXMLfileTeachers();
                    case "4" -> addProfesorToFile();
                    case "5" -> sortareAn_Grupa();
                    case "6" -> sortareStudenti();
                    case "7" -> {sortareCurs(); LOOPadmin = false; }
                    case "8" -> LOOPadmin = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }



    public void LogIn() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert username: ");
        String username = scanner.next();
        System.out.println("Insert password: ");
        String password = scanner.next();

        boolean Verificare = false;
        boolean LOOP = true;


        for (Student student : studenti) {
            String usernameCheck = student.nume + "." + student.prenume;
            String passwordCheck = student.nume.toLowerCase() + student.prenume.toLowerCase();

            if (usernameCheck.equals(username) && passwordCheck.equals(password)) {
                Verificare = true;

                System.out.println("\n***LOGIN SUCCESSFUL! You have successfully signed into your account.***");
                System.out.println(" LogIn details: " + username + " & " + password);
                System.out.println(" \n\n  Student: " + student.nume + " " + student.prenume + " - Grupa: " + student.grupa + " - Anul: " + (student.grupa % 10));

                Scanner scanner1 = new Scanner(System.in);
                try {
                    System.out.println("""
                                                    
                            *** Student's options: ***
                            1) Notele la cursuri.
                            2) Media finala.
                            3) Materiile restante.
                            4)   EXIT.
                                    """);

                    while (LOOP) {
                        System.out.print("\n *Optiunea ta:  ");
                        String x = scanner1.next();
                        switch (x) {
                            case "1" -> noteStudent(student);
                            case "2" -> System.out.println(MediaStudent(student));
                            case "3" -> Restante(student);
                            case "4" -> LOOP = false;
                        }
                    }
                    System.out.print("\n");
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }

            }
        }


        for (Profesor profesor : profesori) {

            String usernameCheck2 = profesor.nume + "." + profesor.prenume;
            String passwordCheck2 = profesor.nume.toLowerCase() + profesor.prenume.toLowerCase();


            if (usernameCheck2.equals(username) && passwordCheck2.equals(password)) {
                Verificare = true;
                System.out.println("\n***LOGIN SUCCESSFUL! You have successfully signed into your account.***");
                System.out.println(" LogIn details: " + username + " & " + password);
                System.out.println(" \n\n  Profesor: " + profesor.nume + " " + profesor.prenume);

                Scanner scanner1 = new Scanner(System.in);
                try {
                    System.out.println("""
                                                    
                            *** Teacher's options: ***
                            1) Cursurile la care este asociat.
                            2) Notele de la cursuri.
                            3) Media finala totala.
                            4)   EXIT.  """);

                    while (LOOP) {
                        System.out.print("\n *Optiunea ta:  ");
                        String x = scanner1.next();
                        switch (x) {
                            case "1" -> profLaCurs(profesor);
                            case "2" -> noteProfesor(profesor);
                            case "3" -> medieNoteLaCurs(profesor);
                            case "4" -> LOOP = false;
                        }
                    }
                    System.out.print("\n");
                    break;
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }

            }
        }

        if (username.equals("admin") && password.equals("admin")) {
            try {
                Verificare = true;
                LogInAdmin();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (Verificare == false)
            System.out.println("***ERROR: You have entered incorrect username or password.***");

        System.out.println("_____________________________________________________________________");
        System.out.println("\n");

        CLOSE_CONTINUE();
        LogIn();
    }






    ////////////////////////////////////////////////////

    class GrupaComparator implements Comparator<Student> {
        public int compare(Student s1, Student s2) {
            return Integer.compare(s1.grupa % 10, s2.grupa % 10);
        }
    }
    public void sortareStudenti() {
        studenti.sort(new GrupaComparator());
        for (Student s : studenti) {
            System.out.println(s);
        }
    }
    public int contorStudenti(Curs curs) {
        int cnt = 0;
        for (Curs c : cursuri) {
            for (Student student : studenti) {
                if (student.grupa % 10 == c.an && curs.an == c.an) {
                    cnt++;
                }
            }
        }
        return cnt;
    }



     class CursuriComparator implements Comparator<Curs> {
         public int compare(Curs c1, Curs c2) {
             return Integer.compare(contorStudenti(c1), contorStudenti(c2));
         }
     }




    class numeComparator implements Comparator<Curs> {
        public int compare(Curs c1, Curs c2) {
            return Integer.compare(c1.nume.compareTo(c2.nume), 0);
        }
    }
    public void Curs_SortareNC()
    {
        cursuri.sort(new numeComparator());
        afiseazaCursuriLaConsola();
    }



    class profesoriComparator implements Comparator<Curs> {
        public int compare(Curs c1, Curs c2) {
            return Integer.compare(c1.profu.nume.compareTo(c2.profu.nume), 0);
        }
    }
    public void Curs_SortareNP()
    {
        cursuri.sort(new profesoriComparator());
        afiseazaCursuriLaConsola();
    }



    class nrStdComparator implements Comparator<Curs> {
        public int compare(Curs c1, Curs c2) {
            return Integer.compare(contorStudenti(c1), contorStudenti(c2));
        }
    }
    public void Curs_SortareNRS()
    {
        cursuri.sort(new nrStdComparator());
        afiseazaCursuriLaConsola();
    }



     public void sortareCurs()
     {

         boolean  loop_sort_curs = true;

              Scanner scanner = new Scanner(System.in);
         try {
             System.out.println("""
                     *** Sortare cursuri in functie de: ***
                     1) Numele cursului.
                     2) Numele profesorului.
                     3) Numarul de studenti inscrisi la curs.
                     4)       EXIT.     """);
             while (loop_sort_curs) {
                 System.out.print("\n *Optiunea ta:  ");
                 String x = scanner.next();
                 switch (x) {
                     case "1" -> Curs_SortareNC();
                     case "2" -> Curs_SortareNP();
                     case "3" -> Curs_SortareNRS();
                     case "4" -> loop_sort_curs = false;

                     }
                 }

             if(loop_sort_curs == false)
             {
                 return;
             }

             } catch (Exception e) {
               System.out.println("Something went wrong");
                 }


         cursuri.sort(new CursuriComparator());
        afiseazaCursuriLaConsola();
     }




    ////////////////////////////////////////////////////







                ///*****************************CITIRE DIN FISIERE*********************************

    public void addProfesorToFile()
    {
        Profesor profNou = new Profesor("", "");
        Scanner citeste = new Scanner(System.in);
        System.out.print("\n Nume:  ");
        String citesteNume = citeste.nextLine();
        System.out.print("\n Prenume:  ");
        String citestePrenume = citeste.nextLine();
        profNou.setNume(citesteNume);
        profNou.setPrenume(citestePrenume);
        profesori.add(profNou);
        final String xmlFilePath = "A:\\JAVA\\FACULTATE\\Laborator\\PROFESORI.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("PROFESORI");
            document.appendChild(rootElement);

            for (Profesor i : profesori) {
                Element student = document.createElement("Profesor");
                rootElement.appendChild(student);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(i.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(i.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("\n The teacher has been successfully added! ");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }
    public void addStudentToFile()
    {
        Student studentNou = new Student("", "", 0);
        Scanner citeste = new Scanner(System.in);
        System.out.print("\n Nume:  ");
        String citesteNume = citeste.nextLine();
        System.out.print("\n Prenume:  ");
        String citestePrenume = citeste.nextLine();
        System.out.print("\n Grupa:  ");
        int citesteGrupa = citeste.nextInt();
        studentNou.setNume(citesteNume);
        studentNou.setPrenume(citestePrenume);
        studentNou.setGrupa(citesteGrupa);
        studenti.add(studentNou);
        final String xmlFilePath = "A:\\JAVA\\FACULTATE\\Laborator\\STUDENTI.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("STUDENTI");
            document.appendChild(rootElement);

            for (Student i : studenti) {
                Element student = document.createElement("Student");
                rootElement.appendChild(student);
                Attr grupa = document.createAttribute("Grupa");
                grupa.setValue(String.valueOf(i.grupa));
                student.setAttributeNode(grupa);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(i.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(i.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("\n The student has been successfully added! ");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public void CreateXMLfileStudents() {
        final String xmlFilePath = "A:\\JAVA\\FACULTATE\\Laborator\\STUDENTI.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("STUDENTI");
            document.appendChild(rootElement);

            for (Student value : studenti) {
                Element student = document.createElement("Student");
                rootElement.appendChild(student);
                Attr grupa = document.createAttribute("Grupa");
                grupa.setValue(String.valueOf(value.grupa));
                student.setAttributeNode(grupa);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(value.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(value.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }
    public void CreateXMLfileTeachers() {
        final String xmlFilePath = "A:\\JAVA\\FACULTATE\\Laborator\\PROFESORI.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("PROFESORI");
            document.appendChild(rootElement);

            for (Profesor profesor : profesori) {
                Element student = document.createElement("Profesor");
                rootElement.appendChild(student);
                Element nume = document.createElement("Nume");
                nume.appendChild(document.createTextNode(profesor.nume));
                student.appendChild(nume);
                Element prenume = document.createElement("Prenume");
                prenume.appendChild(document.createTextNode(profesor.prenume));
                student.appendChild(prenume);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public void ReadXMLfileStudents() throws ParserConfigurationException {
        final String FILENAME = "A:\\JAVA\\FACULTATE\\Laborator\\STUDENTI.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("Student");
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String nume = element.getElementsByTagName("Nume").item(0).getTextContent();
                    String prenume = element.getElementsByTagName("Prenume").item(0).getTextContent();
                    int grupa = Integer.parseInt(element.getAttribute("Grupa"));
                    Student studentNou = new Student("", "", 0);
                    studentNou.setNume(nume);
                    studentNou.setPrenume(prenume);
                    studentNou.setGrupa(grupa);
                    studentiXML.add(studentNou);
                }
            }
        } catch (IOException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void ReadXMLfileTeachers() throws ParserConfigurationException {              // CITIRE PROFESORI DIN FISIER XML
        final String FILENAME = "A:\\JAVA\\FACULTATE\\Laborator\\PROFESORI.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("Profesor");
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String nume = element.getElementsByTagName("Nume").item(0).getTextContent();
                    String prenume = element.getElementsByTagName("Prenume").item(0).getTextContent();
                    Profesor profNou = new Profesor("", "");
                    profNou.setNume(nume);
                    profNou.setPrenume(prenume);
                    profesoriXML.add(profNou);
                }
            }
        } catch (IOException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }


}