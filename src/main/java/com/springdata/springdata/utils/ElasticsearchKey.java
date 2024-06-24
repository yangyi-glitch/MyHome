package com.springdata.springdata.utils;

public class ElasticsearchKey {
    public static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"custName\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"custAddress\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
