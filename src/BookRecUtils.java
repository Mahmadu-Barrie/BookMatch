import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BookRecUtils {

    // Method that takes in Book file and returns an Array
    public static String[] readFileToArray(String fileName) {

        //ArrayList to dynamically store the book names read from the file
        ArrayList<String> tempList = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                tempList.add(line);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // After reading all the lines, the tempList is converted to a String array using  toArray method
        String[] array = new String[tempList.size()];
        tempList.toArray(array);

        return array;
    }


    public static int[][] readRatingsFile(String fileName) {

        try {
            Scanner scanner = new Scanner(new File(fileName));

            int numRows = 30;
            int numCols = 20;

            int[][] ratingsArray = new int[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if (scanner.hasNextInt()) {
                        ratingsArray[i][j] = scanner.nextInt();
                    }
                }
            }
            scanner.close();
            return ratingsArray;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static double[] calculateSimilarity(int[][] ratings, int[] userRatings) {
        int numBooks = ratings[0].length;
        //length of the sub-array at index 0 of the ratings array, number of books
        int numPeople = ratings.length;
        // the length of the ratings array, which represents the number of people/raters
        double[] similarityScores = new double[numPeople];
        // declared array to be returned with rater's similarityscore to that of users
        for (int i = 0; i < numPeople; i++) {
            // loop to iterate over each person's ratings in the ratings array, variable i represents the index of the person
            double p1 = 0;
            double p2 = 0;
            double sum1 = 0;
            double sum2 = 0;
            double bothVal = 0;
            for (int j = 0; j < numBooks; j++) {
                // loop to iterate over each book represented by j
                if (userRatings[j] != -1 && ratings[i][j] != -1) {
                    // If both user ratings and persons/rater rating for book(s) is not equal to -1,
                    sum1 += userRatings[j] * userRatings[j];
                    // accumulates the squares of the user's rating for books they've read in sum1,
                    sum2 += ratings[i][j] * ratings[i][j];
                    // accumulates the squares of the person's  ratings for books they've read  in sum2
                    bothVal += userRatings[j] * ratings[i][j];
                    // computes product of ratings for books read by both user and person
                }
            }
            p1 = Math.sqrt(sum1);
            p2 = Math.sqrt(sum2);
            double cosineSimilarityValue = bothVal / (p1 * p2);
            // usage of  cosine similarity score between person 1 and person 2 formula of  (both / (p1 * p2))
            similarityScores[i] = cosineSimilarityValue;
            //similarity score for the current person is assigned to the corresponding index in the similarityScores array
        }
        // System.out.println(Arrays.toString(similarityScores));
        return similarityScores;
    }

    public static double[] getrecommendedRatings(int[][] ratings, int[] userRatings) {

        double[] recommendedRatings = new double[20];
        // created array to hold recommendedRatings for each book

        for (int book = 0; book < 20; book++) {
            // for loop iterates all 20 books
            double weightedSum = 0.0;
            // will store weighted sum of ratings for the book.
            double totalWeight = 0.0;
            // will store the total weight for the book
            for (int rater = 0; rater < 30; rater++) {
                // inner for loop iterates through all raters
                if (ratings[rater][book] > 0) {
                    // if rater has rated said book (not given it -1) similiarty score array is obtained
                    double[] similarity = calculateSimilarity(ratings, userRatings);
                    double weight = similarity[rater];
                    // retrieves the similarity score for the current rater hence how much it should be considered/"weighed"
                    weightedSum += ratings[rater][book] * weight; // accumulates calculated weighted rating of current book
                    totalWeight += weight; // accumulates totalWeight of current book from raters
                }

            }
            if (totalWeight > 0) { // ensures that there are ratings from at least one rater for the book
                recommendedRatings[book] = weightedSum / totalWeight; //  calculated weighted average rating is stored in the recomRatings array at the corresponding index (book)
            }
        }
        return recommendedRatings;
    }

    public static String DisplayHighlyRecBook(int[] userRatings, int[][] ratings, String[] bookNames) {
        double[] ratedBooks = getrecommendedRatings(ratings, userRatings); // retrieve array of each book's recommendedrating value

        double maxRating = Double.MIN_VALUE; // will be used to keep track of book with the highest recommended rating
        int indexOfHighestRating = -1; // default value to hold index of book with the highest rating

        for (int i = 0; i < ratedBooks.length; i++) {  // iterates through ratedBooks array
            if (userRatings[i] == -1) { // statement checks that said book was not read by user
                if (ratedBooks[i] > maxRating) { // checks if said unread book is greater than current maxRating if so maxRating is updated
                    maxRating = ratedBooks[i];
                    indexOfHighestRating = i;  // obtains index of said book which matches above criteria of being unread by user and maxrating greater than current
                }

            }

        }
        if(indexOfHighestRating != -1){ // if index is not the default value book that matched criteria above is displayed
        return " \n Our top recommendation would be: " + bookNames[indexOfHighestRating];
    }
        else{ // if index is default value means no book matched said criteria therefore message is displayed
           return " \n No unread books are recommended at the moment";
        }
}

}





















