import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        int n = 3;

        Cube cube3 = new Cube(n);
        Cube init = cube3.move('R', false, 1).move('U',false,1).move('R', true, 1).move('U',true,1)
                .move('R', false, 1).move('U',false,1).move('R', true, 1).move('U',true,1)
                ;

        LinkedList<Cube> queue = new LinkedList<>();
        init.setMoves("");
        queue.add(init);
        while (!queue.isEmpty()) {
            Cube head = queue.remove();
            String[] last3Moves = head.getLastMoves(3);
            String lastMove = last3Moves.length > 0 ? last3Moves[last3Moves.length - 1] : "";
            System.out.println(queue.size()+": "+head.getMoves());
            int addingChild = 0;
            outer: for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < move_names.length; j++) {
                    if (last3Moves.length>1 && getMoveName(last3Moves[1]) == move_names[j] && getMoveName(lastMove) == move_names[j])
                        continue;
                    if (lastMove != "" && getMoveName(lastMove) == move_names[j] && getMoveIndex(lastMove) == i + 1) {
                        Cube moved = head.move(move_names[j], isMovePrime(lastMove), i + 1);
                        if (moved.equals(cube3)){
                            System.out.println("Found: " + moved.getMoves());
                            return;
                        }
                        for (Cube listElement : queue) {
                            if (moved.equals(listElement)){
                                for (int k = 0; k < addingChild; k++) {
                                    queue.removeLast();
                                }
                                System.out.println("duplicate 1");
                                continue outer;
                            }
                        }
                        queue.add(moved);
                        addingChild++;
                    } else {
                        Cube moved1 = head.move(move_names[j], false, i + 1);
                        if (moved1.equals(cube3)){
                            System.out.println("Found: " + moved1.getMoves());
                            return;
                        }
                        for (Cube listElement : queue) {
                            if (moved1.equals(listElement)){
                                for (int k = 0; k < addingChild; k++) {
                                    queue.removeLast();
                                }
                                System.out.println("duplicate 2");
                                continue outer;
                            }
                        }
                        queue.add(moved1);
                        addingChild++;

                        Cube moved2 = head.move(move_names[j], true, i + 1);
                        if (moved2.equals(cube3)){
                            System.out.println("Found: " + moved2.getMoves());
                            return;
                        }
                        for (Cube listElement : queue) {
                            if (moved2.equals(listElement)){
                                for (int k = 0; k < addingChild; k++) {
                                    queue.removeLast();
                                }
                                System.out.println("duplicate 3");
                                continue outer;
                            }
                        }
                        queue.add(moved2);
                        addingChild++;
                    }
                }
            }

        }

    }

    static final char[] move_names = {'U', 'R', 'F', 'L', 'B', 'D'};

    static boolean isMovePrime(String move) {
        return move.contains("'");
    }

    static char getMoveName(String move) {
        return move.charAt(0);
    }

    static int getMoveIndex(String move) {
        return Integer.parseInt("" + move.charAt(move.length() - 1));
    }
}
