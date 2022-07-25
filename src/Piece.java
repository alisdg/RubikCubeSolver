import java.util.Objects;

public class Piece {
    private Colors color;
    String name;

    public Piece(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "'" + name + "'" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return Objects.equals(name, piece.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
enum Colors {
    RED, BLUE, WHITE, GREEN, YELLOW, ORANGE
}