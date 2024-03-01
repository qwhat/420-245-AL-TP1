public class Composant {
    /* Déclaration des variables */
    public final String categorie;
    public String marque;
    public String nom;
    private double prix;
    private double rabais;

    /* Constructeurs */
    private Composant(String categorie, String marque, String nom, double prix, double rabais) {
        this.categorie = categorie.trim().length() <= 3 ? categorie.trim().toUpperCase() : categorie;
        this.marque = marque;
        this.nom = nom;
        this.rabais = rabais;

        if (prix >=0) {
            this.prix = prix;
        } else {
            this.prix = 0.0;
        }
    }

    public Composant(String categorie, String marque, String nom, double prix) {
        this(categorie, marque, nom, prix,0.0);
    }

    public Composant(String categorie, double prix) {
        this(categorie, "","", prix, 0.0);
    }

    public Composant(String categorie) {
        this(categorie, "","", 0.0, 0.0);
    }

    /* Get et Set */

    public double getPrix() {
        return prix - (prix * rabais);
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getRabais() {
        return this.rabais;
    }

    public void setRabais(double rabais) {
        this.rabais = rabais;
    }

    /* Crée une copie du composant */
    public Composant copier() {
        return new Composant(this.categorie, this.marque, this.nom, this.prix, this.rabais);
    }

    /* Compare le Composant c au composant actuel en ignorant les majuscules */
    public boolean estPareilQue(Composant c) {

        return this.categorie.equalsIgnoreCase(c.categorie) && this.marque.equalsIgnoreCase(c.marque) && this.nom.equalsIgnoreCase(c.nom);
    }

    /* Génère un String plus lisible d'un Composant */
    public String toString() {
        return "[" + categorie + "] " + marque + " " + nom;
    }
}
