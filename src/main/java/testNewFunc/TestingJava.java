package testNewFunc;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class TestingJava {

    public static void main(String[] args) throws IOException {

        //region IO
        String parent = "c:\\Users\\alyja\\Documents\\";
        String child = "fileMessage.txt";
        String fullPath = parent + child;
        Path path = Paths.get(fullPath);
        File file = new File(path.toFile().getAbsolutePath());

        //region Description
//        System.out.println(readFromInputStream(new InputStreamReader(Files.newInputStream(file.toPath()))));
//        System.out.println(readWithBufferedReader(fullPath));
//        List<String> allLInesList = Files.readAllLines(path);
//        Files.readAllLines(path).stream().filter(s -> s.contains("3.c")).forEach(System.out::println);
        //endregion

//        try {
//            Iterator<String> iterator = readAllLinesFromFileUsingNIO(fullPath)
//                    .stream()
//                    .collect(Collectors.toSet())
//                    .iterator();
//
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }
//        } catch (IOException e) {
////            throw new RuntimeException(e);
//            System.out.println("Wyjątek");
//        }

        //endregion

        //region done
        //region Optional
//        OptionalInt maxOdd = IntStream.of(10, 20, 30, 41, 51213).filter(n -> n % 2 == 1).max();
//        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 123, 345756, 3442};
//        Stream<Integer> integerStream = Stream.of(integers);

//        IntStream.Builder intStream = IntStream.builder();
//        intStream.add(11).add(22).add(33);
//        intStream.accept(44);
//        intStream.build().forEach(System.out::println);

//        Stream.Builder<String> stringBuilder = Stream.builder();
//        Stream<String> stringStream = stringBuilder.add("XML").add("Java").add("CSS").add("SQL").build();
//        stringStream.forEach(System.out::println);
        //endregion11


        //region Stream.Builder
//        Stream.Builder<String> stringBuilder = Stream.builder();
//
//        Stream<String> stringStream = stringBuilder
//                .add("CSS").add("HTML").add("JAVA").add("C++")
//                .build();
//
//        stringStream.forEach(System.out::println);
        //endregion


        //region Iterate & Generate
//        IntStream intStreamRangeClosed = IntStream.rangeClosed(1, 20);
//        intStreamRangeClosed.forEach(System.out::println);

//        boolean resultAll = intStreamRangeClosed.allMatch(value -> value % 2 == 0);
//        boolean resultAny = intStreamRangeClosed.anyMatch(value -> value % 2 == 1);
//        System.out.println(resultAny);

//        Stream<Integer> iterateStreamInteger = Stream.iterate(1, integer -> integer + 3).limit(10);
//        Stream<Integer> filteredInteger = iterateStreamInteger.filter(integer -> integer % 2 == 0).skip(4);
//        iterateStreamInteger.forEach(System.out::println);
//        filteredInteger.forEach(System.out::println);

//        Stream<Double> generateDoubleStream = Stream.generate(() -> 1.11).limit(10);
//        generateDoubleStream.forEach(System.out::println);

//        Stream<Double> randomDoubleStream = Stream.generate(Math::random).limit(10);
//        randomDoubleStream.forEach(System.out::println);

//        new Random().ints()
//                .limit(5)
//                .forEach(System.out::println);

//        Stream.generate(new Random()::nextInt)
//                .limit(5)
//                .forEach(System.out::println);

//        IntStream.generate(() -> 1)
//                .limit(10)
//                .forEach(System.out::println);

//        IntStream numbers = Arrays.stream(new int[]{1, 2, 3});
//        Stream<String> names = Arrays.stream(new String[]{"XML", "Java"});

//        Set<String> streamSet = new HashSet<>();
//        streamSet.add("Arek");
//        streamSet.add("Marek");

//        Stream<String> namesStream = streamSet.stream();
//        namesStream.forEach(s -> System.out.printf("%s ", s));
//        Stream<String> namesStreamParallel = streamSet.parallelStream();
//        namesStreamParallel.forEach(s -> System.out.printf("%s ", s));

//        String str = "5 123,123,qwe,1,123, 25";
//        str.chars()
//                .filter(n -> !Character.isDigit((char) n) && !Character.isWhitespace((char) n))
//                .forEach(n -> System.out.print((char) n));

//        String strPattern = "XML,CSS,HTML";
//        Pattern.compile(",").splitAsStream(strPattern).forEach(System.out::println);
//        Pattern.compile(",").splitAsStream(str).forEach(System.out::println);

        //endregion


        //region flat map
//        IntStream integerStream = IntStream.rangeClosed(1, 10);
//        DoubleStream doubleStream = integerStream.mapToDouble(value -> value * 1.0);
//

//        Stream.of(1, 2, 3)
//                .flatMapToDouble(integer -> DoubleStream.iterate((double) integer, operand -> operand * 1.1))
////                .flatMapToLong(integer -> LongStream.iterate(integer, operand -> operand * 10))
//                .limit(10)
//                .forEach(System.out::println);


//        Stream.of("XML", "Java", "CSS")
//                .map(CharSequence::chars)
//                .flatMap(intStream -> intStream.mapToObj(value -> (char) value))
//                .forEach(System.out::print);


//        Stream.of("XML", "Java", "CSS")
//                .flatMap(s -> IntStream.range(0, s.length()).mapToObj(s::charAt))
//                .forEach(System.out::println);


//        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
//        Stream<Integer> integerStream = integers.stream();
//        Optional<Integer> sum = integerStream.reduce(Integer::sum);
//        sum.ifPresent(System.out::println);


//        Integer sumInteger = integerStream.reduce(0, Integer::sum);
//        System.out.println(sumInteger);


//        double incomeAllEmployees = Employee.persons().stream()
//                .mapToDouble(Employee::getIncome)
//                .reduce(0.0, Double::sum);
//        System.out.println(incomeAllEmployees);


//        Double sum = Employee.persons()
//                .stream()
//                .reduce(0.0, (aDouble, employee) -> aDouble + employee.getIncome(), Double::sum);
//        System.out.println(sum);


//        Optional<Integer> max = Stream.of(1, 2, 3, 4, 5).reduce(Integer::max);
//
//        if (max.isPresent()) {
//            System.out.println("max = " + max.get());
//        } else {
//            System.out.println("max is not  defined.");
//        }
//
//        max = Stream.<Integer>empty().reduce(Integer::max);
//
//        if (max.isPresent()) {
//            System.out.println("max = " + max.get());
//        } else {
//            System.out.println("max is not  defined.");
//        }


//        Optional<Employee> person = Employee.persons()
//                .stream()
//                .reduce((p1, p2) -> p1.getIncome() > p2.getIncome() ? p1 : p2);
//
//        if (person.isPresent()) {
//            System.out.println("Highest earner: " + person.get());
//        } else {
//            System.out.println("Could not  get   the   highest earner.");
//        }
        //endregion


        //region Java Stream Aggregation
//        double incomeSum = Employee.persons()
//                .stream()
//                .mapToDouble(Employee::getIncome)
//                .sum();


//        Optional<Employee> comparingIncome = Employee.persons()
//                .stream()
//                .max(Comparator.comparingDouble(Employee::getIncome));
//        comparingIncome.ifPresent(System.out::println);
//

//        OptionalDouble maxIncome = Employee.persons().stream().mapToDouble(Employee::getIncome).max();
//        maxIncome.ifPresent(System.out::println);
        //endregion


        //region Java Streams Count
//        long personCount1 = Employee.persons().stream().count();
//        System.out.println("Person count: " + personCount1);


//        long personCount2 = Employee.persons()
//                .stream()
//                .mapToLong(p -> 1L)
//                .sum();
//        System.out.println(personCount2);


//        long personCount3 = Employee.persons()
//                .stream()
//                .map(p -> 1L)
//                .reduce(0L, Long::sum);
//        System.out.println(personCount3);


//        long personCount4 = Employee.persons()
//                .stream()
//                .reduce(0L, (partialCount, person) -> partialCount + 1L, Long::sum);
//        System.out.println(personCount4);
        //endregion


        //region Path
//        Path path = Paths.get("C:/repo/MasterThesis/src/main/java/MasterThesis/testJava/TestingJava.java");
//
//        try (Stream<String> lines = Files.lines(path)) {
////            lines.filter(s -> (!Pattern.compile("//").matcher(s).matches())).forEach(System.out::println);
//            lines.filter(s -> (Pattern.compile("//").matcher(s).matches())).forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Path dir = Paths.get(".");
//        Path dir = Paths.get("C:/repo");
//        Path dir = Paths.get("C:/repo/MasterThesis");
//        Path dir = Paths.get("C:/repo/MasterThesis/src");
//        Path dir = Paths.get("C:/repo/MasterThesis/src/main");
//        Path dir = Paths.get("C:/repo/MasterThesis/src/main/java");
//        Path dir = Paths.get("C:/repo/MasterThesis/src/main/java/MasterThesis");
//        Path dir = Paths.get("C:/repo/MasterThesis/src/main/java/MasterThesis/testJava");
//        System.out.printf("%nThe file tree for %s%n%n", dir.toAbsolutePath());
//        try (Stream<Path> fileTree = Files.walk(dir)) {
//            fileTree.forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //endregion


        //region peek
//        int sum = Stream.of(1, 2, 3, 4, 5)
//                .peek(e -> System.out.println("Taking integer: " + e))
//                .filter(n -> n % 2 == 1)
//                .peek(e -> System.out.println("Filtered integer: " + e))
//                .map(n -> n * n)
//                .peek(e -> System.out.println("Mapped integer: " + e))
//                .reduce(0, Integer::sum);
//
//        System.out.println("Sum = " + sum);
        //endregion


        //region Employee
//        Employee.persons().stream().filter(Employee::isFemale).forEach(System.out::println);

//        List<Employee> personList = Employee.persons();

//        personList.stream().filter(Employee::isFemale)
//                .forEach(employee -> employee.setIncome(employee.getIncome() * 1.1));

//        System.out.println("After increasing the income: \n" + personList);

//        for (Employee employee : personList) {
//            System.out.printf(employee.toString());
//        }

//        personList.stream().parallel()
//                .filter(Employee::isMale)
//                .map(Employee::getName)
//                .forEach(System.out::println);


//        personList.parallelStream()
//                .filter(Employee::isMale)
//                .filter(employee -> employee.getIncome() > 4000)
//                .map(Employee::getName)
//                .forEach(System.out::println);
        //endregion


        //region Java Streams - Java Stream Collectors
//        List<Person> persons = Arrays.asList(
//                new Person("Max", 1),
//                new Person("Peter", 2),
//                new Person("Pamela", 3),
//                new Person("David", 4));

//        Collector<Person, ?, Map<Integer, String>> personMapCollector = Collectors.toMap(Person::getId, Person::getName);
//        Map<Integer, String> personMap = persons.stream().collect(personMapCollector);

//        Set<Map.Entry<Integer, String>> entries = personMap.entrySet();
//        for (Map.Entry<Integer, String> entry : personMap.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//
//        for (String names : personMap.values()) {
//            System.out.println(names);
//        }
//
//        for (Integer ids : personMap.keySet()) {
//            System.out.println(ids);
//        }


//        Collector<Person, StringJoiner, String> personNameCollector =
//                Collector.of(() -> new StringJoiner(" | "), // supplier
//                        (j, p) -> j.add(p.getName().toUpperCase()), // accumulator
//                        (j1, j2) -> j1.merge(j2), // combiner
//                        StringJoiner::toString); // finisher
//        String names = persons.stream().collect(personNameCollector);
//        System.out.println(names);


//        Collector<Person, ?, Double> personDoubleCollector = Collectors.averagingInt(Person::getId);
//        Double result = persons.stream().collect(personDoubleCollector);
//        System.out.println(result);


//        Double averageIncome = Employee.persons()
//                .stream()
//                .collect(Collectors.averagingDouble(Employee::getIncome));
//        System.out.println(averageIncome);


//        Long amount = Employee.persons().stream().collect(Collectors.counting());
//        System.out.println(amount);


//        Supplier<ArrayList<String>> supplier1 = () -> new ArrayList<>();
//        Supplier<ArrayList<String>> supplier2 = ArrayList::new;
//        BiConsumer<ArrayList<String>, String> accumulator1 = (list, name) -> list.add(name);
//        BiConsumer<ArrayList<String>, String> accumulator2 = ArrayList::add;


//        Supplier<List<String>> supplier = ArrayList::new;
//        BiConsumer<List<String>, String> accumulator = List::add;
//        BiConsumer<List<String>, List<String>> combiner = List::addAll;
//        List<String> names = Employee.persons()
//                .stream()
//                .map(Employee::getName)
//                .collect(supplier, accumulator, combiner);
//        System.out.println(names);


//        Supplier<Set<Double>> setSupplier = TreeSet::new;
//
//        Set<Double> income = Employee.persons()
//                .stream()
//                .map(Employee::getIncome)
//                .collect(Collectors.toCollection(setSupplier));
//
//        System.out.println(income);


//        List<String> personsList = Employee.persons()
//                .stream()
//                .map(Employee::getName)
//                .collect(Collectors.toList());
//
//        System.out.println(personsList);

//        iterateUsingLambda(collectingToMapJoinValsForTheSameKeys(Employee.persons()));

//        Map<Employee.Gender, Long> countByGender = Employee.persons()
//                .stream()
//                .collect(Collectors.toMap(
//                        Employee::getGender,
//                        p -> 1L,
//                        (oldCount, newCount) -> newCount + oldCount));
//
//        System.out.println(countByGender);

//        Function<Employee, Employee.Gender> keyMaker = Employee::getGender;
//        Function<Employee, Employee> valueMaker = Function.identity();
//        BinaryOperator<Employee> mergeFunction = (employeeOld, employeeNew) ->
//                employeeOld.getIncome() > employeeNew.getIncome() ? employeeOld : employeeNew;
//
//        Map<Employee.Gender, Employee> highestEarnerByGender = Employee.persons()
//                .stream()
//                .collect(Collectors.toMap(
//                        keyMaker,
//                        valueMaker,
//                        mergeFunction));
//
//        System.out.println(highestEarnerByGender);
        //endregion


        //region Java Streams Join
//        List<Person> persons = Arrays.asList(
//                new Person("Max", 1),
//                new Person("Peter", 2),
//                new Person("Pamela", 3),
//                new Person("David", 4));

//        List<Employee> persons = Employee.persons();
//        String names = persons
//                .stream()
//                .map(Employee::getName)
//                .collect(Collectors.joining());
//
//        System.out.println("Joined names:  " + names);
//
//        String delimitedNames = persons.stream()
//                .map(Employee::getName)
//                .collect(Collectors.joining(", "));
//
//        System.out.println("Joined,  delimited names:  " + delimitedNames);
//
//        String prefixedNames = persons.stream()
//                .map(Employee::getName)
//                .collect(Collectors.joining(", ", "Hello ", ".  Goodbye."));
//
//        System.out.println(prefixedNames);
        //endregion


        //region Statistics
//        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
//        stats.accept(100.0);
//        stats.accept(300.0);
//        stats.accept(230.0);
//        stats.accept(532.0);
//        stats.accept(422.0);
//
//        long count = stats.getCount();
//        double sum = stats.getSum();
//        double min = stats.getMin();
//        double avg = stats.getAverage();
//        double max = stats.getMax();
//
//        System.out.printf("count=%d%n sum=%.2f%n min=%.2f%n average=%.2f%n max=%.2f%n",
//                count, sum, min, max, avg);

//        Supplier<DoubleSummaryStatistics> supplier = DoubleSummaryStatistics::new;
//        ObjDoubleConsumer<DoubleSummaryStatistics> accumulator = DoubleSummaryStatistics::accept;
//        BiConsumer<DoubleSummaryStatistics, DoubleSummaryStatistics> combiner = DoubleSummaryStatistics::combine;
//
//        DoubleSummaryStatistics doubleSummaryStatistics = Employee.persons()
//                .stream()
//                .mapToDouble(Employee::getIncome)
//                .collect(supplier, accumulator, combiner);
//
//        System.out.println(doubleSummaryStatistics);


//        DoubleSummaryStatistics dss = Employee.persons()
//                .stream()
//                .collect(Collectors.summarizingDouble(Employee::getIncome));
//
//        System.out.println(dss.getCount());
//        System.out.println(dss.getMax());
//        System.out.println(dss.getAverage());
//        System.out.println(dss.getSum());
//        System.out.println(dss.getMin());


        //endregion
        //endregion

        //region Java Streams Grouping
//        Map<Employee.Gender, Long> countByGender = Employee.employees()
//                .stream()
//                .collect(Collectors.groupingBy(
//                        Employee::getGender,
//                        Collectors.counting()));
//
//        System.out.println(countByGender);


//        Map<Employee.Gender, String> namesByGender = Employee.employees()
//                .stream()
//                .collect(Collectors.groupingBy(
//                        Employee::getGender,
//                        Collectors.mapping(
//                                Employee::getName,
//                                Collectors.joining(", "))));
//
//        System.out.println(namesByGender);


//        Map<Employee.Gender, List<String>> namesByGender2 = Employee.employees()
//                .stream()
//                .collect(Collectors.groupingBy(
//                        Employee::getGender,
//                        Collectors.mapping(
//                                Employee::getName,
//                                Collectors.toList())));
//
//        System.out.println(namesByGender2);


//        Map personsByGenderAndDobMonth = Employee.employees()
//                .stream()
//                .collect(Collectors.groupingBy(
//                        Employee::getGender,
//                        Collectors.groupingBy(
//                                p -> p.getDob().getMonth(),
//                                Collectors.mapping(
//                                        Employee::getName,
//                                        Collectors.joining(", ")))));
//
//        System.out.println(personsByGenderAndDobMonth);


//        Map<Employee.Gender, DoubleSummaryStatistics> incomeStatsByGender = Employee.employees()
//                .stream()
//                .collect(Collectors.groupingBy(
//                        Employee::getGender,
//                        Collectors.summarizingDouble(Employee::getIncome)));
//
//        System.out.println(incomeStatsByGender);

        //endregion

        //region Java Streams - Java Stream Partitioning
//        Map<Boolean, List<Employee>> partionedByMaleGender =
//                Employee.employees()
//                        .stream()
//                        .collect(Collectors.partitioningBy(Employee::isMale));
//
//        System.out.println(partionedByMaleGender);
        //endregion

        //region Java Streams Converter Collector Results
//        List<String> names = Employee.employees()
//                .stream()
//                .map(Employee::getName)
//                .collect(Collectors.collectingAndThen(Collectors.toList(),
//                        result -> Collections.unmodifiableList(result)));
//
//        System.out.println(names);
        //endregion

    }

    //region readFromInputStream
    public static String readFromInputStream(InputStreamReader isr) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader br = new BufferedReader(isr)) {
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            System.out.println("Błąd metody");
//            e.printStackTrace();
        }

        return stringBuilder.toString();

    }
    //endregion

    //region readAllLinesFromFileUsingNIO
    public static List<String> readAllLinesFromFileUsingNIO(String fullFilePath) throws IOException {
        return Files.readAllLines(Paths.get(fullFilePath), StandardCharsets.US_ASCII);
    }
    //endregion

    //region readWithBufferedReader
    public static String readWithBufferedReader(String fullPathToFile) throws FileNotFoundException {

        StringJoiner stringJoiner = new StringJoiner("\n");

        try (BufferedReader br = new BufferedReader(new FileReader(fullPathToFile))) {
            while (br.ready()) {
                stringJoiner.add(br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringJoiner.toString();

    }
    //endregion

    //region iterateUsingLambda
    public static <K, V> void iterateUsingLambda(Map<K, V> map) {
        Iterator<Map.Entry<K, V>> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<K, V> mapElement = mapIterator.next();
            System.out.println(mapElement.getKey() + " | " + mapElement.getValue());
        }

    }
    //endregion

    //region collectingToMap
    public static Map<Employee.Gender, String> collectingToMapJoinValsForTheSameKeys(List<Employee> employees) {
        BinaryOperator<String> stringJoiner = (s1, s2) -> String.join(", ", s1, s2);

        return employees.stream().collect(Collectors.toMap(
                Employee::getGender,
                Employee::getName,
                stringJoiner));
    }
    //endregion
}

