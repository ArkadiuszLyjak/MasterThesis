package testNewFunc.IO;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RWdata {
    public static void main(String[] args) {

        int i = 23;
        double d = 3.14;
        boolean b = true;
        short s = 3;
        String s1 = "hello";

        BigInteger bi = new BigInteger(
                "1238934759843598437895435667768788785437984");

        BigDecimal bd = new BigDecimal(
                "985743878574859745875123.4567834545345454359");

        try (DataOutputStream dos = new DataOutputStream(
                Files.newOutputStream(
                        Paths.get(
                                "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\IO\\rwdata.dat")))) {

            dos.writeInt(i);
            dos.writeDouble(d);
            dos.writeBoolean(b);
            dos.writeShort(s);
            dos.writeUTF(s1);
            dos.writeUTF(BigNumbersFormatting.formatBigInteger(bi));
            dos.writeUTF(BigNumbersFormatting.formatBigDecimal(bd));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try (DataInputStream dis = new DataInputStream(
                new FileInputStream(
                        "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\IO\\rwdata.dat"))) {
            i = dis.readInt();
            System.out.println(i);

            d = dis.readDouble();
            System.out.println(d);

            b = dis.readBoolean();
            System.out.println(b);

            s = dis.readShort();
            System.out.println(s);

            s1 = dis.readUTF();
            System.out.println(s1);

            String formattedBi = dis.readUTF();
            System.out.println(formattedBi);

            String formattedBd = dis.readUTF();
            System.out.println(formattedBd);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
