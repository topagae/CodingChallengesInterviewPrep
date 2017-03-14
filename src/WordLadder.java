
/*
Used to solve a programming challenge here on www.codefights.com
Given two words, beginWord and endWord, and a wordList of approved words, find the length of the shortest transformation sequence from beginWord to endWord such that:

Only one letter can be changed at a time
Each transformed word must exist in the wordList.
Return the length of the shortest transformation sequence, or 0 if no such transformation sequence exists.

Note: beginWord does not count as a transformed word. You can assume that beginWord and endWord are not empty and are not the same.

Example

For beginWord = "hit", endWord = "cog", and wordList = ["hot", "dot", "dog", "lot", "log", "cog"], the output should be
wordLadder(beginWord, endWord, wordList) = 5.

The shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog" with a length of 5.

Input/Output

[time limit] 3000ms (java)
[input] string beginWord

Constraints:
1 ≤ beginWord.length ≤ 5.

[input] string endWord

Constraints:
endWord.length = beginWord.length.

[input] array.string wordList

An array containing all of the approved words. All words in the array are the same length and contain only lowercase English letters. You can assume that there are no duplicates in wordList.

Constraints:
2 ≤ wordList.length ≤ 600,
wordList[i].length = beginWord.length.

[output] integer

An integer representing the length of the shortest transformation sequence from beginWord to endWord using only words from wordList as described above.
*/

/*
 * BFS solution.
 * This problem is in fact a "Find shortest path from A->B in a undirectional gragh" problem. BFS is better.
 * Stolen from below link to study, annotated to learn. Modified to understand.
 * From here http://codeluli.blogspot.com/2013/04/word-ladder.html
 * @author of modified crap Chris Lee
 */

import java.util.HashSet;
import java.util.ArrayList;

public class WordLadder {

    public int wordLadder(String beginWord, String endWord, String[] wordList) {

        // Shove word list into a more efficient hash set.
        HashSet<String> betterWordList = new HashSet<String>(Arrays.asList(wordList));

        // Keep track of all words in your list you've checked so far so you can detect dupes.
        HashSet<String> dedupSet = new HashSet<String>();

        // Keep track of your iterations of transformations.
        ArrayList<String> fromList = new ArrayList<String>();

        // Seed the list with your beginning word.
        fromList.add(beginWord);

        // You always got at least one.
        int result = 1;

        // If we find no possible transformations to get to another iteration we want, break out of this loop. Return 0.
        while (fromList.size() != 0) {

            // List of your transformations. Also what you use for your transformations as you iterate down.
            ArrayList<String> toList = new ArrayList<String>();

            // For every word in your current transformation, search for a next word you can use.
            for (String currentWord : fromList) {

                //If your transformed word is your endWord word, you're done, return result.
                if (currentWord.equals(endWord)) {
                    return result;
                }

                // Take string you're looking at and shove it into a char array so we can look at every character.
                char[] testStringChars = currentWord.toCharArray();

                // Loop through your new possible string.
                for (int i = 0; i < testStringChars.length; i++) {

                    // Your base character will be the character at this index of the above for loop.
                    char originalChar = testStringChars[i];

                    // Loop through every lower case character in English language.
                    for (char testChar = 'a'; testChar < 'z'; testChar++) {

                        // If it's the same as the base, it's not transformed, skip it.
                        if (testChar == originalChar) {
                            continue;
                        }

                        // Otherwise, set that point in the word to this character, then make a new string of it.
                        testStringChars[i] = testChar;
                        String testStringTransformation = new String(testStringChars);

                        // See if that string is both in the word list, and not already in our transformation. If so,
                        // then we can add it to the possible transformation list and dupe checking list.
                        if (betterWordList.contains(testStringTransformation) && !dedupSet.contains(testStringTransformation)) {
                            toList.add(testStringTransformation);
                            dedupSet.add(testStringTransformation);
                        }
                    }
                    // End of one iteration of one possible string. Set your index back to it's original value.
                    // I'd probably name these better.
                    testStringChars[i] = originalChar;
                }
            }
            // Your new test bed of words that can transform into other words is now your updated list from above.
            fromList = toList;

            // The above loop and list is now populated with the next level of the graph/tree, add one to the result
            // Because any of those could lead to your solution, and is one additional step needed to reach it.
            result++;
        }
        // If we find no possible transformations to get to another iteration we want, break out of this loop. Return 0.
        return 0;
    }
}