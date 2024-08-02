package testNewFunc.IO;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigNumbersFormatting {

    // Metoda formatująca BigInteger
    public static String formatBigInteger(BigInteger bi) {
        String str = bi.toString();
        StringBuilder formatted = new StringBuilder();

        int length = str.length();
        int firstGroupLength = length % 3;
        if (firstGroupLength == 0) firstGroupLength = 3;

        formatted.append(str.substring(0, firstGroupLength));
        for (int i = firstGroupLength; i < length; i += 3) {
            formatted.append("'").append(str.substring(i, i + 3));
        }

        return formatted.toString();
    }

    // Metoda formatująca BigDecimal
    public static String formatBigDecimal(BigDecimal bd) {
        String str = bd.toPlainString();

        // Rozdzielenie części całkowitej i ułamkowej
        String[] parts = str.split("\\.");

        String integerPart = parts[0];
        String decimalPart = parts.length > 1 ? parts[1] : "";

        // Formatowanie części całkowitej
        StringBuilder formatted = new StringBuilder();
        int length = integerPart.length();
        int firstGroupLength = length % 3;
        if (firstGroupLength == 0) firstGroupLength = 3;

        formatted.append(integerPart.substring(0, firstGroupLength));
        for (int i = firstGroupLength; i < length; i += 3) {
            formatted.append("'").append(integerPart.substring(i, i + 3));
        }

        // Dodanie części ułamkowej, jeśli istnieje
        if (!decimalPart.isEmpty()) {
            formatted.append(".").append(decimalPart);
        }

        return formatted.toString();
    }
}
