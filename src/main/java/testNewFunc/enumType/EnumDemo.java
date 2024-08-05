package testNewFunc.enumType;

// Proste wyliczenie dla różnych środków transportu
enum Transport {
    AUTO, AUTOBUS, TIR, SAMOLOT, POCIAG
}

// Wyliczenie dla samochodów z dodatkową właściwością maksymalnej prędkości
enum Cars {
    AUDI(200), BMW(340), PORSCHE(400);

    private int max_speed;

    // Konstruktor dla ustawienia maksymalnej prędkości
    Cars(int max_speed) {
        this.max_speed = max_speed;
    }

    // Getter dla maksymalnej prędkości
    public int getMax_speed() {
        return max_speed;
    }
}

public class EnumDemo {
    public static void main(String[] args) {
        // Przykład użycia wyliczenia Transport
        Transport t = Transport.AUTO;

        switch (t) {
            case AUTO:
                System.out.println("Auto");
                break;
            case AUTOBUS:
                System.out.println("AutoBus");
                break;
            case TIR:
                System.out.println("TIR");
                break;
            case SAMOLOT:
                System.out.println("Samolot");
                break;
            case POCIAG:
                System.out.println("Pociąg");
                break;
        }

        // Przykład użycia wyliczenia Cars i wyświetlenia maksymalnej prędkości
        for (Cars car : Cars.values()) {
            System.out.println(car + " ma maksymalną prędkość " + car.getMax_speed() + " km/h");
        }
    }
}
