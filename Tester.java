
public class Tester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

//	Implementation or java driver for testing FourLinkedlist;
	public static void main(String[] args) {
		
		FourLinkedList<Object> list = new FourLinkedList<>();
		
		list.add("BOS");
		list.add("LAX");
		list.add("222");
		list.add("YAX");
		list.add("BYA");
		list.add("ABC");
		list.add("BCN");
		list.add("HDK");
		list.add("NZA");
		list.add("HYD");
		list.add("YYY");
	
		System.out.print("Size: ");
		System.out.println(list.size());
		System.out.println(list.toString());
		
		list.add(2, "333");
		list.add(3, "444");
		
		System.out.println();
		System.out.print("Size: ");
		System.out.println(list.size());
		System.out.println(list.toString());
		
		list.remove(13);
		
		System.out.print("\nSize: ");
		System.out.println(list.size());
		System.out.println(list.toString());
		
		list.remove(12);
		System.out.print("\nSize: ");
		System.out.println(list.size());
		System.out.println(list.toString());
		
		System.out.print("\nGet: ");
		System.out.print(list.get(6));
		System.out.println();
		
		list.clear();
		
		System.out.print("\nSize: ");
		System.out.println(list.size());
		System.out.println(list.toString());

	}

}
