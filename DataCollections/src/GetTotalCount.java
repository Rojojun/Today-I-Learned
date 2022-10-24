import java.util.Scanner;

public class GetTotalCount {
    /*
    int totalCnt;
    int numOfRows;
    */
    int remains;
    int totalPages;
    public int GetTotalCount(int totalCnt, int numOfRows) {
/*        Scanner scanner = new Scanner(System.in);
        totalCnt = scanner.nextInt();
        numOfRows = scanner.nextInt();*/
        remains = totalCnt % numOfRows;

        totalPages = totalCnt / numOfRows;
        if (remains != 0) {
            totalPages++;
        }
        System.out.println(totalPages);
        return totalPages;
    }
}
