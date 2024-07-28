package testNewFunc;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Employee {

    //region attributes
    private long id;
    private String name;
    private Gender gender;
    private LocalDate dob;
    private double income;

    //region Employee
    public Employee(long id, String name, Gender gender, LocalDate dob,
                    double income) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.income = income;
    }
    //endregion

    //region persons
    public static List<Employee> employees() {
        Employee p1 = new Employee(1,
                "Jake",
                Gender.MALE,
                LocalDate.of(1971, Month.JANUARY, 1),
                2343.0);

        Employee p2 = new Employee(2,
                "Jack",
                Gender.MALE,
                LocalDate.of(1972, Month.JULY, 21),
                7100.0);

        Employee p3 = new Employee(3,
                "Jane",
                Gender.FEMALE,
                LocalDate.of(1973, Month.MAY, 29),
                5455.0);

        Employee p4 = new Employee(4,
                "Jode",
                Gender.MALE,
                LocalDate.of(1974, Month.OCTOBER, 16),
                1800.0);

        Employee p5 = new Employee(5,
                "Jeny",
                Gender.FEMALE,
                LocalDate.of(1975, Month.DECEMBER, 13),
                1234.0);

        Employee p6 = new Employee(6,
                "Jason",
                Gender.MALE,
                LocalDate.of(1976, Month.JUNE, 9),
                3211.0);

        List<Employee> persons = Arrays.asList(p1, p2, p3, p4, p5, p6);

        return persons;
    }
    //endregion

    //region is(Fe)male
    public boolean isFemale() {
        return this.gender == Gender.FEMALE;
    }

    public boolean isMale() {
        return this.gender == Gender.MALE;
    }
    //endregion

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    //region getName
    public String getName() {
        return name;
    }
    //endregion

    public double getIncome() {
        return income;
    }
    //endregion

    //region get income & set income
    public void setIncome(double income) {
        this.income = income;
    }

    public long getId() {
        return id;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format("%d %s %s %s %.2f%n", id, name, dtf.format(dob), gender, income);
    }

    public static enum Gender {
        MALE, FEMALE
    }
    //endregion
}
