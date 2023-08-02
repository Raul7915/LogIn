public class Student {
    String nume;
    String prenume;
    int grupa;

    public Student(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }
    public Student(String nume, String prenume, int grupa)
    {
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
    }

    @Override
    public String toString() {
        return "    Student: \n " + "     Nume= " + nume + ",  Prenume= " + prenume + ",    Grupa= " + grupa;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public String getPrenume() {
        return prenume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
    public int getGrupa() {
        return grupa;
    }
    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }
}
