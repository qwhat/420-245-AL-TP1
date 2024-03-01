import java.text.DecimalFormat;

public class Configuration {
    /* Déclaration des variables */
    private String description;
    private double prixMax;
    public final int COMPOSANTS_MAX = 20;
    public Composant[] composants = new Composant[COMPOSANTS_MAX];

    /* Constructeur */
    public Configuration(String description, double prixMax, Composant[] composants) {
        this.description = description;
        this.prixMax = prixMax;

        for (int i = 0; i < this.composants.length; i++) {
            if (i < composants.length && composants[i] != null){
                this.composants[i] = composants[i].copier();
            } else {
                this.composants[i] = null;
            }

        }
    }

    /* Get et Set */
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrixMax() {
        return this.prixMax;
    }
    public void setPrixMax(double prixMax) {
        this.prixMax = prixMax;
    }
    public int getNbComposants(){ //retourne le nombre de composants non-null
        int longueurTableau = 0;
        for (int i = 0; i < this.composants.length; i++) {
            if (composants[i] != null) {
                longueurTableau++;
            }
        }
        return longueurTableau;
    }

    /* Crée une copie de la Configuration */
    public Configuration copier() {
        String description = getDescription() + " (copie)";
        double prixMax = getPrixMax();
        Composant[] composant = new Composant[this.composants.length];

        for (int i = 0; i < composant.length;i++) {
            if (this.composants[i] == null) {
                continue;
            }
            composant[i] = this.composants[i].copier();
        }

        return new Configuration(description, prixMax, composant);

    }

    /* Affiche la Configuration et ses composants */
    public void afficherTout() {
        Composant[] c = new Composant[getNbComposants()];
        int indexC = 0;

        System.out.printf(getDescription() + " (" +(getPrixMax() == 0 ? "montant illimité" : "max " + deuxDecimaux(getPrixMax()) + "$") +") : \n");
        for (Composant composant : this.composants) {
            if (composant == null) {
                continue;
            }
            c[indexC] = composant;
            indexC++;
        }
        for (int i = 0; i < c.length; i++) {
            System.out.println((i + 1) + ": " + c[i].toString() + " (" + deuxDecimaux(c[i].getPrix()) + "$)");
        }
    }

    /* Fait le prix total d'une Configuration selon un montant de taxe donné */
    public double calculerTotal(double taxe) {
        double prixTotale = 0.0;
        for (int i = 0; i < this.composants.length; i++) {
            if (this.composants[i] != null) {
                prixTotale += this.composants[i].getPrix();
            }

        }
        return prixTotale + (prixTotale * taxe);
    }

    /* Trouve le premier Composant avec la catégorie indiquée, sinon, retourne null */
    public Composant rechercher(String categorie) {

        for (int i = 0; i < composants.length;i++) {
            if (this.composants[i] == null) {
                continue;
            }
            else if (this.composants[i].categorie.equalsIgnoreCase(categorie)) {
                return this.composants[i];
            }
        }
        return null;
    }

    /* Permet d'ajouter un Composant donné à la Configuration si un composant de
    cette catégorie n'y existe pas déjà et si le prix total restera en dessous ou égal au prix maximum */
    public boolean ajouter(Composant composant) {
        if (this.getNbComposants() < COMPOSANTS_MAX && this.rechercher(composant.categorie) == null && (calculerTotal(0.0) + composant.getPrix() <= getPrixMax() || getPrixMax() == 0) ) {
            for (int i = 0; i < COMPOSANTS_MAX; i++) {
                if (this.composants[i] == null) {
                    this.composants[i] = composant;
                    System.out.println(this.composants[i].toString() + " ajouté à la configuration (total=" + deuxDecimaux(calculerTotal(0.0)) +"$)");
                    return true;
                }
            }

        } else if (this.rechercher(composant.categorie) != null) {
            System.out.println("Il y a déjà un autre composant de cette catégorie:" + this.rechercher(composant.categorie).toString());

        } else if (calculerTotal(0.0) + composant.getPrix() > getPrixMax()) {
            System.out.println("L'ajout de ce composant ferait dépasser le prix maximum: " + composant.toString());

        }

        return false;
    }

    /* Permet de retirer un Composant donné d'une Configuration s'il est présent */
    public boolean retirer(Composant composant) {
        if (this.rechercher(composant.categorie) != null) {
            for (int i = 0; i < this.composants.length; i++) {
                if (this.composants[i] == null) {
                    continue;
                }

                if (this.composants[i].estPareilQue(composant)) {
                    this.composants[i] = null;
                    System.out.println(composant.toString() + " retiré de la configuration (total=" + deuxDecimaux(calculerTotal(0.0)) +"$)");
                    return true;
                }
            }
        }

        System.out.println("Composant introuvable: " + composant.toString());
        return false;
    }

    /* Permet d'ajouter un Composant donné d'une Configuration en y enlevant le composant de la même catégorie */
    public boolean remplacer(Composant composant) {

        for (Composant c : this.composants) {
            if (c == null) {
                continue;
            }
            if (c.categorie.equalsIgnoreCase(composant.categorie)) {
                this.retirer(c);
                if (!this.ajouter(composant)){
                    this.ajouter(c);
                }
                return true;
            }
        }
        System.out.println("Il n'y a pas de composant de la catégorie " + composant.categorie);
        return false;
    }

    /* Fonction d'aide pour formatter les prix avec deux chiffres décimaux */
    private String deuxDecimaux(double montant) {
        DecimalFormat d = new DecimalFormat("0.00");
        return d.format(montant);
    }
}
