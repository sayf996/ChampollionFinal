package champollion;

import static java.lang.Math.round;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Enseignant extends Personne {

    private final Set<Intervention> myIntervention;
    private final Map<UE, Map<TypeIntervention, Integer>> myHashMap = new HashMap<>();
    private float roundedTotalHours = 0;
    private float roundedplanTotales = 0;
    private final ServicePrevu myService = new ServicePrevu(0,0,0);
    
    
    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

    public Enseignant(String nom, String email) {
        super(nom, email);
        this.myIntervention = new HashSet<>();
        
    
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public float heuresPrevues() {
        // TODO: Implémenter cette méthode
        myHashMap.keySet().forEach(mapkey -> {
            roundedTotalHours += heuresPrevuesPourUE(mapkey);
        });
        return (int) roundedTotalHours;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public float heuresPrevuesPourUE(UE ue) {
       HashMap<TypeIntervention, Integer> myHours = (HashMap<TypeIntervention, Integer>) myHashMap.get(ue);
            float heuresCM = myHours.get(TypeIntervention.CM) * 1.5f;
            int heuresTD = myHours.get(TypeIntervention.TD);
            float heuresTP = myHours.get(TypeIntervention.TP) * 0.75f;
            int roundedHours = round(heuresCM + heuresTD + heuresTP);
            
            return roundedHours;
        
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        
        myService.setVolumeCM(myService.getVolumeCM() + volumeCM);
        myService.setVolumeTD(myService.getVolumeTD() + volumeTD);
        myService.setVolumeTP(myService.getVolumeTP() + volumeTP);
        
            
        if (myHashMap.get(ue) != null) {
            
            HashMap<TypeIntervention, Integer> myHours = (HashMap<TypeIntervention, Integer>) myHashMap.get(ue);
                myHours.put(TypeIntervention.CM, myHours.get(TypeIntervention.CM) + volumeCM);
                myHours.put(TypeIntervention.TD, myHours.get(TypeIntervention.TD) + volumeTD);
                myHours.put(TypeIntervention.TP, myHours.get(TypeIntervention.TP) + volumeTP);
                myHashMap.put(ue, myHours);
                
            }
            else {
            HashMap<TypeIntervention, Integer> myHours = new HashMap<>();
                myHours.put(TypeIntervention.TP, volumeTP);
                myHours.put(TypeIntervention.TD, volumeTD);
                myHours.put(TypeIntervention.CM, volumeCM);
                myHashMap.put(ue, myHours);        
            }
        
    }
    public boolean enSousService() {
        return heuresPrevues() < heuresPlanifee();
    }
    public int heuresPlanifee() {
        myIntervention.forEach(e -> {
            roundedplanTotales += e.getDuree();
        });
        return (int) roundedplanTotales;
    }
    public void ajouteIntervention(Intervention e) {
        myIntervention.add(e);
    }

}
