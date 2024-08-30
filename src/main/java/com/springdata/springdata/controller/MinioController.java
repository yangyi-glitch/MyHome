package com.springdata.springdata.controller;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MinioController {
    public static void main(String[] args) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("minioadmin","minioadmin")
                .build();

        String name  ="localhost-file";
        //判断bucker存在与否
        boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
        if (!b){
            //创建bucker桶
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
        }

        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(name).build());

        Iterator<Result<Item>> iterator = results.iterator();

        List<Object> list = new ArrayList<>();
        while (iterator.hasNext()){
            Item item = iterator.next().get();
            list.add(item.objectName());
            list.add(item.size());
        }

        System.out.println(list);
//        minioClient.listBuckets(ListBucketsArgs.builder().build())
    }
}
