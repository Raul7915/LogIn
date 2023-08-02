import java.util.Arrays;

public class ArrayHelper {
    public static <T> void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


    public static <T> T [] pop(T[] arr) {
        T[] tmp = Arrays.copyOf(arr, arr.length - 1);
        return tmp;
    }

    public static <T> T[] push(T[] arr, T item) {
        T[] tmp = Arrays.copyOf(arr, arr.length + 1);
        tmp[tmp.length - 1] = item;
        return tmp;
    }
}