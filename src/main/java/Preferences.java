import java.util.ArrayList;
import java.util.List;

public class Preferences {
    private List<String> favouriteMeals;
    private List<String> dietaryRestrictions;

    public Preferences(){
        this.dietaryRestrictions = new ArrayList<>();
        this.favouriteMeals = new ArrayList<>();
    }
    public List<String> getFavouriteMeals(){
        return favouriteMeals;
    }
    public void setFavouriteMeals(List<String> favouriteMeals){
        this.favouriteMeals = favouriteMeals;
    }

    public List<String> getDietaryRestrictions(){
        return dietaryRestrictions;
    }
    public void setDietaryRestrictions(List<String> dietaryRestrictions){
        this.dietaryRestrictions = dietaryRestrictions;
    }
}
