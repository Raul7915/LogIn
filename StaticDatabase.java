
public class StaticDatabase {


    public static Student[] studenti = new Student[] {new Student("Negoita","Andrei",4711),
                                                      new Student("Artene","Marius",4721),
                                                      new Student("Lovin","Andrei",4721),
                                                      new Student("Pintilie","Adrian",4712),
                                                      new Student("Filip","Andreea",4712),
                                                      new Student("Necula","Bianca",4722),
                                                      new Student("Codreanu","Danut",4713),
                                                      new Student("Chirila","Marcel",4713),
                                                      new Student("Lefter","Ionut",4723),
                                                      new Student("Matei","Dima",4714),
                                                      new Student("Dadacea","Alexandra",4724),
                                                      new Student("Samoila","Mirela",4724) };

                 ///ULTIMA CIFRA REPREZINTA **ANUL** ,  PENULTIMA REPREZINTA **GRUPA**

    public static Profesor[] profesori = new Profesor[] {new Profesor("Panaitescu","Anton"),
                                                         new Profesor("Danila","Mircea"),
                                                         new Profesor("Carutasu","Dan"),
                                                         new Profesor("Voinea","Andreea"),
                                                         new Profesor("Costachescu","Adina"),
                                                         new Profesor("Huiban","Marin"),
                                                         new Profesor("Angheluta","Bogdan") };

    public static Curs[] cursuri = new Curs[] {new Curs("Comunicare", "relatii interpersonale\n", profesori[0], 1),
                                               new Curs("PCLP", "programare orientata pe obiecte\n", profesori[1], 1),
                                               new Curs("Engleza", "studiu / cultura\n", profesori[2], 2),
                                               new Curs("TPSM", "calculul probabilitatilor\n", profesori[3], 2),
                                               new Curs("ALGAD", "matematica in general\n", profesori[4], 3),
                                               new Curs("Sport", "cardio si gimnastica", profesori[3], 3),
                                               new Curs("Fizica", "calculul reacţiunilor\n", profesori[5], 4),
                                               new Curs("Robotica", "dispozitive automatizate\n", profesori[6], 4),
                                               new Curs("Mecanica", "construcția de autovehicule\n", profesori[1], 4),
                                               new Curs("Germana", "Initialisierung, Geschichte, Kultur! \n", profesori[2], 2),
                                               new Curs("Gaming", "ff15\n", profesori[0], 3) };


}
