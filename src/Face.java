import java.util.Arrays;
import java.util.Objects;

public class Face {
    private int n;
    private Piece[][] pieces;
    private String name;

    public Piece[][] getPieces() {
        return pieces;
    }

    public Face(int n, Piece[][] pieces) {
        this.n = n;
        this.pieces = pieces;
    }

    public Face(int n, Piece[][] pieces, String name) {
        this.n = n;
        this.pieces = pieces;
        this.name = name;
    }

    public void print() {
        System.out.println(this.name);
        for (int i = 0; i < n; i++) {
            System.out.print("| ");
            for (int j = 0; j < n; j++) {
                System.out.print(pieces[i][j].toString() + " ");
            }
            System.out.println("|");
        }
    }

    public void rotate90(boolean prime) {
        Piece[][] mat = this.pieces;
        for (int x = 0; x < n / 2; x++) {
            for (int y = x; y < n - x - 1; y++) {
                if (prime) {
                    Piece temp = mat[x][y];
                    mat[x][y] = mat[y][n - 1 - x];
                    mat[y][n - 1 - x] = mat[n - 1 - x][n - 1 - y];
                    mat[n - 1 - x][n - 1 - y] = mat[n - 1 - y][x];
                    mat[n - 1 - y][x] = temp;
                } else {
                    Piece temp = mat[n - 1 - y][x];
                    mat[n - 1 - y][x] = mat[n - 1 - x][n - 1 - y];
                    mat[n - 1 - x][n - 1 - y] = mat[y][n - 1 - x];
                    mat[y][n - 1 - x] = mat[x][y];
                    mat[x][y] = temp;
                }
            }
        }
    }

    public void setRow(int index, Piece[] newRow) {
        for (int i = 0; i < n; i++) {
            pieces[index][i] = newRow[i];
        }
    }

    public void setCol(int index, Piece[] newCol) {
        for (int i = 0; i < n; i++) {
            pieces[i][index] = newCol[i];
        }
    }

    public Piece[] getRow(int index) {
        return pieces[index];
    }

    public Piece[] getCol(int index) {
        Piece[] res = new Piece[this.n];
        for (int i = 0; i < n; i++) {
            res[i] = this.pieces[i][index];
        }
        return res;
    }

    public Face clone() {
        Piece[][] pieces = new Piece[n][n];
        for (int j = 0; j < n; j++) {
            pieces[j] = new Piece[n];
            for (int k = 0; k < n; k++) {
                pieces[j][k] = new Piece(this.pieces[j][k].name);
            }
        }
        Face newFace = new Face(n, pieces, this.name);
        return newFace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face face = (Face) o;
        if (n != face.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!pieces[i][j].equals(face.pieces[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}

