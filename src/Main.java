public class Main {
    public static void main(String[] args) {
        Cube cube3 = new Cube(3);
        boolean ss = cube3.move('R', false, 1).move('B', false, 1).move('R', true, 1).move('B', true, 1)
                .move('R', false, 1).move('B', false, 1).move('R', true, 1).move('B', true, 1)
                .move('R', false, 1).move('B', false, 1).move('R', true, 1).move('B', true, 1)
                .move('R', false, 1).move('B', false, 1).move('R', true, 1).move('B', true, 1)
                .move('R', false, 1).move('B', false, 1).move('R', true, 1).move('B', true, 1)
                .move('R', false, 1).move('B', false, 1).move('R', true, 1).move('B', true, 1)
                .equals(cube3.Rotate90('Y',3));

        System.out.println(ss);

    }
}
