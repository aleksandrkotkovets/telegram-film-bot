package com.telegram.film_bot.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringHelper {

    final static String FILM_REGEX = "[\\n:!@#$%^&*()_+~`|\\,<.>/?\"'\\s]";

    public static boolean containsWords(String inputString, String criteria) {
        List<String> stringList = getRegexListString(inputString);
        List<String> wordsList = getRegexListString(criteria);

        return stringList.containsAll(wordsList);
    }

    public static List<String> getRegexListString(String inputString) {
        return Arrays.stream(inputString.split(FILM_REGEX)).filter(a -> !a.equals("")).map(String::toLowerCase).collect(Collectors.toList());
    }

    public static List<String> getUniqParamsWithSplit(List<String> messageList, String regex) {
        List<String> process = messageList.stream()
                .map(message -> getParam(message, regex))
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return getParamsSplit(process);
    }

    public static List<String> getUniqParams(List<String> messageList, String regex) {

        return messageList.stream()
                .map(message -> getParam(message, regex))
                .filter(Objects::nonNull)
                .map(String::trim)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }


    public static String getParam(String message, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(message);

        String find = null;
        while (matcher.find()) {
            String process = matcher.group(1);
            find = process.trim();
        }
        return find;
    }

    public static List<String> getParamsSplit(List<String> params) {
        List<String> find = split(params);
        return trimAndSorted(find);
    }

    private static List<String> split(List<String> params) {
        List<String> list = new ArrayList<>();
        params.stream()
                .map(str -> Arrays.asList(str.split(",")))
                .forEach(list::addAll);
        return list;
    }

    private static List<String> trimAndSorted(List<String> list) {
        return list.stream()
                .map(String::trim)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> sortStringHowIntList(List<String> list) {
        return list.stream().sorted(Comparator.comparingInt(Integer::parseInt)).collect(Collectors.toList());
    }

}

