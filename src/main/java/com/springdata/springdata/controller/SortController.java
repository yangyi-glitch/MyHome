package com.springdata.springdata.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Api(tags = "排序")
@RequestMapping("/sort")
@RestController
public class SortController {

    @ApiOperation("冒泡排序")
    @PostMapping("/bubbleSort")
    public int[] bubbleSort(@ApiParam("用户id集合") @RequestParam("ids") List<Integer> arr) {
        int[] ids = new int[arr.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = arr.get(i);
        }
        int n = ids.length;
        while (n > 0) {
            for (int i = 0; i < n - 1; i++) {
                if (ids[i] < ids[i + 1]) {
                    int c = ids[i];
                    ids[i] = ids[i + 1];
                    ids[i + 1] = c;
                }
            }
            n--;
        }
        return ids;
    }

    @ApiOperation("快速排序")
    @GetMapping("/quickSort")
    public int[] quickSort(@ApiParam("用户id集合") @RequestParam("arr") List<Integer> arr) {
        int[] ids = new int[arr.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = arr.get(i);
        }
        quickSortMet(ids, 0, ids.length - 1);
        return ids;
    }

    public void quickSortMet(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortMet(arr, low, pi - 1);
            quickSortMet(arr, pi + 1, high);
        }
    }

    public int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
