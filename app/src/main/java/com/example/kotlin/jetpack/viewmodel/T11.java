package com.example.kotlin.jetpack.viewmodel;

public class T11 {
    public void sort(int[] a) {
        int temp;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j + 1] < a[j]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 1   2   3   4   5   6  7   8   9
     */

    public void s(int[] aar) {
        int temp;
        for (int i = 0; i < aar.length; i++) {
            boolean sorted = true;
            for (int j = 0; j < aar.length - i - 1; j++) {
                if (aar[j + 1] < aar[j]) {
                    temp = aar[j + 1];
                    aar[j + 1] = aar[j];
                    aar[j] = temp;
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

}
