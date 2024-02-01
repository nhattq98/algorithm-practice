import java.util.*


fun main(args: Array<String>) {
    optimalGroupAnagrams(arrayOf("eat", "tea", "tan", "ate", "nat", "bat"))
}

//ref: https://leetcode.com/problems/top-k-frequent-elements/description/
// 1. create a map to store value and frequency
// 2.
fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val map = mutableMapOf<Int, Int>()
    nums.forEach {
        map[it] = map.getOrDefault(it, 0) + 1
    }


    val entriesList = map.entries.toMutableList()

    entriesList.sortWith { (key, value), (key1, value1) ->
        if (value == value1) key1 - key else (value1 - value)
    }


    return intArrayOf()
}

//ref: https://leetcode.com/problems/group-anagrams/
fun groupAnagrams(strs: Array<String>): List<List<String>> {
    val mutableMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    strs.forEach {
        val sorted = it.toSortedSet().toString()
        if (mutableMap.containsKey(sorted)) {
            val alreadyList: MutableList<String>? = mutableMap[sorted]
            alreadyList?.add(it)
        } else {
            mutableMap[sorted] = mutableListOf(it)
        }
    }

    val result = mutableListOf<List<String>>()
    mutableMap.forEach { (_, u) ->
        result.add(u)
    }

    println(result)
    return result
}

//ref: https://leetcode.com/problems/group-anagrams/
fun optimalGroupAnagrams(strs: Array<String>): List<List<String>> {
    val map = mutableMapOf<String, MutableList<String>>()

    strs.forEach { str ->
        // create count array as the key
        val array = IntArray(26) {
            0
        }
        str.forEach {
            val charIndex = it.code - 97
            array[charIndex]++
        }

        val key = array.joinToString("#")
        if (map.containsKey(key)) {
            map[key]?.add(str)
        } else {
            map[key] = mutableListOf(str)
        }
    }

    val result = mutableListOf<List<String>>()
    map.forEach { (_, u) ->
        result.add(u)
    }

    println(result)
    return result
}

//ref: https://leetcode.com/problems/two-sum/
fun twoSum(nums: IntArray, target: Int): IntArray {
    val mutableMap = mutableMapOf<Int, Int>()

    nums.forEachIndexed { index, value ->
        val diff = target - value
        if (mutableMap.containsKey(diff)) {
            return intArrayOf(index, mutableMap[value]!!)
        } else {
            mutableMap[value] = index
        }
    }

    return intArrayOf(0, 0)
}

//ref: https://leetcode.com/problems/valid-anagram/

fun isAnagram(s: String, t: String): Boolean {
    if (s.length != t.length) return false

    val map = mutableMapOf<Char, Int>()
    for (i in s.indices) {
        val charS = s[i]
        val charT = s[i]

        map[charS] = map.getOrDefault(charS, 0) + 1
        map[charT] = map.getOrDefault(charT, 0) - 1
    }

    for (i in map) {
        if (i.value != 0) return false
    }

    return true
}

//ref: https://leetcode.com/problems/merge-sorted-array/?envType=study-plan-v2&envId=top-interview-150
//sol:  https://leetcode.com/problems/merge-sorted-array/solutions/3155713/two-pointer-on-kotlin/?envType=study-plan-v2&envId=top-interview-150

fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
    var i = m - 1
    var j = n - 1
    var k = m + n - 1
    while (j >= 0) {
        if (i < 0 || nums1[i] < nums2[j]) {
            nums1[k] = nums2[j]
            j -= 1
        } else {
            nums1[k] = nums1[i]
            i -= 1
        }
        k -= 1
    }

    nums1.forEach { println(it) }
}

//ref: https://leetcode.com/problems/integer-to-roman/description/
fun intToRoman(n: Int): String {
    var roman = StringBuilder()
    var number = n
    val defineRoman = listOf(
        Pair("M", 1000),
        Pair("CM", 900),
        Pair("D", 500),
        Pair("CD", 400),
        Pair("C", 100),
        Pair("XC", 90),
        Pair("L", 50),
        Pair("XL", 40),
        Pair("X", 10),
        Pair("IX", 9),
        Pair("V", 5),
        Pair("IV", 4),
        Pair("III", 3),
        Pair("II", 2),
        Pair("I", 1)
    )

    for (pair in defineRoman) {
        while (number >= pair.second) {
            roman.append(pair.first)
            number -= pair.second
        }
    }

    return roman.toString()
}

//ref: https://leetcode.com/problems/reverse-integer/
//testcase: 123 -> 321, -123 -> 321, 120
fun reverse(x: Int): Int {
    val isNegative = x < 0
    var number = if (isNegative) -1 * x else x

    if (number == 0) return 0

    //build reverse number
    var reverseNumber: Long = 0
    while (number > 0) {
        val lastDigit = number % 10
        reverseNumber += lastDigit
        reverseNumber *= 10
        number /= 10
    }
    reverseNumber /= 10

    if (reverseNumber > Integer.MAX_VALUE || reverseNumber < Integer.MIN_VALUE) return 0

    return if (isNegative) (-1 * (reverseNumber)).toInt() else reverseNumber.toInt()
}


