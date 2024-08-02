package testNewFunc.dziedziczenie;

import lombok.Getter;

public abstract class TwoDShape {
    @Getter(lombok.AccessLevel.PACKAGE)
    private final double width;
    @Getter
    private final double height;

    TwoDShape() {
        width = 0.0;
        height = 0.0;
    }

    TwoDShape(double width, double height) {
        this.width = width;
        this.height = height;
    }

    void showDimenstion() {
        System.out.println(width * height);
    }

    abstract void area();

}

class Triangle extends TwoDShape {
    String style;

    Triangle(double width, double height, String style) {
        super(width, height);
        this.style = style;
    }

    void showDimenstion() {
        System.out.println(getWidth() * getHeight() * 1 / 2);
    }

    void area() {
        System.out.println(getWidth() * getHeight());
    }
}

class DziedziczenieApp {
    public static void main(String[] args) {
//        TwoDShape twoDShape = new TwoDShape(23.4, 56.5);
//        twoDShape.showDimenstion();

        Triangle triangle = new Triangle(23.4, 56.5, "Triangle");
        triangle.showDimenstion();
        triangle.area();


    }
}
