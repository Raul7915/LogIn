public class Profesor {
    String nume;
    String prenume;
    @Override
    public String toString() {
        return "\nProfesor:\n" + "  Nume= " + nume + ",   Prenume= " + prenume + "\n\n";
    }
    public Profesor(String nume, String prenume)
    {
        this.nume = nume;
        this.prenume = prenume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
            this.prenume = prenume;
    }
}