//ref: https://leetcode.com/problems/zigzag-conversion/
fun convert(s: String, numRows: Int): String {
    if (numRows == 1 || s.length < numRows) {
        return s
    }

    //init 2d array
    val rows = Array(numRows) { StringBuilder() }
    var index = 0
    var step = 1

    for (c in s) {
        rows[index].append(c)
        if (index == 0) {
            step = 1
        } else if (index == numRows - 1) {
            step = -1
        }
        index += step
    }

    val res = StringBuilder()
    for (row in rows)
        res.append(row)

    return res.toString()
}

//ref: https://leetcode.com/problems/longest-palindromic-substring/
//find the Longest palindromic substring
fun longestPalindrome(s: String): String {
    var start = 0
    var end = 0
    var longestLength = 0

    if (s.isNullOrEmpty()) return ""
    if (s.length == 1) return s

    for (i in s.indices) {
        val length = Math.max(expandFromMiddle(s, left = i, right = i + 1), expandFromMiddle(s, left = i, right = i))

        if (length > longestLength) {
            start = i - ((length - 1) / 2)
            end = i + (length / 2)
        }
        longestLength = length.coerceAtLeast(longestLength)
    }

    val longestPalindromicSubStr = s.substring(start, end + 1)

    return longestPalindromicSubStr
}

fun expandFromMiddle(str: String?, left: Int, right: Int): Int {
    if (str.isNullOrEmpty() || left > right) return 0
    var _left = left
    var _right = right
    while (_left >= 0 && _right < str.length && str[_left] == str[_right]) {
        _left--
        _right++
    }

    return _right - _left - 1
}


//ref: https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
//find the longest substring without repeat characters
//sliding window algorithm

fun lengthOfLongestSubstring(s: String): Int {
    var start = 0
    var end = 0
    var maxLength = 0

    val charSet = mutableSetOf<Char>()
    if (s.length == 1) return 1

    while (end < s.length) {
        if (!charSet.contains(s[end])) {
            charSet.add(s[end])
            end++
            maxLength = maxLength.coerceAtLeast(end - start)
        } else {
            charSet.remove(s[start])
            start++
        }
    }
    return maxLength
}

//ref: https://leetcode.com/problems/add-two-numbers/description/
//add two sum linked list
//define single linked list in kotlin
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun addTwoSum(l1: ListNode?, l2: ListNode?): ListNode? {
    var first = l1
    var second = l2
    var res: ListNode? = null
    var tmp: ListNode? = null
    var prev: ListNode? = null
    var carry = 0
    var sum = 0

    while (first != null || second != null) {
        sum = carry + (first?.`val` ?: 0) + (second?.`val` ?: 0)
        //decide the carry
        carry = if (sum >= 10) 1 else 0
        //get the digit
        sum %= 10

        //create new node saving sum value
        tmp = ListNode(sum)

        //mark the head node
        if (res == null) {
            res = tmp
        }
        //connect node with the rest of linked list
        else {
            prev?.next = tmp
        }

        prev = tmp

        //move forward
        if (first != null) first = first.next
        if (second != null) second = second.next
    }

    //final carry
    if (carry > 0) tmp?.next = ListNode(carry)

    //return linked list
    return res
}

// remove duplicate array
fun removeDuplicates(nums: IntArray): Int {
    var index = 1
    for (i in 1 until nums.size) {
        if (nums[i - 1] != nums[i]) {
            nums[index] = nums[i];
            index++;
        }
    }
    println(nums.map { it.toString() })
    return index
}

// running sum of 1d array
fun runningSum(nums: IntArray): IntArray {
    var sum = 0
    val runningArray = IntArray(nums.size)
    for (i in nums.indices) {
        sum += nums[i]
        runningArray[i] = sum
    }
    return runningArray
}

//find pivot index
fun pivotIndex(nums: IntArray): Int {
    //get sum
    val sum = nums.sum()
    var lsum = 0
    var rsum = sum

    for (i in nums.indices) {
        rsum -= nums[i]
        if (lsum == rsum) return i

        lsum += nums[i]
    }

    return -1
}

//isomorphic string
//example
//s: egg, t: add is isomorphic string
//s: foo, t: bar is not isomorphic string

fun isIsomorphic(s: String, t: String): Boolean {
    //check length
    if (s.length != t.length) return false

    val mapString = hashMapOf<String, String>()
    val mapValid = hashMapOf<String, Boolean>()

    for (i in s.indices) {
        val ch1 = s[i].toString()
        val ch2 = t[i].toString()

        if (!mapString.containsKey(ch1)) {
            mapString[ch1] = ch2
            mapValid[ch2] = true
        } else {
            if (mapString[ch1] != ch2) return false
        }
    }

    return true
}