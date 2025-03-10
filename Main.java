import java.util.*;

class UserProfile {
    String name;
    int age;
    String dietaryPreference;
    private int calorieRequirement;
    private List<String> dietaryRestrictions;

    public UserProfile(String name, int age, String dietaryPreference, int calorieRequirement, List<String> dietaryRestrictions) {
        this.name = name;
        this.age = age;
        this.dietaryPreference = dietaryPreference;
        this.calorieRequirement = calorieRequirement;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public int getCalorieRequirement() {
        return calorieRequirement;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void displayProfile() {
        System.out.println("User: " + name + ", Age: " + age + ", Preference: " + dietaryPreference + ", \nCalorie Requirement: " + calorieRequirement + ", Restrictions: " + dietaryRestrictions);
    }
}

class Meal {
    String name;
    int calories;
    int protein;
    int carbs;
    String category;

    public Meal(String name, int calories, int protein, int carbs, String category) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.category = category;
    }

    public void displayMeal() {
        System.out.println(name + " | Calories: " + calories + " | Protein: " + protein + "g | Carbs: " + carbs + "g | Category: " + category);
    }
}

class MealPlan {
    List<Meal> breakfast, lunch, dinner;

    public MealPlan(List<Meal> breakfast, List<Meal> lunch, List<Meal> dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public void displayMealPlan() {
        System.out.println("\nMeal Plan:");
        System.out.println("Breakfast:");
        for (Meal meal : breakfast) meal.displayMeal();
        System.out.println("Lunch:");
        for (Meal meal : lunch) meal.displayMeal();
        System.out.println("Dinner:");
        for (Meal meal : dinner) meal.displayMeal();
    }
}

class BSTNode {
    UserProfile userProfile;
    BSTNode left, right;

    public BSTNode(UserProfile profile) {
        this.userProfile = profile;
        left = right = null;
    }
}

class DietaryBST {
    private BSTNode root;

    public void insert(UserProfile profile) {
        root = insertRec(root, profile);
    }

    private BSTNode insertRec(BSTNode root, UserProfile profile) {
        if (root == null) {
            return new BSTNode(profile);
        }
        if (profile.getCalorieRequirement() < root.userProfile.getCalorieRequirement()) {
            root.left = insertRec(root.left, profile);
        } else if (profile.getCalorieRequirement() > root.userProfile.getCalorieRequirement()) {
            root.right = insertRec(root.right, profile);
        }
        return root;
    }

    public void inorderTraversal() {
        inorderRec(root);
    }

    private void inorderRec(BSTNode root) {
        if (root != null) {
            inorderRec(root.left);
            root.userProfile.displayProfile();
            inorderRec(root.right);
        }
    }
}

class MealPlanner {
    private static List<Meal> mealDatabase = new ArrayList<>(Arrays.asList(
        new Meal("Salad", 300, 10, 40, "Vegan"),
        new Meal("Grilled Chicken", 500, 40, 10, "Non-Vegetarian"),
        new Meal("Pasta", 600, 20, 80, "Vegetarian"),
        new Meal("Oatmeal", 350, 15, 50, "Vegan"),
        new Meal("Smoothie", 250, 5, 30, "Vegan"),
        new Meal("Rice and Beans", 450, 15, 60, "Vegetarian"),
        new Meal("Egg Scramble", 400, 25, 20, "Vegetarian")
    ));

    public static List<Meal> getMatchingMeals(UserProfile profile) {
        List<Meal> matchedMeals = new ArrayList<>();
        for (Meal meal : mealDatabase) {
            if (meal.calories <= profile.getCalorieRequirement() &&
                profile.getDietaryRestrictions().contains(meal.category)) {
                matchedMeals.add(meal);
            }
        }
        return matchedMeals;
    }

    public static MealPlan generateMealPlan(UserProfile profile) {
        List<Meal> matchedMeals = getMatchingMeals(profile);
        List<Meal> breakfast = new ArrayList<>();
        List<Meal> lunch = new ArrayList<>();
        List<Meal> dinner = new ArrayList<>();
        
        for (Meal meal : matchedMeals) {
            if (breakfast.isEmpty()) {
                breakfast.add(meal);
            } else if (lunch.isEmpty()) {
                lunch.add(meal);
            } else if (dinner.isEmpty()) {
                dinner.add(meal);
            }
            if (!breakfast.isEmpty() && !lunch.isEmpty() && !dinner.isEmpty()) break;
        }
        
        return new MealPlan(breakfast, lunch, dinner);
    }
}

public class Main {
    public static void main(String[] args) {
        List<String> diet1 = Arrays.asList("Vegan");
        List<String> diet2 = Arrays.asList("Vegetarian");
        List<String> diet3 = Arrays.asList("Vegan", "Vegetarian");
        
        UserProfile user1 = new UserProfile("Sathwik", 25, "Vegan", 1000, diet1);
        UserProfile user2 = new UserProfile("Dheeraj", 22, "Vegetarian", 1500, diet2);
        UserProfile user3 = new UserProfile("Deekshith", 28, "Vegan", 1800, diet3);
        
        DietaryBST bst = new DietaryBST();
        bst.insert(user1);
        bst.insert(user2);
        bst.insert(user3);
        
        System.out.println("Inorder Traversal of Profiles:");
        bst.inorderTraversal();
        
        System.out.println("\nMeal Plan for Sathwik:");
        MealPlanner.generateMealPlan(user1).displayMealPlan();
        
        System.out.println("\nMeal Plan for Dheeraj:");
        MealPlanner.generateMealPlan(user2).displayMealPlan();
        
        System.out.println("\nMeal Plan for Deekshith:");
        MealPlanner.generateMealPlan(user3).displayMealPlan();
    }
}