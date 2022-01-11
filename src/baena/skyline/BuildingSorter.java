package baena.skyline;

public class BuildingSorter {
	
	private BuildingSorter() {
		
	}
	
	public static void sortBuildings(Building[] arrayToSort) {
		
		mergeSort(arrayToSort, 0, arrayToSort.length-1);	// time complexity of O(n logn)
	}
	
	private static void mergeSort(Building[] array, int l, int r) {
		
		if(r <= l) return;	// trivial case
		
		int mid = (l + r) / 2;
		mergeSort(array, l, mid);
		mergeSort(array, mid+1, r);
		merge(array, l, mid, r);		
	}
	
	private static void merge(Building[] array, int l, int mid, int r) {
		 // Creating temporary subarrays
		Building[] leftArray = new Building[mid - l + 1];
		Building[] rightArray = new Building[r - mid];

	    // Copying our subarrays into temporaries
	    for (int i = 0; i < leftArray.length; i++)
	        leftArray[i] = array[l + i];
	    for (int i = 0; i < rightArray.length; i++)
	        rightArray[i] = array[mid + i + 1];

	    // Iterators containing current index of temp subarrays
	    int leftIndex = 0;
	    int rightIndex = 0;

	    // Copying from leftArray and rightArray back into array
	    for (int i = l; i < r + 1; i++) {
	        // If there are still uncopied elements in R and L, copy minimum of the two
	        if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
	            if (leftArray[leftIndex].getX1() < rightArray[rightIndex].getX1()) {
	               array[i] = leftArray[leftIndex];
	               leftIndex++;
	            } else {
	                array[i] = rightArray[rightIndex];
	                rightIndex++;
	            }
	        } else if (leftIndex < leftArray.length) {
	            // If all elements have been copied from rightArray, copy rest of leftArray
	            array[i] = leftArray[leftIndex];
	            leftIndex++;
	        } else if (rightIndex < rightArray.length) {
	            // If all elements have been copied from leftArray, copy rest of rightArray
	            array[i] = rightArray[rightIndex];
	            rightIndex++;
	        }
	    }
	}
	
}
