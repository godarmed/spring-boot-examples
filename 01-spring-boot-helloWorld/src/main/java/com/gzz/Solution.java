package com.gzz;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Solution {
	public static int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(nums[i])) {
				return new int[] { map.get(nums[i]), i };
			}
			map.put(target - nums[i], i);
		}
		return null;
	}

	/**
	 * add Str to IntList
	 * @param list
	 * @param target
	 * @return
	 */
	public static List<Integer> twoSum(List<Integer> list, String target) {
		// list.add("aaa");  Direct additions can't be compiled
		try {
			list.getClass().getMethod("add", Object.class).invoke(list, target);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		System.out.println(list);
		return list;
	}

	public static void main(String[] args) {
		//Bypass type checking by reflect
		//List<Integer> nums = new ArrayList<>(Arrays.asList( 2, 7, 11, 15, 8 ));
		//System.out.println(Solution.twoSum(nums, "str"));
		int i = 1;
		System.out.println((i++)+(i++));
		System.out.println(i);

		i = 2;
		System.out.println((i++)*3);
		System.out.println(i);

	}
}
