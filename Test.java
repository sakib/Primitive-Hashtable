public class Test {

    public static void main(String[] args) {

        Hash x = new Hash(5);

        String test = "Mary had a little lamb whose fleece was white as snow. And everywhere that Mary went, the lamb was sure to go.";
        String test2 = "Jack and Jill went up a hill to fetch a pail of water. Jack fell down and broke his crown and Jill came tumbling after..";
        test2 += "Jack  Jack fell down and broke his and Jill went up a hill to fetch a pail of water.crown and Jill came tumbling after..";
        test2 += "Jack a pail of water. Jack fell down and brnd Jill went up a hill to fetch aoke his crown and Jill came tumbling after..";
        test2 += "Jack a pail of water. Jack fell down and brnd Jill went up a hill to fetch aoke his crown and Jill came tumbling after..";

        for (int i = 0; i < test.length(); i+=2) {
            String key = test.substring(i, i+2);
            x.set(key, i);
        }

        System.out.println(x + "\n--------------");

        for (int i = 0; i < test.length(); i+=2) {
            String key = test.substring(i, i+2);
            x.set(key, i*2);
        }

        System.out.println(x + "\n--------------");

        for (int i = 0; i < test.length(); i+=2) {
            String key = test.substring(i, i+2);
            System.out.println(x.get(key));
        }

        System.out.println(x + "\n--------------");

        for (int i = 0; i < test.length(); i+=2)
            x.delete(test.substring(i,i+2));

        System.out.println(x.load());
        System.out.println(x + "\n--------------");

        for (int i = 0; i < test2.length(); i+=4)
            x.set(test2.substring(i,i+4), i);

        System.out.println(x);

    }

}
