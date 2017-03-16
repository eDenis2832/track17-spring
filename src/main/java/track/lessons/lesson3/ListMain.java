package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        /*
        MyArrayList list1 = new MyArrayList();
        list1.add(1);
        list1.add(34);
        list1.add(3245);
        System.out.println("1 - " + list1.get(0));
        System.out.println("2 - " + list1.get(1));
        System.out.println("3 - " + list1.get(2));
        System.out.println(list1.size());
        list1.remove(0);
        System.out.println("1 - " + list1.get(0));
        System.out.println("2 - " + list1.get(1));

        list1.remove(0);
        System.out.println("1 - " + list1.get(0));

        list1.remove(0);*/
        MyArrayList list = new MyArrayList(0);
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

    }
}
