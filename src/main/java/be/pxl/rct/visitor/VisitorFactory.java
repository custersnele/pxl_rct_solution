package be.pxl.rct.visitor;

import com.github.javafaker.Faker;

import java.util.Random;

public class VisitorFactory {

    private static final Random RANDOM = new Random();
    private static final Faker FAKER = new Faker();

    public final Visitor createVisitor() {
        Visitor visitor = new Visitor();
        visitor.setFirstname(FAKER.name().firstName());
        int age = (int) (Math.abs(RANDOM.nextGaussian()) * 99);
        visitor.setAge(age);
        visitor.setCashAvailable(RANDOM.nextInt(2500));
        return visitor;
    }
}
