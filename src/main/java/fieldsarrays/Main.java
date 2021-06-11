package fieldsarrays;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        printDeclaredFieldsInfo(Employee.class,null);
        System.out.println(Employee.class.getDeclaredFields());

    }
    public static <T> void printDeclaredFieldsInfo(Class<? extends T> clazz, T instance) throws IllegalAccessException {
        for(Field field: clazz.getFields()){
            System.out.println(String.format("Filed Name :%s type %s",field.getName(),field.getType().getName()));
            System.out.println(String.format("Is Synthetic filed: %s",field.isSynthetic()));
//            System.out.println(String.format("Field value is : %s",field.get(instance)));
            System.out.println();
        }
    }

    public static class Movie extends Product{
        private static final double MINIMUM_PRICE = 10.99;

        private boolean isReleased;
        private Category category;
        private double actualPrice;

        public Movie(String name, int year, boolean isReleased, Category category, double actualPrice) {
            super(name, year);
            this.isReleased = isReleased;
            this.category = category;
            this.actualPrice = actualPrice;
        }

        //Nested class
        public class MovieStats{
            private double timesWatched;
            public MovieStats(double timesWatched){
                this.timesWatched = timesWatched;
            }
            public double getRevenue(){
                return timesWatched*actualPrice;
            }
        }
    }
    //Super
    public static class Product{
        protected String name;
        protected int year;
        protected double actualPrice;

        public Product(String name,int year) {
            this.name = name;
            this.year = year;
        }
    }
    public enum Category{
        ADVENTURE,ACTION,COMEDY
    }
}
