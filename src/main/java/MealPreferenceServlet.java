import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MealPreferenceServlet")
public class MealPreferenceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Define a map to store meal categories and associated meals
    private Map<String, List<Meal>> categoryToMeals = new HashMap<>();

    public MealPreferenceServlet() {
        super();

        // Initialize meal data (you can fetch this from a database or external source)
        initializeMealData();
    }

    private void initializeMealData() {
        // Populate the map with hardcoded data (for demonstration)
        List<String> tacoIngredients = Arrays.asList("Tortilla", "Ground beef", "Lettuce", "Tomato", "Cheese", "Salsa");
        String tacoRecipe = "1. Cook ground beef. 2. Warm tortillas. 3. Assemble ingredients.";
        Meal taco = new Meal("Taco", tacoIngredients, tacoRecipe);

        List<String> pizzaIngredients = Arrays.asList("Dough", "Tomato sauce", "Cheese", "Pepperoni", "Mushrooms");
        String pizzaRecipe = "1. Roll out dough. 2. Spread sauce. 3. Add toppings. 4. Bake.";
        Meal pizza = new Meal("Pizza", pizzaIngredients, pizzaRecipe);

        // Add meals to categories
        categoryToMeals.put("Mexican", Arrays.asList(taco));
        categoryToMeals.put("Italian", Arrays.asList(pizza));
        // Add more categories and meals as needed...
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Direct GET requests to the HTML page
        response.sendRedirect("meal_preference.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Retrieve selected meal categories from the request
        String[] selectedCategories = request.getParameterValues("category");

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Meal Preferences</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: white; margin: 0; padding: 20px; }");
        out.println(".container { max-width: 800px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        out.println("h1 { text-align: center; }");
        out.println(".category { margin-bottom: 20px; }");
        out.println(".meal { margin-bottom: 10px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");
        out.println("<h1>Selected Meal Preferences</h1>");

        if (selectedCategories != null && selectedCategories.length > 0) {
            for (String category : selectedCategories) {
                List<Meal> meals = categoryToMeals.get(category);
                if (meals != null && !meals.isEmpty()) {
                    out.println("<div class='category'>");
                    out.println("<h2>" + category + "</h2>");
                    for (Meal meal : meals) {
                        out.println("<div class='meal'>");
                        out.println("<h3>" + meal.getName() + "</h3>");
                        out.println("<p><strong>Ingredients:</strong> " + meal.getIngredients() + "</p>");
                        out.println("<p><strong>Recipe:</strong> " + meal.getRecipe() + "</p>");
                        out.println("</div>");
                    }
                    out.println("</div>");
                }
            }
        } else {
            out.println("<p>No categories selected</p>");
        }

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}
