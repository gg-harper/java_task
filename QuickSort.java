import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class QuickSort {
	public static void main(String[] args) {
	
		Scanner scanner = new Scanner(System.in);
		List<Integer> inputList = new ArrayList<>();
		String[] stringInput = scanner.nextLine().split(" ");
		for(int i = 0; i < stringInput.length; i++) {
			inputList.add(Integer.parseInt(stringInput[i]));
		}
		System.out.println(inputList);
		List<Integer> sortedList = quickSort(inputList);

	}

	private static List<Integer> quickSort(List<Integer> list) {
	
		int pivot = list.get(0);
		List<Integer> less = new ArrayList<>();
		List<Integer> greater = new ArrayList<>();
		for(int i = 1; i < list.size(); i++) {
			int element = list.get(i);
			if(element < pivot) {
				less.add(element);
			} else {
				greater.add(element);
			}
		}
		list = quickSort(less);
		list.add(pivot);
		list.addAll(quickSort(greater));
		return list;
	}
}
