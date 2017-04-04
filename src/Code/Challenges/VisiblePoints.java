package Code.Challenges;
/**
Used to solve a programming challenge here on www.codefights.com

Given an array of points on a 2D plane, find the maximum number of points that are visible from point (0, 0) with a viewing angle that is equal to 45 degrees.

Example:
For points = [[1, 1], [3, 1], [3, 2], [3, 3],
            [1, 3], [2, 5], [1, 5], [-1, -1],
            [-1, -2], [-2, -3], [-4, -4]]
the output should be visiblePoints(points) = 6.

This visualization shows how these 6 points can be viewed:

https://codefightsuserpics.s3.amazonaws.com/tasks/visiblePoints/img/example.png?_tm=1487272306067

Input/Output

[time limit] 3000ms (java)
[input] array.array.integer points

The array of points. For each valid i, points[i] contains exactly 2 elements and represents the ith point, where points[i][0] is the x-coordinate and points[i][1] is the y-coordinate.
It is guaranteed that there are no points located at (0, 0) and there are no two points located at the same place.

Constraints:
1 ≤ points.length ≤ 105,
1 ≤ points[i].length = 2,
-107 ≤ points[i][j] ≤ 107.

[output] integer

The maximum number of points that can be viewed from point (0, 0) with a viewing angle that is equal to 45 degrees.*
*/

/** Found these in research:
 * Got the answer from the internet. Nobody actually had a good explanation of this shit, so I stopped caring. Not really keen on studying
 * 9th grade geometry when other stuff probably more likely to come up.
 *
 */
import java.util.*;

public class VisiblePoints {

    int visiblePoints(int[][] points) {

        int inputPointsAmount = points.length;

        // Calculate the angle of every point from 0,0.
        double[] calculatedAnglesOfPoints = new double[inputPointsAmount];
        for (int i = 0; i < inputPointsAmount; i++) {
            calculatedAnglesOfPoints[i] = Math.atan2(points[i][0], points[i][1]);
        }

        Arrays.sort(calculatedAnglesOfPoints);

        int end = 0;
        int max = 0;
        int count = 0;
        double diff = Math.PI / 4;

        for (int i = 0; i < inputPointsAmount; i++) {
            while (true) {
                // This is the secret sauce. I know why you'd want to use a different angle to compare, but not why this is the comparison.
                double endAngle = end < inputPointsAmount ? calculatedAnglesOfPoints[end] : calculatedAnglesOfPoints[end - inputPointsAmount] + 2 * Math.PI;
                // I presume this is the check to see if it's within the 45 degree window since that's the "Diff" mentioned above.
                if (endAngle - calculatedAnglesOfPoints[i] <= diff) {
                    count++;
                    end++;
                } else {
                    count--;
                    break;
                }
                max = Math.max(count, max);
            }
        }
        return max;
    }

}
