package sortinggame;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;

public class SortingLogic {
    private Label[] boxes;
    private Label tempBox;

    public SortingLogic(Label[] boxes, Label tempBox) {
        this.boxes = boxes;
        this.tempBox = tempBox;
    }

    private void updateBox(int index, int value) {
        Platform.runLater(() -> boxes[index].setText(String.valueOf(value)));
    }

    private void updateTempBox(Integer value) {
        Platform.runLater(() -> tempBox.setText(value != null ? String.valueOf(value) : ""));
    }

    private void highlightCompare(int index1, int index2) throws InterruptedException {
        Platform.runLater(() -> {
            boxes[index1].setStyle("-fx-background-color: yellow;");
            boxes[index2].setStyle("-fx-background-color: yellow;");
        });
        TimeUnit.SECONDS.sleep(12);
        Platform.runLater(() -> {
            boxes[index1].setStyle("-fx-background-color: blue;");
            boxes[index2].setStyle("-fx-background-color: blue;");
        });
    }

    private void highlightSwap(int index1, int index2) throws InterruptedException {
        Platform.runLater(() -> {
            boxes[index1].setStyle("-fx-background-color: red;");
            boxes[index2].setStyle("-fx-background-color: red;");
        });
        TimeUnit.SECONDS.sleep(12);
        Platform.runLater(() -> {
            boxes[index1].setStyle("-fx-background-color: blue;");
            boxes[index2].setStyle("-fx-background-color: blue;");
        });
    }

    public void bubbleSort(int[] arr) throws InterruptedException {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                highlightCompare(j, j + 1);
                if (arr[j] > arr[j + 1]) {
                    updateTempBox(arr[j]);
                    TimeUnit.SECONDS.sleep(12);
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    updateBox(j, arr[j]);
                    updateBox(j + 1, arr[j + 1]);
                    highlightSwap(j, j + 1);
                    updateTempBox(null);
                }
            }
        }
    }

    public void selectionSort(int[] arr) throws InterruptedException {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            int minIndex = i;
            for (int j = i + 1; j < len; j++) {
                highlightCompare(minIndex, j);
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                updateTempBox(arr[i]);
                TimeUnit.SECONDS.sleep(12);
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                updateBox(i, arr[i]);
                updateBox(minIndex, arr[minIndex]);
                highlightSwap(i, minIndex);
                updateTempBox(null);
            }
        }
    }

    public void insertionSort(int[] arr) throws InterruptedException {
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            int current = arr[i];
            int j = i - 1;
            updateTempBox(current);
            while (j >= 0 && arr[j] > current) {
                highlightCompare(j, j + 1);
                arr[j + 1] = arr[j];
                updateBox(j + 1, arr[j]);
                j--;
            }
            arr[j + 1] = current;
            updateBox(j + 1, current);
            TimeUnit.SECONDS.sleep(12);
            updateTempBox(null);
        }
    }

    public void quickSort(int[] arr, int left, int right) throws InterruptedException {
        if (left >= right) return;

        int pivotIndex = partition(arr, left, right);
        quickSort(arr, left, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, right);
    }

    private int partition(int[] arr, int left, int right) throws InterruptedException {
        int pivot = arr[right];
        int pivotIndex = left;
        updateTempBox(pivot);
        for (int i = left; i < right; i++) {
            highlightCompare(i, right);
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[pivotIndex];
                arr[pivotIndex] = temp;
                updateBox(i, arr[i]);
                updateBox(pivotIndex, arr[pivotIndex]);
                highlightSwap(i, pivotIndex);
                pivotIndex++;
            }
        }
        int temp = arr[pivotIndex];
        arr[pivotIndex] = arr[right];
        arr[right] = temp;
        updateBox(pivotIndex, arr[pivotIndex]);
        updateBox(right, arr[right]);
        highlightSwap(pivotIndex, right);
        updateTempBox(null);
        return pivotIndex;
    }

    public void mergeSort(int[] arr, int left, int right) throws InterruptedException {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private void merge(int[] arr, int left, int mid, int right) throws InterruptedException {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            highlightCompare(k, k + 1);
            updateTempBox(leftArr[i] <= rightArr[j] ? leftArr[i] : rightArr[j]);
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            updateBox(k, arr[k]);
            TimeUnit.SECONDS.sleep(12);
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            updateTempBox(leftArr[i]);
            updateBox(k, arr[k]);
            TimeUnit.SECONDS.sleep(12);
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            updateTempBox(rightArr[j]);
            updateBox(k, arr[k]);
            TimeUnit.SECONDS.sleep(12);
            j++;
            k++;
        }
        updateTempBox(null);
    }
}
