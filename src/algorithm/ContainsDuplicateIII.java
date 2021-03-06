package algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * As a followup question, it naturally also requires maintaining a window of size k. 
 * When t == 0, it reduces to the previous question so we just reuse the solution.
 * Since there is now a constraint on the range of the values of the elements to be considered duplicates, 
 * it reminds us of doing a range check which is implemented in tree data structure and would take O(LogN) if a balanced tree structure is used, 
 * or doing a bucket check which is constant time. We shall just discuss the idea using bucket here.
 * 
 * Bucketing means we map a range of values to the a bucket. For example, if the bucket size is 3, we consider 0, 1, 2 all map to the same bucket. 
 * However, if t == 3, (0, 3) is a considered duplicates but does not map to the same bucket. 
 * This is fine since we are checking the buckets immediately before and after as well. 
 * So, as a rule of thumb, just make sure the size of the bucket is reasonable such that elements having the same bucket is immediately considered duplicates or duplicates must lie within adjacent buckets. 
 * So this actually gives us a range of possible bucket size, i.e. t and t + 1.
 * Actually, we can use t + 1 as the bucket size to get rid of the case when t == 0. It simplifies the code. 
 * Another complication is that negative ints are allowed. A simple num / (t+1) just shrinks everything towards 0. 
 * Therefore, we can just reposition every element to start from Integer.MIN_VALUE.
 * @author jasmineliu
 *
 */
public class ContainsDuplicateIII {
	
	/**
	 * 抽屉原理的应用，关键是如何构造抽屉（bucket）
	 * 一般会结合HashMap
	 * @param nums
	 * @param k 位差上限为k
	 * @param t 值差上限为t
	 * @return
	 */
	public boolean containsNearbyAlmostDuplicateDiscussion(int[] nums, int k, int t) {
        if (k < 1 || t < 0) return false;
        Map<Long, Long> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            long remappedNum = (long) nums[i] - Integer.MIN_VALUE;
            long bucket = remappedNum / ((long) t + 1); // 分给t+1个抽屉
            if (map.containsKey(bucket)
                || (map.containsKey(bucket - 1) && remappedNum - map.get(bucket - 1) <= t)
                || (map.containsKey(bucket + 1) && map.get(bucket + 1) - remappedNum <= t))
                            return true;
            if (map.entrySet().size() >= k) {
                long lastBucket = ((long) nums[i - k] - Integer.MIN_VALUE) / ((long) t + 1);
                map.remove(lastBucket);
            }
            map.put(bucket, remappedNum);
        }
        return false;
    }
	
//	public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
//        
//    }
	
//	public static void insertionSort(int[] a) {
//		
//	}
	
}
