import java.lang.reflect.Array;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        String[] bookCollection = BookRecUtils.readFileToArray("books.txt");
        // Reads the book names from a file and stores them in an array called bookCollection
        int [][] ratingsArray = BookRecUtils.readRatingsFile("ratings.txt");
        // Reads the ratings from a file and stores them in a 2D array called ratingsArray.   // System.out.println(Arrays.deepToString(ratingsArray));
        Scanner myObj = new Scanner(System.in);
        int[] userRatings = new int[bookCollection.length];
        // Creates an integer array called userRatings with the same length as bookCollection to store user's ratings
        for(int i =0; i< bookCollection.length;i++){
            // for loop that iterates over bookCollection, prompts user to rate each book, adds users input to userRating Array, while loop ensures valid input
            String prompt = ("Rate the following book (between 1-5):  "+ bookCollection[i]+ " , enter -1 if you have not read it ");
            System.out.println(prompt);
            int answer = myObj.nextInt();
            while (answer > 5 || answer < -1 || answer == 0) {
                System.out.println("Invalid input: " + prompt);
                answer = myObj.nextInt();
            }
                userRatings[i]= answer;
        }
        double [] similiarityScores = BookRecUtils.calculateSimilarity(ratingsArray, userRatings);
        // Calculates the similarity scores between the user's ratings and other people's ratings
       // System.out.println(Arrays.toString(similiarityScores)); Prints the similarity scores array
        for(int i=0;i<similiarityScores.length;i++){
            System.out.println("you and rater "+(i+1)+" had a similarity score of "+similiarityScores[i]);
        }
        //  displays similarity score user had with each rater
        double [] booksRecRating = BookRecUtils.getrecommendedRatings(ratingsArray,userRatings);
        // Calculates the recommended ratings for each book based on the user's ratings and other people's ratings
        // System.out.println(Arrays.toString(booksRecRating)); Prints the recommended ratings array for each book
        for(int i=0;i<bookCollection.length;i++){
            System.out.println(bookCollection[i]+" had a recommendation rating of: "+ booksRecRating[i]);
        }
       //
        String Recommendation = BookRecUtils.DisplayHighlyRecBook(userRatings,ratingsArray,bookCollection);
        // Retrieves the name of the top book recommendation based on the user's ratings and other people's ratings

        System.out.println(Recommendation);
        // Prints the recommendation of the top book




    }





    }

