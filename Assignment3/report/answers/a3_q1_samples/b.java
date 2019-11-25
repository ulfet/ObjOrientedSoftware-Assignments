public class Animal {
/* */
}
public class Monkey extends Animal {
/* */
}
public class Zoo<T> {
/* */
}

public class Test {
	Zoo<Monkey> zm = new Zoo<Monkey>();
	Zoo<Animal> za = zm;
}