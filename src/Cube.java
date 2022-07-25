import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Cube {
    private static final int face_number = 6;
    private static final int up = 0;
    private static final int down = 5;
    private static final int front = 2;
    private static final int right = 3;
    private static final int back = 4;
    private static final int left = 1;
    private static final String[] face_names = {"up", "left", "front", "right", "back", "down"};
    private static final String[] default_color_orders = {"O", "G", "W", "B", "Y", "R"};
    private int n;
    private Face[] faces;
    private String moves = "";

    public Cube(int n) {
        this.n = n;
        this.faces = new Face[face_number];
        for (int i = 0; i < face_number; i++) {
            Piece[][] pieces = new Piece[n][n];
            for (int j = 0; j < n; j++) {
                pieces[j] = new Piece[n];
                for (int k = 0; k < n; k++) {
                    pieces[j][k] = new Piece((default_color_orders[i] + j) + k);
                }
            }
            this.faces[i] = new Face(n, pieces, face_names[i]);
        }
    }

    public Cube(int n, Face[] faces, String moves) {
        this.n = n;
        this.faces = faces;
        this.moves = moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        if (n != cube.n) return false;
        String[] upPermutes = {
                "Z,0",
                "Z,1",
                "Z,2",
                "Z,3",
                "X,1",
                "X,3"
        };
        String[] yPermutes = {
                "Y,0",
                "Y,1",
                "Y,2",
                "Y,3",
        };
        for (int i = 0; i < upPermutes.length; i++) {
            String[] upOrder = upPermutes[i].split(",");
            Cube check = cube.Rotate90(upOrder[0].charAt(0), Integer.parseInt(upOrder[1]));
            for (int j = 0; j < yPermutes.length; j++) {
                String[] yOrder = yPermutes[j].split(",");
                check = check.Rotate90(yOrder[0].charAt(0), Integer.parseInt(yOrder[1]));
                if (this.equalFaces(check.faces)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean equalFaces(Face[] newFaces) {
        for (int i = 0; i < 6; i++) {
            if (!faces[i].equals(newFaces[i])) {
                return false;
            }
        }
        return true;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public void setFace(int index, Face face) {
        this.faces[index] = face;
    }

    public void print() {
        for (int i = 0; i < face_number; i++) {
            faces[i].print();
        }
    }

    public String[] getLastMoves(int nth) {
        String lastMoves = this.moves.substring(nthLastIndexOf(nth, " ", this.moves) + 1);
        return lastMoves.split(" ");
    }

    protected Face[] clone() {
        Face[] state = new Face[face_number];
        for (int i = 0; i < face_number; i++) {
            state[i] = faces[i].clone();
        }
        return state;
    }

    public Cube move(char name, Boolean prime, int move_index) {
        Face[] state = this.clone();
        int[] affectedFaces;

        int fth_index = move_index - 1;
        int nth_index = n - move_index;
        int c_index, n_index;

        switch (name) {
            case 'L':
                if (move_index == 1)
                    state[left].rotate90(!prime);
                if (prime)
                    affectedFaces = new int[]{front, down, back, up, front};
                else
                    affectedFaces = new int[]{up, back, down, front, up};

                for (int i = 0; i < affectedFaces.length - 1; i++) {
                    int current_face = affectedFaces[i];
                    int next_face = affectedFaces[i + 1];
                    c_index = current_face == back ? nth_index : fth_index;
                    n_index = next_face == back ? nth_index : fth_index;
                    Piece[] newCol = this.faces[current_face].getCol(c_index);
                    if (next_face == back || current_face == back)
                        Collections.reverse(Arrays.asList(newCol));
                    state[next_face].setCol(n_index, newCol);
                }
                break;
            case 'R':
                if (move_index == 1)
                    state[right].rotate90(prime);
                if (prime)
                    affectedFaces = new int[]{front, down, back, up, front};
                else
                    affectedFaces = new int[]{up, back, down, front, up};

                for (int i = 0; i < affectedFaces.length - 1; i++) {
                    int current_face = affectedFaces[i];
                    int next_face = affectedFaces[i + 1];
                    c_index = current_face == back ? fth_index : nth_index;
                    n_index = next_face == back ? fth_index : nth_index;
                    Piece[] newCol = this.faces[current_face].getCol(c_index);
                    if (next_face == back || current_face == back)
                        Collections.reverse(Arrays.asList(newCol));
                    state[next_face].setCol(n_index, newCol);
                }
                break;

            case 'U':
                if (move_index == 1)
                    state[up].rotate90(prime);
                if (prime)
                    affectedFaces = new int[]{right, back, left, front, right};
                else
                    affectedFaces = new int[]{front, left, back, right, front};

                for (int i = 0; i < affectedFaces.length - 1; i++) {
                    int current_face = affectedFaces[i];
                    int next_face = affectedFaces[i + 1];
                    c_index = fth_index;
                    n_index = fth_index;
                    state[next_face].setRow(n_index, this.faces[current_face].getRow(c_index));
                }
                break;

            case 'D':
                if (move_index == 1)
                    state[down].rotate90(!prime);
                if (prime)
                    affectedFaces = new int[]{right, back, left, front, right};
                else
                    affectedFaces = new int[]{front, left, back, right, front};

                for (int i = 0; i < affectedFaces.length - 1; i++) {
                    int current_face = affectedFaces[i];
                    int next_face = affectedFaces[i + 1];
                    c_index = nth_index;
                    n_index = nth_index;
                    state[next_face].setRow(n_index, this.faces[current_face].getRow(c_index));
                }
                break;
            case 'F':
                if (move_index == 1)
                    state[front].rotate90(prime);

                if (prime) {
                    Piece[] toDown = this.faces[left].getCol(nth_index);
                    Collections.reverse(Arrays.asList(toDown));
                    state[down].setRow(fth_index, toDown);

                    Piece[] toRight = this.faces[down].getRow(fth_index);
                    Collections.reverse(Arrays.asList(toRight));
                    state[right].setCol(fth_index, toRight);

                    state[left].setCol(nth_index, this.faces[up].getRow(nth_index));
                    state[up].setRow(nth_index, this.faces[right].getCol(fth_index));
                } else {
                    Piece[] toDown = this.faces[right].getCol(fth_index);
                    Collections.reverse(Arrays.asList(toDown));
                    state[down].setRow(fth_index, toDown);

                    Piece[] toLeft = this.faces[down].getRow(fth_index);
                    Collections.reverse(Arrays.asList(toLeft));
                    state[left].setCol(nth_index, toLeft);

                    state[right].setCol(fth_index, this.faces[up].getRow(nth_index));
                    state[up].setRow(nth_index, this.faces[left].getCol(nth_index));
                }
                break;
            case 'B':
                if (move_index == 1)
                    state[back].rotate90(!prime);

                if (prime) {
                    Piece[] toDown = this.faces[left].getCol(fth_index);
                    Collections.reverse(Arrays.asList(toDown));
                    state[down].setRow(nth_index, toDown);

                    Piece[] toRight = this.faces[down].getRow(nth_index);
                    Collections.reverse(Arrays.asList(toRight));
                    state[right].setCol(nth_index, toRight);

                    state[left].setCol(fth_index, this.faces[up].getRow(fth_index));
                    state[up].setRow(fth_index, this.faces[right].getCol(nth_index));
                } else {
                    Piece[] toDown = this.faces[right].getCol(nth_index);
                    Collections.reverse(Arrays.asList(toDown));
                    state[down].setRow(nth_index, toDown);

                    Piece[] toLeft = this.faces[down].getRow(nth_index);
                    Collections.reverse(Arrays.asList(toLeft));
                    state[left].setCol(fth_index, toLeft);

                    state[right].setCol(nth_index, this.faces[up].getRow(fth_index));
                    state[up].setRow(fth_index, this.faces[left].getCol(fth_index));
                }
                break;

            default:
                break;

        }
        String move_str = (this.moves.length() == 0 ? "" : " ") + name + (prime ? "'" : "") + move_index;
        Cube newCube = new Cube(n, state, this.moves + move_str);
        return newCube;
    }

    public String getMoves() {
        return moves;
    }

    public Cube Rotate90(char axes, int times) {
        Cube result = new Cube(n, this.clone(), this.moves);
        switch (axes) {
            case 'Z':
                if (times == 3) {
                    for (int i = 0; i < n; i++) {
                        if (i == n - 1)
                            result = result.move('B', true, 1);
                        else
                            result = result.move('F', true, i + 1);
                    }
                } else {
                    for (int t = 0; t < times; t++) {
                        for (int i = 0; i < n; i++) {
                            if (i == n - 1)
                                result = result.move('B', false, 1);
                            else
                                result = result.move('F', false, i + 1);
                        }
                    }
                }
                break;
            case 'Y':
                if (times == 3) {
                    for (int i = 0; i < n; i++) {
                        if (i == n - 1)
                            result = result.move('D', true, 1);
                        else
                            result = result.move('U', true, i + 1);
                    }
                } else {
                    for (int t = 0; t < times; t++) {
                        for (int i = 0; i < n; i++) {
                            if (i == n - 1)
                                result = result.move('D', false, 1);
                            else
                                result = result.move('U', false, i + 1);
                        }
                    }
                }
                break;
            case 'X':
                if (times == 3) {
                    for (int i = 0; i < n; i++) {
                        if (i == n - 1)
                            result = result.move('L', true, 1);
                        else
                            result = result.move('R', true, i + 1);
                    }
                } else {
                    for (int t = 0; t < times; t++) {
                        for (int i = 0; i < n; i++) {
                            if (i == n - 1)
                                result = result.move('L', false, 1);
                            else
                                result = result.move('R', false, i + 1);
                        }
                    }
                }
                break;
            default:
                break;
        }
        result.setMoves(this.moves);
        return result;
    }

    static int nthLastIndexOf(int nth, String ch, String string) {
        if (nth <= 0) return string.length();
        return nthLastIndexOf(--nth, ch, string.substring(0, string.lastIndexOf(ch)));
    }
}
